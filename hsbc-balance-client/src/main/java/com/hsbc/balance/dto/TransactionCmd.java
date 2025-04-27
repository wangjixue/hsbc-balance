package com.hsbc.balance.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * TransactionCommand
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
@Data
public class TransactionCmd extends Command {
    /**
     * 交易ID，用于唯一标识每一笔交易。
     */
    @NotEmpty(message = "transactionId is not empty")
    private String transactionId;

    /**
     * 源账户ID，表示交易的发起方账户。
     */
    @NotEmpty(message = "sourceAccountId is not empty")
    private String sourceAccountId;

    /**
     * 目标账户ID，表示交易的接收方账户。
     */
    @NotEmpty(message = "targetAccountId is not empty")
    private String targetAccountId;

    /**
     * 交易金额，表示本次交易的金额大小。
     */
    @NotNull(message = "amount is not empty")
    @Positive(message = "amount must be greater than 0")
    private BigDecimal amount;
}

