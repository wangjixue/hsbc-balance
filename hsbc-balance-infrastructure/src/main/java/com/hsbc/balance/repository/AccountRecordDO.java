package com.hsbc.balance.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName account_record
 */
@Data
public class AccountRecordDO implements Serializable {
    /**
     * 主键ID，自增生成
     */
    private Long id;

    /**
     * 记录ID，业务主键
     */
    private String recordId;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 记录时的余额
     */
    private BigDecimal balance;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 删除标记 1
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}