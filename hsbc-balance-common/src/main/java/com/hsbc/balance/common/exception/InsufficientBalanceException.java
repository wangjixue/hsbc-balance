package com.hsbc.balance.common.exception;

import com.alibaba.cola.exception.BaseException;

/**
 * InsufficientBalanceException
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
public class InsufficientBalanceException extends BaseException {

    /**
     * 构造一个新的 InsufficientBalanceException 实例，带有指定的错误消息。
     * @param message 错误消息。
     */
    public InsufficientBalanceException(String message) {super(message);
    }
}
