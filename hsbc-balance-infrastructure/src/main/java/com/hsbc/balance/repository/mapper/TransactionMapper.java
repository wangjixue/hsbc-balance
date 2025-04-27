package com.hsbc.balance.repository.mapper;

import com.hsbc.balance.repository.TransactionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author jixue
* @description 针对表【transaction】的数据库操作Mapper
* @createDate 2025-04-26
* @Entity com.hsbc.balance.repository.Transaction
*/
@Mapper
public interface TransactionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TransactionDO transaction);

    int insertSelective(TransactionDO transaction);

    TransactionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransactionDO transaction);

    int updateByPrimaryKey(TransactionDO transaction);

    TransactionDO findByTransactionId(String transactionId);

    int updateStatusByTransactionId(@Param("transactionId") String transactionId, @Param("status") int status, @Param("failedReason") String failedReason);
}
