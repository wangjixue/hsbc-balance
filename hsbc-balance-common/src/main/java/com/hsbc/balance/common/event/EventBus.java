package com.hsbc.balance.common.event;


/**
 * EventBus
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
public interface EventBus {
    /**
     * 发布一个事务事件。
     * @param event 要发布的交易记录对象。
     */
    void fire(DomainEvent event);
}
