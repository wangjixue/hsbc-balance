package com.hsbc.balance.gatewayimpl.transaction;

import com.hsbc.balance.converter.ObjectConverter;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionStatus;
import com.hsbc.balance.repository.TransactionDO;
import com.hsbc.balance.repository.mapper.TransactionMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class TransactionGatewayImplTest {
    @Mock
    TransactionMapper transactionMapper;
    @Mock
    ObjectConverter objectConverter;
    @InjectMocks
    TransactionGatewayImpl transactionGatewayImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        when(transactionMapper.insert(any(TransactionDO.class))).thenReturn(0);
        when(objectConverter.transactionBoToDo(any(TransactionBO.class))).thenReturn(new TransactionDO());

        transactionGatewayImpl.save(new TransactionBO());
    }

    @Test
    void testUpdateStatusByTransactionId() {
        when(transactionMapper.updateStatusByTransactionId(anyString(), anyInt(), anyString())).thenReturn(0);

        transactionGatewayImpl.updateStatusByTransactionId("transactionId", TransactionStatus.SUBMITTED, "failedReason");
    }

    @Test
    void testFindByTransactionId() {
        when(transactionMapper.findByTransactionId(anyString())).thenReturn(new TransactionDO());
        when(objectConverter.transactionDoToBo(any(TransactionDO.class))).thenReturn(new TransactionBO());

        TransactionBO result = transactionGatewayImpl.findByTransactionId("transactionId");
        Assertions.assertEquals(new TransactionBO(), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme