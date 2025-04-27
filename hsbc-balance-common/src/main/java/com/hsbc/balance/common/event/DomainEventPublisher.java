package com.hsbc.balance.common.event;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * DomainEventPublisher
 * @author jixueWang
 * @version 2025/4/26
 */
@Component
public class DomainEventPublisher {

    @Resource
    private EventBus eventBus;

    public void publish(DomainEvent domainEvent) {
        eventBus.fire(domainEvent);
    }

}
