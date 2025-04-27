package com.hsbc.balance.gatewayimpl.transaction;

import com.hsbc.balance.common.exception.InfrastructureException;
import com.hsbc.balance.converter.ObjectConverter;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionStatus;
import com.hsbc.balance.domain.transactioncontext.gateway.TransactionGateway;
import com.hsbc.balance.repository.TransactionDO;
import com.hsbc.balance.repository.mapper.AccountMapper;
import com.hsbc.balance.repository.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TransactionGatewayImpl class.
 *
 * @author jixueWang
 * @version 2025/4/26
 */
@Service
@Slf4j
public class TransactionGatewayImpl implements TransactionGateway {

    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    ObjectConverter objectConverter;
    
    @Override
    public void save(TransactionBO transactionBO) {
        try{
            transactionMapper.insert(objectConverter.transactionBoToDo(transactionBO));
        } catch (Exception ex) {
            log.error("当前数据库保存交易信息异常", ex);
            throw new InfrastructureException("当前数据库保存交易信息异常");
        }
    }

    @Override
    public void updateStatusByTransactionId(String transactionId, TransactionStatus status, String failedReason) {
        try{
            int updateNum = transactionMapper.updateStatusByTransactionId(transactionId, status.getCode(), failedReason);
            if(updateNum > 0) {
                log.info("根据交易ID更新交易状态成功, transactionId={}, status={}", transactionId, status);
            } else {
                log.error("未更新成功, 可能transactionId={}不存在", transactionId);
            }

        } catch (Exception ex) {
            log.error("当前数据库更新状态异常", ex);
            throw new InfrastructureException("changeTransactionProcessed");
        }
    }

    @Override
    public TransactionBO findByTransactionId(String transactionId) {
        try{
            TransactionDO  transactionDO = transactionMapper.findByTransactionId(transactionId);
            if(transactionDO == null) {
                log.error("未找到transactionId={}的交易记录", transactionId);
                return null;
            }
            return objectConverter.transactionDoToBo(transactionDO);
        } catch (Exception ex) {
            log.error("当前数据库状态异常", ex);
            throw new InfrastructureException("changeTransactionProcessed");
        }
    }
}
