package com.hsbc.balance.domain.transactioncontext.entity;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TransactionEvent
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
@Data
public class TransactionBO {

    private Long id;

    /**
     * 交易ID，关联到具体的交易记录。
     */
    private String transactionId;

    /**
     * 来源账户的ID，用于记录交易的来源账户。
     */
    private String sourceAccountId;

    /**
     * 目标账户的ID，用于记录交易的目标账户。
     */
    private String targetAccountId;

    /**
     * 交易金额，表示交易的具体数值。
     */
    private BigDecimal amount;

    /**
     * 交易记录的状态，表示交易记录的当前状态。
     */
    private TransactionStatus status;

    /**
     * 交易记录失败的原因，用于记录交易记录失败的具体原因。
     */
    private String failedReason;

    /**
     * 创建时间戳，记录该交易记录的创建时间。
     */
    private Date created;

    /**
     * 标记该交易记录是否已被删除。
     */
    private Boolean isDeleted;
}
