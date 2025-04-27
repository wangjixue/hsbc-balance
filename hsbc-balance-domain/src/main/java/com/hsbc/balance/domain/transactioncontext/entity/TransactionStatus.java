package com.hsbc.balance.domain.transactioncontext.entity;

import lombok.Getter;

/**
 *
 */
@Getter
public enum TransactionStatus {
    /**
     * 提交完成的交易状态。
     */
    SUBMITTED(0,"提交完成"),

    /**
     * 表示事务处理完成的状态。
     */
    PROCESSED(1,"处理完成"),
    /**
     * 表示事务异常重试中的状态。
     */
    PROCESSING(2,"处理中"),

    /**
     * 处理失败的交易状态。
     */
    FAILED(3,"处理失败");

    /**
     * 交易状态对应值。
     */
    private final int code;
    /**
     * 事务状态的描述信息。
     */
    private final String description;

    /**
     * 创建一个新的 TransactionStatus 对象，用于描述交易状态。
     * @param description 描述交易状态的字符串。
     */
    TransactionStatus(int code,String description) {
        this.code = code;
        this.description = description;
    }

}

