package com.hsbc.balance.common.exception;

import com.alibaba.cola.exception.BaseException;

/**
 * InfrastructureException
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
public class InfrastructureException extends BaseException {
    /**
     * 构造一个带有指定消息的InfrastructureException对象。
     * @param message 异常的详细信息。
     */
    public InfrastructureException(String message) {
        super(message);
    }
    public InfrastructureException(String message, Throwable ex) {
        super(message, ex);
    }
}
