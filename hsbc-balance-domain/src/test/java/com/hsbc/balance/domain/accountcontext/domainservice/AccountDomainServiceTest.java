package com.hsbc.balance.domain.accountcontext.domainservice;

import com.hsbc.balance.common.cache.CacheService;
import com.hsbc.balance.domain.accountcontext.entity.AccountBO;
import com.hsbc.balance.domain.accountcontext.entity.AccountRecordBO;
import com.hsbc.balance.domain.accountcontext.gateway.AccountGateway;
import com.hsbc.balance.domain.accountcontext.gateway.AccountRecordGateway;
import com.hsbc.balance.domain.transactioncontext.domainservice.TransactionDomainService;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class AccountDomainServiceTest {
    @Mock
    AccountGateway accountGateway;
    @Mock
    AccountRecordGateway accountRecordGateway;
    @Mock
    CacheService cacheService;
    @Mock
    TransactionDomainService transactionDomainService;
    @InjectMocks
    AccountDomainService accountDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateAccountBalances() {
        when(accountGateway.findByAccountIdForUpdate(anyString())).thenReturn(new AccountBO());
        when(accountRecordGateway.findAccountByIdWithLatestVersion(anyString())).thenReturn(new AccountRecordBO());
        when(cacheService.generateKey(anyString(), anyString())).thenReturn("generateKeyResponse");

        accountDomainService.updateAccountBalances(new TransactionBO());
        verify(accountGateway).update(any(AccountBO.class));
        verify(accountRecordGateway).save(any(AccountRecordBO.class));
        verify(cacheService).add(anyString(), any(String.class), anyLong(), any(TimeUnit.class));
        verify(transactionDomainService).changeTransactionProcessed(anyString());
    }

    @Test
    void testGetAccountById() {
        when(accountGateway.findByAccountId(anyString())).thenReturn(new AccountBO());
        when(cacheService.generateKey(anyString(), anyString())).thenReturn("generateKeyResponse");
        when(cacheService.get(anyString())).thenReturn(new String());

        AccountBO result = accountDomainService.getAccountById("accountId");
        verify(cacheService).add(anyString(), any(String.class), anyLong(), any(TimeUnit.class));
        Assertions.assertEquals(new AccountBO(), result);
    }

    @Test
    void testCreateRecord() {
        when(accountRecordGateway.findAccountByIdWithLatestVersion(anyString())).thenReturn(new AccountRecordBO());

        accountDomainService.createRecord(new AccountBO());
        verify(accountRecordGateway).save(any(AccountRecordBO.class));
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme