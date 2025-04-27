package com.hsbc.balance.api;

import com.alibaba.cola.dto.Response;
import com.hsbc.balance.dto.TransactionCmd;

/**
 * TransactionService
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
public interface TransactionService {
    /**
     * 处理交易命令
     * @param command 交易命令对象
     */
    Response transfer(TransactionCmd command);

}
