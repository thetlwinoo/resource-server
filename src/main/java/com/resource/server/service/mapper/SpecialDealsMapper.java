package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.SpecialDealsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SpecialDeals and its DTO SpecialDealsDTO.
 */
@Mapper(componentModel = "spring", uses = {BuyingGroupsMapper.class, CustomerCategoriesMapper.class, CustomersMapper.class, StockGroupsMapper.class, ProductsMapper.class})
public interface SpecialDealsMapper extends EntityMapper<SpecialDealsDTO, SpecialDeals> {

    @Mapping(source = "buyingGroup.id", target = "buyingGroupId")
    @Mapping(source = "buyingGroup.buyingGroupName", target = "buyingGroupBuyingGroupName")
    @Mapping(source = "customerCategory.id", target = "customerCategoryId")
    @Mapping(source = "customerCategory.customerCategoryName", target = "customerCategoryCustomerCategoryName")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "stockGroup.id", target = "stockGroupId")
    @Mapping(source = "stockGroup.stockGroupName", target = "stockGroupStockGroupName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    SpecialDealsDTO toDto(SpecialDeals specialDeals);

    @Mapping(target = "cartDiscounts", ignore = true)
    @Mapping(target = "orderDiscounts", ignore = true)
    @Mapping(source = "buyingGroupId", target = "buyingGroup")
    @Mapping(source = "customerCategoryId", target = "customerCategory")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "stockGroupId", target = "stockGroup")
    @Mapping(source = "productId", target = "product")
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
