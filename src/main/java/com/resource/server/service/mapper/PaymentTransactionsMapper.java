package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.PaymentTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaymentTransactions and its DTO PaymentTransactionsDTO.
 */
@Mapper(componentModel = "spring", uses = {OrdersMapper.class})
public interface PaymentTransactionsMapper extends EntityMapper<PaymentTransactionsDTO, PaymentTransactions> {

    @Mapping(source = "order.id", target = "orderId")
    PaymentTransactionsDTO toDto(PaymentTransactions paymentTransactions);

    @Mapping(source = "orderId", target = "order")
    PaymentTransactions toEntity(PaymentTransactionsDTO paymentTransactionsDTO);

    default PaymentTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentTransactions paymentTransactions = new PaymentTransactions();
        paymentTransactions.setId(id);
        return paymentTransactions;
    }
}
