package com.hsbc.balance.transaction;

import com.alibaba.cola.dto.Response;
import com.hsbc.balance.dto.TransactionCmd;
import com.hsbc.balance.transaction.executor.TransactionCmdExe;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {
    @Mock
    TransactionCmdExe transactionCmdExe;
    @InjectMocks
    TransactionServiceImpl transactionServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransfer() throws Exception {
        when(transactionCmdExe.execute(any(TransactionCmd.class))).thenReturn(new Response());

        Response result = transactionServiceImpl.transfer(new TransactionCmd());
        Assert.assertEquals(new Response().isSuccess(), result.isSuccess());
    }
}

