package com.hsbc.balance.domain.accountcontext.gateway;


import com.hsbc.balance.domain.accountcontext.entity.AccountBO;

import java.util.Optional;

/**
 * AccountFacadeService
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
public interface AccountGateway {
    /**
     * 根据账户ID查找并锁定账户，用于更新操作。
     * @param accountId 账户ID
     * @return 找到的账户实例，若不存在则返回空
     */
    AccountBO findByAccountIdForUpdate(String accountId);

    /**
     * 保存账户信息
     * @param accountBO 要保存的账户对象
     */
    void save(AccountBO accountBO);

    /**
     * 更新账户信息
     * @param accountBO 要保存的账户对象
     */
    void update(AccountBO accountBO);

    /**
     * 根据账户ID查找账户信息。
     * @param accountId 账户ID。
     * @return 对应的账户信息，若不存在则返回空。
     */
    AccountBO findByAccountId(String accountId);
}
