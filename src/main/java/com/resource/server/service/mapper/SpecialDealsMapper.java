package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.SpecialDealsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SpecialDeals and its DTO SpecialDealsDTO.
 */
@Mapper(componentModel = "spring", uses = {BuyingGroupsMapper.class, CustomerCategoriesMapper.class, CustomersMapper.class, ProductCategoryMapper.class, StockItemsMapper.class})
public interface SpecialDealsMapper extends EntityMapper<SpecialDealsDTO, SpecialDeals> {

    @Mapping(source = "buyingGroup.id", target = "buyingGroupId")
    @Mapping(source = "buyingGroup.buyingGroupName", target = "buyingGroupBuyingGroupName")
    @Mapping(source = "customerCategory.id", target = "customerCategoryId")
    @Mapping(source = "customerCategory.customerCategoryName", target = "customerCategoryCustomerCategoryName")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.stockItemName", target = "stockItemStockItemName")
    SpecialDealsDTO toDto(SpecialDeals specialDeals);

    @Mapping(target = "cartDiscounts", ignore = true)
    @Mapping(target = "orderDiscounts", ignore = true)
    @Mapping(source = "buyingGroupId", target = "buyingGroup")
    @Mapping(source = "customerCategoryId", target = "customerCategory")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "stockItemId", target = "stockItem")
    SpecialDeals toEntity(SpecialDealsDTO specialDealsDTO);

    default SpecialDeals fromId(Long id) {
        if (id == null) {
            return null;
        }
        SpecialDeals specialDeals = new SpecialDeals();
        specialDeals.setId(id);
        return specialDeals;
    }
}
