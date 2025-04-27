package com.hsbc.balance.domain.accountcontext.gateway;

import com.hsbc.balance.common.exception.InfrastructureException;
import com.hsbc.balance.domain.accountcontext.entity.AccountRecordBO;

/**
 * AccountRecordGateway
 *
 * @author jixueWang
 * @version 2025/4/26
 *
 **/
public interface AccountRecordGateway {

    /**
     * 根据账户ID查找最新版本的账户记录。
     * @param accountId 账户ID。
     * @return 最新版本的账户记录，若不存在则返回空。
     * @throws InfrastructureException 当基础设施出现异常时抛出。
     */
    AccountRecordBO findAccountByIdWithLatestVersion(String accountId) throws InfrastructureException;

    /**
     * 保存账户记录。
     * @param accountRecordBO 要保存的账户记录对象。
     */
    void save(AccountRecordBO accountRecordBO);
}
