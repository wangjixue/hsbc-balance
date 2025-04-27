package com.hsbc.balance.gatewayimpl.account;

import com.hsbc.balance.converter.ObjectConverter;
import com.hsbc.balance.domain.accountcontext.entity.AccountRecordBO;
import com.hsbc.balance.repository.AccountRecordDO;
import com.hsbc.balance.repository.mapper.AccountRecordMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class AccountRecordGatewayImplTest {
    @Mock
    AccountRecordMapper accountRecordMapper;
    @Mock
    ObjectConverter objectConverter;
    @InjectMocks
    AccountRecordGatewayImpl accountRecordGatewayImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAccountByIdWithLatestVersion() {
        when(accountRecordMapper.findAccountByIdWithLatestVersion(anyString())).thenReturn(new AccountRecordDO());
        when(objectConverter.accountRecordDoToBo(any(AccountRecordDO.class))).thenReturn(new AccountRecordBO());

        AccountRecordBO result = accountRecordGatewayImpl.findAccountByIdWithLatestVersion("accountId");
        Assertions.assertEquals(new AccountRecordBO(), result);
    }

    @Test
    void testSave() {
        when(accountRecordMapper.insert(any(AccountRecordDO.class))).thenReturn(0);
        when(objectConverter.accountRecordBoToDo(any(AccountRecordBO.class))).thenReturn(new AccountRecordDO());

        accountRecordGatewayImpl.save(new AccountRecordBO());
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme