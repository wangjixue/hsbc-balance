package com.hsbc.balance.domain.transactioncontext.domainservice;

import com.hsbc.balance.common.event.DomainEvent;
import com.hsbc.balance.common.event.DomainEventPublisher;
import com.hsbc.balance.domain.accountcontext.gateway.AccountGateway;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionStatus;
import com.hsbc.balance.domain.transactioncontext.gateway.TransactionGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class TransactionDomainServiceTest {
    @Mock
    DomainEventPublisher domainEventPublisher;
    @Mock
    TransactionGateway transactionGateway;
    @Mock
    AccountGateway accountGateway;
    @InjectMocks
    TransactionDomainService transactionDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessTransaction() {
        transactionDomainService.processTransaction(new TransactionBO());
        verify(domainEventPublisher).publish(any(DomainEvent.class));
        verify(transactionGateway).save(any(TransactionBO.class));
    }

    @Test
    void testChangeTransactionProcessed() {
        transactionDomainService.changeTransactionProcessed("transactionId");
        verify(transactionGateway).updateStatusByTransactionId(anyString(), any(TransactionStatus.class), anyString());
    }

    @Test
    void testChangeTransactionFailed() {
        transactionDomainService.changeTransactionFailed("transactionId", "failedReason");
        verify(transactionGateway).updateStatusByTransactionId(anyString(), any(TransactionStatus.class), anyString());
    }

    @Test
    void testChangeTransactionProcessing() {
        transactionDomainService.changeTransactionProcessing("transactionId", "failedReason");
        verify(transactionGateway).updateStatusByTransactionId(anyString(), any(TransactionStatus.class), anyString());
    }

    @Test
    void testCheckTransactionProcessed() {
        when(transactionGateway.findByTransactionId(anyString())).thenReturn(new TransactionBO());

        Boolean result = transactionDomainService.checkTransactionProcessed("transactionId");
        Assertions.assertEquals(Boolean.TRUE, result);
    }

    @Test
    void testCheckTransactionExist() {
        when(transactionGateway.findByTransactionId(anyString())).thenReturn(new TransactionBO());

        Boolean result = transactionDomainService.checkTransactionExist("transactionId");
        Assertions.assertEquals(Boolean.TRUE, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme