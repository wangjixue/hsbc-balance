package com.hsbc.balance.event;

import com.hsbc.balance.common.event.DomainEvent;
import com.hsbc.balance.common.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SpringEventBus implements EventBus {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void fire(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
