package com.hsbc.balance.common;

/**
 * @author jixueWang
 * @version 2025/4/26
 */
public enum ErrorCode{
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),
    PARAM_ERROR("PARAM_ERROR", "参数异常"),
    BALANCE_NOT_ENOUGH("BALANCE_NOT_ENOUGH", "余额不足"),
    BALANCE_NOT_EXIST("BALANCE_NOT_EXIST", "余额不存在"),
    TRANSACTION_EXIST("TRANSACTION_EXIST", "交易已存在"),


    ;

    private final String errCode;
    private final String errDesc;

    private ErrorCode(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }
}
