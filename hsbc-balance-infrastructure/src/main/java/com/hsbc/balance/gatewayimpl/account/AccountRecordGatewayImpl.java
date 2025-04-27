package com.hsbc.balance.gatewayimpl.account;

import com.hsbc.balance.common.exception.InfrastructureException;
import com.hsbc.balance.converter.ObjectConverter;
import com.hsbc.balance.domain.accountcontext.entity.AccountRecordBO;
import com.hsbc.balance.domain.accountcontext.gateway.AccountRecordGateway;
import com.hsbc.balance.repository.AccountRecordDO;
import com.hsbc.balance.repository.mapper.AccountRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AccountRecordGatewayImpl class.
 *
 * @author jixueWang
 * @version 2025/4/26
 */
@Service
@Slf4j
public class AccountRecordGatewayImpl implements AccountRecordGateway {

    @Autowired
    private AccountRecordMapper accountRecordMapper;

    @Autowired
    ObjectConverter  objectConverter;

    @Override
    public AccountRecordBO findAccountByIdWithLatestVersion(String accountId) {

        try{
            AccountRecordDO accountRecordDO = accountRecordMapper.findAccountByIdWithLatestVersion(accountId);
            if (accountRecordDO == null) {
                return null;
            }
            return objectConverter.accountRecordDoToBo(accountRecordDO);
        } catch (Exception ex) {
            log.error("当前数据库获取账号记录信息异常", ex);
            throw new InfrastructureException("当前数据库获取账号记录信息异常");
        }
    }

    @Override
    public void save(AccountRecordBO accountRecordBO) {
        try{
            accountRecordMapper.insert(objectConverter.accountRecordBoToDo(accountRecordBO));
        } catch (Exception ex) {
            log.error("当前数据库保存账号记录信息异常", ex);
            throw new InfrastructureException("当前数据库保存账号记录信息异常");
        }
    }
}
