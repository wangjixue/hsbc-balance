package com.hsbc.balance.common.event;

import lombok.Data;

import java.util.Date;

@Data
public class DomainEvent<T> {
    String eventId;          // 唯一标识
    String eventType;     // 事件类型
    T eventData;               // 业务数据负载
    /**
     * 时间戳
     */
    Date eventTime;
}
