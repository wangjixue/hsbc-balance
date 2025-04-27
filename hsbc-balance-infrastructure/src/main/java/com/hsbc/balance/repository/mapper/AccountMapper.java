package com.hsbc.balance.repository.mapper;

import com.hsbc.balance.repository.AccountDO;
import org.apache.ibatis.annotations.Mapper;

/**
* @author jixue
* @description 针对表【account】的数据库操作Mapper
* @createDate 2025-04-26
* @Entity com.hsbc.balance.repository.Account
*/

@Mapper
public interface AccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AccountDO account);

    int insertSelective(AccountDO account);

    AccountDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountDO account);

    int updateByPrimaryKey(AccountDO account);

    AccountDO findByAccountIdForUpdate(String accountId);

    AccountDO findByAccountId(String accountId);

    int updateBalanceByAccountId(AccountDO account);

}
