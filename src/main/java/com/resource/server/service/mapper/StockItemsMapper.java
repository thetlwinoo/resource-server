package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.StockItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockItems and its DTO StockItemsDTO.
 */
@Mapper(componentModel = "spring", uses = {ReviewLinesMapper.class, ProductsMapper.class, UnitMeasureMapper.class, ProductAttributeMapper.class, ProductOptionMapper.class})
public interface StockItemsMapper extends EntityMapper<StockItemsDTO, StockItems> {

    @Mapping(source = "reviewLine.id", target = "reviewLineId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "lengthUnitMeasureCode.id", target = "lengthUnitMeasureCodeId")
    @Mapping(source = "lengthUnitMeasureCode.unitMeasureCode", target = "lengthUnitMeasureCodeUnitMeasureCode")
    @Mapping(source = "weightUnitMeasureCode.id", target = "weightUnitMeasureCodeId")
    @Mapping(source = "weightUnitMeasureCode.unitMeasureCode", target = "weightUnitMeasureCodeUnitMeasureCode")
    @Mapping(source = "widthUnitMeasureCode.id", target = "widthUnitMeasureCodeId")
    @Mapping(source = "widthUnitMeasureCode.unitMeasureCode", target = "widthUnitMeasureCodeUnitMeasureCode")
    @Mapping(source = "heightUnitMeasureCode.id", target = "heightUnitMeasureCodeId")
    @Mapping(source = "heightUnitMeasureCode.unitMeasureCode", target = "heightUnitMeasureCodeUnitMeasureCode")
    @Mapping(source = "productAttribute.id", target = "productAttributeId")
    @Mapping(source = "productAttribute.value", target = "productAttributeValue")
    @Mapping(source = "productOption.id", target = "productOptionId")
    @Mapping(source = "productOption.value", target = "productOptionValue")
    StockItemsDTO toDto(StockItems stockItems);

    @Mapping(source = "reviewLineId", target = "reviewLine")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "lengthUnitMeasureCodeId", target = "lengthUnitMeasureCode")
    @Mapping(source = "weightUnitMeasureCodeId", target = "weightUnitMeasureCode")
    @Mapping(source = "widthUnitMeasureCodeId", target = "widthUnitMeasureCode")
    @Mapping(source = "heightUnitMeasureCodeId", target = "heightUnitMeasureCode")
    @Mapping(source = "productAttributeId", target = "productAttribute")
    @Mapping(source = "productOptionId", target = "productOption")
    @Mapping(target = "stockItemHolding", ignore = true)
    StockItems toEntity(StockItemsDTO stockItemsDTO);

    default StockItems fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItems stockItems = new StockItems();
        stockItems.setId(id);
        return stockItems;
    }
}
