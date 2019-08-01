package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.StockItemHoldingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockItemHoldings and its DTO StockItemHoldingsDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class})
public interface StockItemHoldingsMapper extends EntityMapper<StockItemHoldingsDTO, StockItemHoldings> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    StockItemHoldingsDTO toDto(StockItemHoldings stockItemHoldings);

    @Mapping(source = "productId", target = "product")
    StockItemHoldings toEntity(StockItemHoldingsDTO stockItemHoldingsDTO);

    default StockItemHoldings fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItemHoldings stockItemHoldings = new StockItemHoldings();
        stockItemHoldings.setId(id);
        return stockItemHoldings;
    }
}
