package com.hsbc.balance.domain.transactioncontext.gateway;

import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionStatus;

/**
 * TransactionGateway
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
public interface TransactionGateway {
    /**
     * 保存交易记录。
     * @param transactionBO 交易记录对象。
     */
    void save(TransactionBO transactionBO);

    void updateStatusByTransactionId(String transactionId, TransactionStatus status, String failedReason);

    TransactionBO findByTransactionId(String transactionId);
}
