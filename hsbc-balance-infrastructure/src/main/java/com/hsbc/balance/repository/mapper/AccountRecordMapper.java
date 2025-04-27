package com.hsbc.balance.repository.mapper;

import com.hsbc.balance.repository.AccountRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
* @author jixue
* @description 针对表【account_record】的数据库操作Mapper
* @createDate 2025-04-26
* @Entity com.hsbc.balance.repository.AccountRecord
*/
@Mapper
public interface AccountRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AccountRecordDO record);

    int insertSelective(AccountRecordDO record);

    AccountRecordDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountRecordDO record);

    int updateByPrimaryKey(AccountRecordDO record);

    AccountRecordDO findAccountByIdWithLatestVersion(String accountId);
}
