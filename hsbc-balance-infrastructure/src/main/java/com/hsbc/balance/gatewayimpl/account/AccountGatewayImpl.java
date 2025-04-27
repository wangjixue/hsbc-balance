package com.hsbc.balance.gatewayimpl.account;

import com.hsbc.balance.common.exception.InfrastructureException;
import com.hsbc.balance.converter.ObjectConverter;
import com.hsbc.balance.domain.accountcontext.entity.AccountBO;
import com.hsbc.balance.domain.accountcontext.gateway.AccountGateway;
import com.hsbc.balance.repository.AccountDO;
import com.hsbc.balance.repository.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Service;

/**
 * AccountGatewayImpl class.
 *
 * @author jixueWang
 * @version 2025/4/26
 */
@Service
@Slf4j
public class AccountGatewayImpl implements AccountGateway {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    ObjectConverter objectConverter;



    @Override
    public AccountBO findByAccountIdForUpdate(String accountId) {
        try {
            AccountDO account = accountMapper.findByAccountIdForUpdate(accountId);
            if (account == null) {
                return null;
            }
            return objectConverter.accountDoToBo(account);
        } catch (CannotAcquireLockException | DeadlockLoserDataAccessException e) {
            log.error("当前更新账户出现死锁异常", e);
            throw new InfrastructureException("当前更新账户出现死锁异常", e); // 抛出异常以触发重试
        } catch (Exception ex) {
            log.error("当前查询账户信息异常", ex);
            throw new InfrastructureException("当前查询账户信息异常", ex);
        }
    }

    @Override
    public void save(AccountBO accountBO) {
        try {
            accountMapper.insert(objectConverter.accountBoToDo(accountBO));
        } catch (Exception ex) {
            log.error("当前保存账户信息异常", ex);
            throw new InfrastructureException("当前保存账户信息异常");
        }
    }

    @Override
    public void update(AccountBO accountBO) {
        try {
            accountMapper.updateBalanceByAccountId(objectConverter.accountBoToDo(accountBO));
        } catch (Exception ex) {
            log.error("当前更新账户信息异常", ex);
            throw new InfrastructureException("当前更新账户信息异常");
        }
    }

    @Override
    public AccountBO findByAccountId(String accountId) {
        try {
            AccountDO account = accountMapper.findByAccountId(accountId);
            if (account == null) {
                return null;
            }
            return objectConverter.accountDoToBo(account);
        } catch (Exception ex) {
            log.error("当前查询账户信息异常", ex);
            throw new InfrastructureException("当前查询账户信息异常");
        }
    }
}
