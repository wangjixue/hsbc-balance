package com.hsbc.balance.common.exception;

import com.alibaba.cola.exception.BaseException;

/**
 * AccountNotFoundException
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
public class AccountNotFoundException extends BaseException {
    /**
     * 构造一个带有错误消息的 AccountNotFoundException 异常对象。
     * @param errMessage 错误消息字符串。
     */
    public AccountNotFoundException(String errMessage) {
        super(errMessage);
    }
}
