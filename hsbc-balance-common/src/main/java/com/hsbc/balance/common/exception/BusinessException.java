package com.hsbc.balance.common.exception;

import com.alibaba.cola.exception.BaseException;

/**
 * BusinessException
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
public class BusinessException extends BaseException {
    /**
     * 构造一个新的业务异常对象，带有指定的错误消息。
     * @param message 错误消息。
     */
    public BusinessException(String message) {
        super(message);
    }
}
