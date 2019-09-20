package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.StockItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockItems and its DTO StockItemsDTO.
 */
@Mapper(componentModel = "spring", uses = {ReviewLinesMapper.class, UnitMeasureMapper.class, ProductAttributeMapper.class, ProductOptionMapper.class, ProductsMapper.class})
public interface StockItemsMapper extends EntityMapper<StockItemsDTO, StockItems> {

    @Mapping(source = "stockItemOnReviewLine.id", target = "stockItemOnReviewLineId")
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
    @Mapping(source = "product.id", target = "productId")
    StockItemsDTO toDto(StockItems stockItems);

    @Mapping(source = "stockItemOnReviewLineId", target = "stockItemOnReviewLine")
    @Mapping(target = "photoLists", ignore = true)
    @Mapping(target = "specialDiscounts", ignore = true)
    @Mapping(source = "lengthUnitMeasureCodeId", target = "lengthUnitMeasureCode")
    @Mapping(source = "weightUnitMeasureCodeId", target = "weightUnitMeasureCode")
    @Mapping(source = "widthUnitMeasureCodeId", target = "widthUnitMeasureCode")
    @Mapping(source = "heightUnitMeasureCodeId", target = "heightUnitMeasureCode")
    @Mapping(source = "productAttributeId", target = "productAttribute")
    @Mapping(source = "productOptionId", target = "productOption")
    @Mapping(target = "stockItemHolding", ignore = true)
    @Mapping(source = "productId", target = "product")
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
