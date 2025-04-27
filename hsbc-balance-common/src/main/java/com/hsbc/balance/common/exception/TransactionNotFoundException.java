package com.hsbc.balance.common.exception;

import com.alibaba.cola.exception.BaseException;

/**
 * TransactionNotFoundException
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
public class TransactionNotFoundException extends BaseException {
    /**
     * 构造一个TransactionNotFoundException异常对象。
     * @param message 异常信息。
     */
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
