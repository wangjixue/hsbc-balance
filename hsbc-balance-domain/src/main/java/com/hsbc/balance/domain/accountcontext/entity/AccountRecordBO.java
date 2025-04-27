package com.hsbc.balance.domain.accountcontext.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountRecordBO {

    private Long id;

    private String recordId;

    /**
     * 账户ID，关联到具体的账户实体。
     */
    private String accountId;

    /**
     * 当前账户的余额。
     */
    private BigDecimal balance;

    /**
     * 版本号，用于乐观锁机制，防止并发更新。
     */
    private Long version;

    /**
     * 创建时间戳，记录账户记录的创建时间。
     */
    private Date created;

    /**
     * 标记该账户记录是否已被删除。
     */
    private Boolean isDeleted;

}

