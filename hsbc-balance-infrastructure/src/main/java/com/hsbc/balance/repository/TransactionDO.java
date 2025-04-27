package com.hsbc.balance.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName transaction
 */
@Data
public class TransactionDO implements Serializable {
    /**
     * 主键ID，自增生成
     */
    private Long id;

    /**
     * 交易ID，业务主键
     */
    private String transactionId;

    /**
     * 源账户ID
     */
    private String sourceAccountId;

    /**
     * 目标账户ID
     */
    private String targetAccountId;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 状态: 0已提交,1成功,2处理中,3失败
     */
    private Integer status;

    /**
     * 如果交易失败,失败原因;成功则为空
     */
    private String failedReason;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date modified;

    /**
     * 删除标记 1
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}