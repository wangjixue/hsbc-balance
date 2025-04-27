package com.hsbc.balance.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName account
 */
@Data
public class AccountDO implements Serializable {
    /**
     * 主键ID，自增生成
     */
    private Long id;

    /**
     * 账户ID，业务主键
     */
    private String accountId;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 当前余额
     */
    private BigDecimal balance;

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