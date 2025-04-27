package com.hsbc.balance.domain.transactioncontext.domainservice;


import com.alibaba.cola.exception.BizException;
import com.hsbc.balance.common.aop.annotation.LogMonitor;
import com.hsbc.balance.common.event.DomainEventPublisher;
import com.hsbc.balance.common.exception.TransactionNotFoundException;
import com.hsbc.balance.domain.accountcontext.gateway.AccountGateway;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionStatus;
import com.hsbc.balance.domain.transactioncontext.event.TransactionEvent;
import com.hsbc.balance.domain.transactioncontext.gateway.TransactionGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * TransactionServiceImpl
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
@Service
@Slf4j
public class TransactionDomainService {

    /**
     * 事件总线，用于发布交易记录。
     */
    @Autowired
    private DomainEventPublisher domainEventPublisher;

    /**
     * 提供交易记录的保存和发布功能。
     */
    @Autowired
    private TransactionGateway transactionGateway;

    /**
     * 账户外观服务，提供账户相关的操作接口。
     */
    @Autowired
    private AccountGateway accountGateway;

    /**
     * 创建并发布交易记录。
     *
     * @param transactionBO 交易命令对象，包含交易的详细信息。
     */
    @Transactional
    @LogMonitor
    public void processTransaction(TransactionBO transactionBO) {

        //校验交易是否执行过

        // 保存交易记录
        transactionGateway.save(transactionBO);

        // 发布事件

        TransactionEvent event = new TransactionEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventType("TRANSACTION_CREATED");
        event.setEventData(transactionBO);
        event.setEventTime(new Date());
        domainEventPublisher.publish(event);
    }

    /**
     * 更新交易状态为已处理。
     *
     * @param transactionId 交易ID。
     */
    @LogMonitor
    public void changeTransactionProcessed(String transactionId) {
        try {
            transactionGateway.updateStatusByTransactionId(transactionId, TransactionStatus.PROCESSED, StringUtils.EMPTY);
        } catch (Exception ex) {
            log.error("当前数据库更新交易状态异常", ex);
            throw new BizException("当前数据库更新交易状态异常");
        }
    }

    /**
     * 更新交易状态为失败，并记录失败原因。
     *
     * @param transactionId 交易ID
     * @param failedReason    失败原因
     */
    @LogMonitor
    public void changeTransactionFailed(String transactionId, String failedReason) {
        try {
            transactionGateway.updateStatusByTransactionId(transactionId, TransactionStatus.FAILED, failedReason);
        } catch (Exception ex) {
            log.error("当前数据库更新交易状态异常", ex);
            throw new BizException("当前数据库更新交易状态异常");
        }

    }

    /**
     * 更新交易状态为异常处理中，并记录失败原因。
     *
     * @param transactionId 交易ID
     * @param failedReason    失败原因
     */
    @LogMonitor
    public void changeTransactionProcessing(String transactionId, String failedReason) {
        try {
            transactionGateway.updateStatusByTransactionId(transactionId, TransactionStatus.PROCESSING, failedReason);
        } catch (Exception ex) {
            log.error("当前数据库更新交易状态异常", ex);
            throw new BizException("当前数据库更新交易状态异常");
        }

    }

    /**
     * 检查交易是否已经处理。
     *
     * @param transactionId 交易ID
     * @return true 如果交易已经处理，false 否则
     */
    public Boolean checkTransactionProcessed(String transactionId) {
        try {
            TransactionBO transactionBO = transactionGateway.findByTransactionId(transactionId);
            if (Objects.isNull(transactionBO)) {
                throw new TransactionNotFoundException("当前transactionId非法");
            }
            return (transactionBO.getStatus() != null
                    && transactionBO.getStatus() == TransactionStatus.PROCESSED);
        } catch (TransactionNotFoundException ex) {
            log.error("当前transactionId非法", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("当前数据库更新交易状态异常", ex);
            throw new BizException("当前数据库更新交易状态异常");
        }
    }

    public Boolean checkTransactionExist(String transactionId) {
        try {
            TransactionBO transactionBO = transactionGateway.findByTransactionId(transactionId);
            if (Objects.isNull(transactionBO)) {
                return false;
            }
            return true;
        } catch (TransactionNotFoundException ex) {
            log.error("当前transactionId非法", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("当前数据库更新交易状态异常", ex);
            throw new BizException("当前数据库更新交易状态异常");
        }
    }
}

