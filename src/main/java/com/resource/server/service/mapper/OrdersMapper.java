package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Orders and its DTO OrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {ReviewsMapper.class, CustomersMapper.class, AddressesMapper.class, ShipMethodMapper.class, CurrencyRateMapper.class, SpecialDealsMapper.class})
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {

    @Mapping(source = "orderReview.id", target = "orderReviewId")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "shipToAddress.id", target = "shipToAddressId")
    @Mapping(source = "billToAddress.id", target = "billToAddressId")
    @Mapping(source = "shipMethod.id", target = "shipMethodId")
    @Mapping(source = "shipMethod.shipMethodName", target = "shipMethodShipMethodName")
    @Mapping(source = "currencyRate.id", target = "currencyRateId")
    @Mapping(source = "specialDeals.id", target = "specialDealsId")
    OrdersDTO toDto(Orders orders);

    @Mapping(source = "orderReviewId", target = "orderReview")
    @Mapping(target = "orderLineLists", ignore = true)
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "shipToAddressId", target = "shipToAddress")
    @Mapping(source = "billToAddressId", target = "billToAddress")
    @Mapping(source = "shipMethodId", target = "shipMethod")
    @Mapping(source = "currencyRateId", target = "currencyRate")
    @Mapping(target = "payment", ignore = true)
    @Mapping(source = "specialDealsId", target = "specialDeals")
    Orders toEntity(OrdersDTO ordersDTO);

    default Orders fromId(Long id) {
        if (id == null) {
            return null;
        }
        Orders orders = new Orders();
        orders.setId(id);
        return orders;
    }
}
