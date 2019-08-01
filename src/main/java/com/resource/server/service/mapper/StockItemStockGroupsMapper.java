package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.StockItemStockGroupsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockItemStockGroups and its DTO StockItemStockGroupsDTO.
 */
@Mapper(componentModel = "spring", uses = {StockGroupsMapper.class, ProductsMapper.class})
public interface StockItemStockGroupsMapper extends EntityMapper<StockItemStockGroupsDTO, StockItemStockGroups> {

    @Mapping(source = "stockGroup.id", target = "stockGroupId")
    @Mapping(source = "stockGroup.stockGroupName", target = "stockGroupStockGroupName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    StockItemStockGroupsDTO toDto(StockItemStockGroups stockItemStockGroups);

    @Mapping(source = "stockGroupId", target = "stockGroup")
    @Mapping(source = "productId", target = "product")
    StockItemStockGroups toEntity(StockItemStockGroupsDTO stockItemStockGroupsDTO);

    default StockItemStockGroups fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItemStockGroups stockItemStockGroups = new StockItemStockGroups();
        stockItemStockGroups.setId(id);
        return stockItemStockGroups;
    }
}
