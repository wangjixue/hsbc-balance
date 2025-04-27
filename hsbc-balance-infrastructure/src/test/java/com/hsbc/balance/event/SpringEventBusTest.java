package com.hsbc.balance.event;

import com.hsbc.balance.common.event.DomainEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.*;

class SpringEventBusTest {
    @Mock
    ApplicationEventPublisher applicationEventPublisher;
    @InjectMocks
    SpringEventBus springEventBus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFire() {
        springEventBus.fire(new DomainEvent());
        verify(applicationEventPublisher).publishEvent(any(Object.class));
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme