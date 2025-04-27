package com.hsbc.balance.gatewayimpl.account;

import com.hsbc.balance.converter.ObjectConverter;
import com.hsbc.balance.domain.accountcontext.entity.AccountBO;
import com.hsbc.balance.repository.AccountDO;
import com.hsbc.balance.repository.mapper.AccountMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class AccountGatewayImplTest {
    @Mock
    AccountMapper accountMapper;
    @Mock
    ObjectConverter objectConverter;
    @InjectMocks
    AccountGatewayImpl accountGatewayImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByAccountIdForUpdate() throws Exception {
        when(accountMapper.findByAccountIdForUpdate(anyString())).thenReturn(new AccountDO());
        when(objectConverter.accountDoToBo(any(AccountDO.class))).thenReturn(new AccountBO());

        AccountBO result = accountGatewayImpl.findByAccountIdForUpdate("accountId");
        Assert.assertEquals(new AccountBO(), result);
    }

    @Test
    public void testSave() throws Exception {
        when(accountMapper.insert(any(AccountDO.class))).thenReturn(0);
        when(objectConverter.accountBoToDo(any(AccountBO.class))).thenReturn(new AccountDO());

        accountGatewayImpl.save(new AccountBO());
    }

    @Test
    public void testFindByAccountId() throws Exception {
        when(accountMapper.findByAccountId(anyString())).thenReturn(new AccountDO());
        when(objectConverter.accountDoToBo(any(AccountDO.class))).thenReturn(new AccountBO());

        AccountBO result = accountGatewayImpl.findByAccountId("accountId");
        Assert.assertEquals(new AccountBO(), result);
    }

    @org.junit.jupiter.api.Test
    void testUpdate() {
        when(accountMapper.updateBalanceByAccountId(any(AccountDO.class))).thenReturn(0);
        when(objectConverter.accountBoToDo(any(AccountBO.class))).thenReturn(new AccountDO());

        accountGatewayImpl.update(new AccountBO());
    }
}

