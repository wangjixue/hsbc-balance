package com.hsbc.balance.converter;

import com.hsbc.balance.domain.accountcontext.entity.AccountBO;
import com.hsbc.balance.domain.accountcontext.entity.AccountRecordBO;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionStatus;
import com.hsbc.balance.repository.AccountDO;
import com.hsbc.balance.repository.AccountRecordDO;
import com.hsbc.balance.repository.TransactionDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * ObjectConverter class.
 *
 * @author jixueWang
 * @version 2025/4/27
 */
@Mapper(componentModel = "spring")  // 启用 Spring 组件模型
public interface ObjectConverter {

    @Named("booleanToInt")
    public static Integer booleanToInt(Boolean value) {
        return value != null && value ? 1 : 0;  // true → 1，false → 0
    }

    @Named("intToBoolean")
    public static Boolean intToBoolean(Integer value) {
        return value != null && value == 1;  // 1 → true，其他值 → false
    }

    // 枚举转 code
    @Named("statusToCode")
    public static int statusToCode(TransactionStatus status) {
        return status != null ? status.getCode() : -1; // 可自定义 null 值兜底逻辑
    }

    // code 转枚举
    @Named("codeToStatus")
    public static TransactionStatus codeToStatus(int code) {
        for (TransactionStatus status : TransactionStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null; // 或抛出异常（根据业务需求）
    }


    @Mapping(
            target = "isDeleted",source = "isDeleted",qualifiedByName = "booleanToInt"  // 引用自定义方法
    )
    AccountDO accountBoToDo(AccountBO accountBO);

    @Mapping(
            target = "isDeleted",source = "isDeleted",qualifiedByName = "intToBoolean"  // 引用自定义方法
    )
    AccountBO accountDoToBo(AccountDO accountDO);

    @Mapping(
            target = "isDeleted",source = "isDeleted",qualifiedByName = "booleanToInt"  // 引用自定义方法
    )
    AccountRecordDO accountRecordBoToDo(AccountRecordBO accountRecordBO);

    @Mapping(
            target = "isDeleted",source = "isDeleted",qualifiedByName = "intToBoolean"  // 引用自定义方法
    )
    AccountRecordBO accountRecordDoToBo(AccountRecordDO accountRecordDO);

    @Mappings({
            @Mapping(target = "isDeleted",source = "isDeleted",qualifiedByName = "booleanToInt"),
            @Mapping(target = "status",source = "status",qualifiedByName = "statusToCode")
    })
    TransactionDO transactionBoToDo(TransactionBO transactionBO);

    @Mappings({
            @Mapping(target = "isDeleted",source = "isDeleted",qualifiedByName = "intToBoolean"),
            @Mapping(target = "status",source = "status",qualifiedByName = "codeToStatus")
    })
    TransactionBO transactionDoToBo(TransactionDO transactionDO);

}
