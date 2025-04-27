package com.hsbc.balance.domain.accountcontext.domainservice;

/**
 *
 */

import com.alibaba.cola.exception.BizException;
import com.hsbc.balance.common.aop.annotation.LogMonitor;
import com.hsbc.balance.common.cache.CacheService;
import com.hsbc.balance.common.exception.AccountNotFoundException;
import com.hsbc.balance.common.exception.InfrastructureException;
import com.hsbc.balance.common.exception.InsufficientBalanceException;
import com.hsbc.balance.domain.accountcontext.entity.AccountBO;
import com.hsbc.balance.domain.accountcontext.entity.AccountRecordBO;
import com.hsbc.balance.domain.accountcontext.gateway.AccountGateway;
import com.hsbc.balance.domain.accountcontext.gateway.AccountRecordGateway;
import com.hsbc.balance.domain.transactioncontext.domainservice.TransactionDomainService;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author jixueWang
 *
 *
 */
@Service
@Slf4j
public class AccountDomainService {
    /**
     * 账户服务的Facade接口，提供账户相关的业务逻辑操作。
     */
    @Autowired
    private AccountGateway accountGateway;

    /**
     * 账户记录服务的Facade接口，提供账户记录相关的业务逻辑操作。
     */
    @Autowired
    private AccountRecordGateway accountRecordGateway;

    /**
     * 负责管理和操作缓存数据的服务。
     */
    @Autowired
    private CacheService cacheService;

    /**
     * 交易服务对象，用于在事务事件中处理交易相关操作。
     */
    @Autowired
    private TransactionDomainService transactionDomainService;

    /**
     * 缓存键的前缀，用于区分不同类型的缓存数据。
     */
    private final static String BIZ_TYPE = "account";

    /**
     * 缓存数据的过期时间，单位为天。
     */
    private final static long EXPIRATION_DAY = 1L;


    /**
     * 更新账户余额并创建或更新记录。
     *
     * @param transactionBO 对象，包含源账户ID、目标账户ID和转账金额。
     * @throws BizException              如果在更新账户时出现数据库异常或其他未知异常。
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(
            value = {BizException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @LogMonitor
    public void updateAccountBalances(TransactionBO transactionBO) {
        try {
            // 1. 默认使用数据库悲观锁，所以需要对查询顺序进行控制，防止死锁
            String firstAccountId = transactionBO.getSourceAccountId().compareTo(transactionBO.getTargetAccountId()) < 0 ?
                    transactionBO.getSourceAccountId() : transactionBO.getTargetAccountId();
            String secondAccountId = transactionBO.getSourceAccountId().compareTo(transactionBO.getTargetAccountId()) > 0 ?
                    transactionBO.getSourceAccountId() : transactionBO.getTargetAccountId();
            // 2. 查询并锁定
            AccountBO firstAccount = accountGateway.findByAccountIdForUpdate(firstAccountId);
            if(Objects.isNull(firstAccount)){
                new AccountNotFoundException("账户ID【"+firstAccountId+"】不存在");
            }
            AccountBO secondAccount = accountGateway.findByAccountIdForUpdate(secondAccountId);
            if(Objects.isNull(secondAccount)){
                new AccountNotFoundException("账户ID【"+secondAccountId+"】不存在");
            }
            // 3.
            AccountBO sourceAccount, targetAccount;
            if (firstAccount.getAccountId().equals(transactionBO.getSourceAccountId())) {
                sourceAccount = firstAccount;
                targetAccount = secondAccount;
            } else {
                sourceAccount = secondAccount;
                targetAccount = firstAccount;
            }
            // 4. 检查余额是否充足
            if (sourceAccount.getBalance().compareTo(transactionBO.getAmount()) < 0) {
                log.warn("当前交易的原账户余额不足, transactionId={}", transactionBO.getTransactionId());
                throw new InsufficientBalanceException("余额不足");
            }
            // 5. 更新余额
            sourceAccount.setBalance(sourceAccount.getBalance().subtract(transactionBO.getAmount()));
            targetAccount.setBalance(targetAccount.getBalance().add(transactionBO.getAmount()));
            // 6. 更新更新时间
            sourceAccount.setModified(new Date());
            targetAccount.setModified(new Date());
            // 7. 更新账户
            accountGateway.update(sourceAccount);
            accountGateway.update(targetAccount);
            // 8. 更新缓存
            cacheService.add(cacheService.generateKey(BIZ_TYPE, sourceAccount.getAccountId()), sourceAccount, EXPIRATION_DAY, TimeUnit.DAYS);
            cacheService.add(cacheService.generateKey(BIZ_TYPE, targetAccount.getAccountId()), targetAccount, EXPIRATION_DAY, TimeUnit.DAYS);
            // 9.创建记录数据
            createRecord(sourceAccount);
            createRecord(targetAccount);
            //10 更新交易表信息
            transactionDomainService.changeTransactionProcessed(transactionBO.getTransactionId());
        } catch (InfrastructureException e) {
            // 抛出异常以触发重试
            log.error("基础设施层数据库操作异常", e);
            throw new BizException("基础设施层数据库操作异常");
        } catch (InsufficientBalanceException e) {
            //余额不足打印日志即可，不在使用消息重试
            log.error("当前原账户余额不足", e);
            throw e;
        } catch (AccountNotFoundException e) {
            //账户不存在，数据异常情况，应该进行报警统计分析处理，不用消息重试
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            //未知异常，还是要重试
            log.error("当前业务出现其他异常", e);
            throw new BizException("当前业务出现其他异常");
        }
    }

    /**
     * 根据账户ID获取账户信息。
     *
     * @param accountId 账户ID
     * @return 对应的账户对象
     */
    @LogMonitor
    public AccountBO getAccountById(String accountId) {
        // 先从缓存中获取
        AccountBO accountBO = cacheService.get(cacheService.generateKey(BIZ_TYPE, accountId));
        if (accountBO == null) {
            // 缓存中没有，从数据库获取并缓存
            accountBO = accountGateway.findByAccountId(accountId);
            if(Objects.isNull(accountBO)) {
                throw new AccountNotFoundException("账户不存在");
            }
            cacheService.add(cacheService.generateKey(BIZ_TYPE, accountId), accountBO, EXPIRATION_DAY, TimeUnit.DAYS);
        }
        return accountBO;
    }

    /**
     * 创建或更新账户记录。
     *
     * @param accountBO 账户对象，包含账户信息。
     */
    @LogMonitor
    public void createRecord(AccountBO accountBO) {
        try {
            // 获取当前最新版本号
            long version = 1;
            AccountRecordBO latestRecordOpt = accountRecordGateway
                    .findAccountByIdWithLatestVersion(accountBO.getAccountId());
            if (Objects.nonNull(latestRecordOpt)) {
                version = latestRecordOpt.getVersion() + 1;
            }

            // 创建新的记录
            AccountRecordBO recordBO = new AccountRecordBO();
            recordBO.setRecordId(UUID.randomUUID().toString());
            recordBO.setAccountId(accountBO.getAccountId());
            recordBO.setBalance(accountBO.getBalance());
            recordBO.setVersion(version);
            recordBO.setCreated(new Date());

            // 保存记录
            accountRecordGateway.save(recordBO);
        } catch (InfrastructureException ex) {
            log.error(ex.getMessage(), ex);
            throw new BizException("创建记录时,数据库操作异常");
        }

    }
}
