
package com.hsbc.balance.transaction.executor;

import com.alibaba.cola.dto.Response;
import com.hsbc.balance.common.ErrorCode;
import com.hsbc.balance.common.utils.JsonUtil;
import com.hsbc.balance.common.utils.ValidaterUtil;
import com.hsbc.balance.domain.accountcontext.domainservice.AccountDomainService;
import com.hsbc.balance.domain.accountcontext.entity.AccountBO;
import com.hsbc.balance.domain.transactioncontext.domainservice.TransactionDomainService;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionStatus;
import com.hsbc.balance.dto.TransactionCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * TransactionCmdExe class.
 *
 * @author jixueWang
 * @version 2025/4/26
 */
@Slf4j
@Component
public class TransactionCmdExe {

    @Autowired
    private TransactionDomainService transactionDomainService;

    @Autowired
    private AccountDomainService accountDomainService;

    public Response execute(TransactionCmd cmd) {
        try {
            Response response = checkCmd(cmd);
            if(!response.isSuccess()){
                return response;
            }
            // 创建交易记录
            TransactionBO transactionBO = new TransactionBO();
            transactionBO.setTransactionId(cmd.getTransactionId());
            transactionBO.setSourceAccountId(cmd.getSourceAccountId());
            transactionBO.setTargetAccountId(cmd.getTargetAccountId());
            transactionBO.setAmount(cmd.getAmount());
            transactionBO.setStatus(TransactionStatus.SUBMITTED);
            transactionBO.setCreated(new Date());
            transactionDomainService.processTransaction(transactionBO);

            return Response.buildSuccess();
        } catch (Exception e) {
            return Response.buildFailure(ErrorCode.SYSTEM_ERROR.getErrCode(), e.getMessage());
        }


    }

    private Response checkCmd(TransactionCmd cmd) {
        //基本校验
        Set<String> errors = ValidaterUtil.validate(cmd);
        if(!CollectionUtils.isEmpty(errors)){
            return Response.buildFailure(ErrorCode.PARAM_ERROR.getErrCode(), JsonUtil.toJson(errors));
        }
        if (cmd.getSourceAccountId().equals(cmd.getTargetAccountId())) {
            return Response.buildFailure(ErrorCode.PARAM_ERROR.getErrCode(), "source accountId and target accountId are the same");
        }
        AccountBO sourceAccount = accountDomainService.getAccountById(cmd.getSourceAccountId());
        if (Objects.isNull(sourceAccount)) {
            return Response.buildFailure(ErrorCode.PARAM_ERROR.getErrCode(),"source accountBO not found");
        }
        AccountBO targetAccount = accountDomainService.getAccountById(cmd.getTargetAccountId());
        if (Objects.isNull(targetAccount)) {
            return Response.buildFailure(ErrorCode.PARAM_ERROR.getErrCode(), "target accountBO not found");
        }
        if(transactionDomainService.checkTransactionExist(cmd.getTransactionId())){
            return Response.buildFailure(ErrorCode.TRANSACTION_EXIST.getErrCode(), "transaction is exist");
        }
        
        return Response.buildSuccess();
    }

}
