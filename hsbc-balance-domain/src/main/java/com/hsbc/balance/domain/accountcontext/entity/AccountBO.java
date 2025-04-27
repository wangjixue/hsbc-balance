package com.hsbc.balance.domain.accountcontext.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class AccountBO implements Serializable {


    private Long id;


    private String accountId;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 账户当前余额
     */
    private BigDecimal balance;

    /**
     * 创建时间戳，记录账户创建的时间点。
     */
    private Date created;

    /**
     * 最后更新时间戳，记录账户信息最后一次被修改的时间点。
     */
    private Date modified;

    /**
     * 标记账户是否已被删除。
     */
    private Boolean isDeleted;

}
