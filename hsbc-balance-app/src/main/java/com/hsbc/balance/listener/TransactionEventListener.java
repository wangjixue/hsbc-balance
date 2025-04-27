package com.hsbc.balance.listener;

import com.hsbc.balance.common.exception.AccountNotFoundException;
import com.hsbc.balance.common.exception.InsufficientBalanceException;
import com.hsbc.balance.common.exception.TransactionNotFoundException;
import com.hsbc.balance.common.utils.JsonUtil;
import com.hsbc.balance.domain.accountcontext.domainservice.AccountDomainService;
import com.hsbc.balance.domain.transactioncontext.domainservice.TransactionDomainService;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import com.hsbc.balance.domain.transactioncontext.event.TransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * TransactionEventListener
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
@Component
@Slf4j
public class TransactionEventListener {

    /**
     * 账户服务对象，用于更新账户余额。
     */
    @Autowired
    private AccountDomainService accountDomainService;

    /**
     * 交易服务对象，用于在事务事件中处理交易相关操作。
     */
    @Autowired
    private TransactionDomainService transactionDomainService;

    /**
     * 异步处理交易事件
     *
     * @param transactionEvent 事件
     */
    @Async("eventTaskExecutor")
    @EventListener
    public void localHandleEvent(TransactionEvent transactionEvent) {
        execute(transactionEvent);
    }

    /**
     * 执行交易命令，更新账户余额。
     *
     * @param transactionEvent 事件
     */
    private void execute(TransactionEvent transactionEvent) {
        TransactionBO transactionBO = transactionEvent.getEventData();
        if(transactionBO == null){
            log.error("transactionBO is null event={}", JsonUtil.toJson(transactionEvent));
            return;
        }
        try {
            //幂等性校验,transactionEvent, 则直接返回
            if (transactionDomainService.checkTransactionProcessed(transactionEvent.getEventData().getTransactionId())) {
                return;
            }
            accountDomainService.updateAccountBalances(transactionEvent.getEventData());
        } catch (TransactionNotFoundException e) {
            log.error("当前transactionId不存在, 请检查, command={}", JsonUtil.toJson(transactionEvent) ,e);
        } catch (InsufficientBalanceException | AccountNotFoundException e) {
            //当前余额不足, 业务账号不存在. 属于业务异常,mq不抛出重试.
            transactionDomainService.changeTransactionFailed(transactionBO.getTransactionId(), e.getMessage());
        } catch (Exception e) {
            log.error("TransactionEventListener execute error", e);
            //交易的其他异常, 先更新为失败, 然后抛出异常, 让mq重试.
            transactionDomainService.changeTransactionProcessing(transactionBO.getTransactionId(), e.getMessage());
            throw e;
        }

    }


}
