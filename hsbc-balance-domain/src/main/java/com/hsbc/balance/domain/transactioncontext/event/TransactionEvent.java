package com.hsbc.balance.domain.transactioncontext.event;

import com.hsbc.balance.common.event.DomainEvent;
import com.hsbc.balance.domain.transactioncontext.entity.TransactionBO;
import lombok.Data;

/**
 * TransactionEvent
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
@Data
public class TransactionEvent extends DomainEvent<TransactionBO> {

}
