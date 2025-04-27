package com.hsbc.balance.transaction;

import com.alibaba.cola.dto.Response;
import com.hsbc.balance.api.TransactionService;
import com.hsbc.balance.dto.TransactionCmd;
import com.hsbc.balance.transaction.executor.TransactionCmdExe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TransactionServiceImpl class.
 *
 * @author jixueWang
 * @version 2025/4/26
 */
@Component
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionCmdExe transactionCmdExe;

    @Override
    public Response transfer(TransactionCmd command) {
        Response response = transactionCmdExe.execute(command);
        return response;
    }
}
