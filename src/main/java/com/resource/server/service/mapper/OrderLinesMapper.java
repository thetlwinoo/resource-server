package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.OrderLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderLines and its DTO OrderLinesDTO.
 */
@Mapper(componentModel = "spring", uses = {PackageTypesMapper.class, ProductsMapper.class, OrdersMapper.class})
public interface OrderLinesMapper extends EntityMapper<OrderLinesDTO, OrderLines> {

    @Mapping(source = "packageType.id", target = "packageTypeId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "order.id", target = "orderId")
    OrderLinesDTO toDto(OrderLines orderLines);

    @Mapping(source = "packageTypeId", target = "packageType")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "orderId", target = "order")
    OrderLines toEntity(OrderLinesDTO orderLinesDTO);

    default OrderLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderLines orderLines = new OrderLines();
        orderLines.setId(id);
        return orderLines;
    }
}
