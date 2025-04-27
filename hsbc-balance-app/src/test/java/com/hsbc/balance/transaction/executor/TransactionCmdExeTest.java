package com.hsbc.balance.transaction.executor;

import com.alibaba.cola.dto.Response;
import com.hsbc.balance.domain.accountcontext.domainservice.AccountDomainService;
import com.hsbc.balance.domain.accountcontext.entity.AccountBO;
import com.hsbc.balance.domain.transactioncontext.domainservice.TransactionDomainService;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import com.hsbc.balance.dto.TransactionCmd;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TransactionCmdExeTest {
    @Mock
    TransactionDomainService transactionDomainService;
    @Mock
    AccountDomainService accountDomainService;
    @InjectMocks
    TransactionCmdExe transactionCmdExe;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() throws Exception {
        when(accountDomainService.getAccountById(anyString())).thenReturn(new AccountBO());

        Response result = transactionCmdExe.execute(new TransactionCmd());
        verify(transactionDomainService).processTransaction(any(TransactionBO.class));
        Assert.assertEquals(new Response().isSuccess(), result.isSuccess());
    }
}