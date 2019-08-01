package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductInventoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductInventory and its DTO ProductInventoryDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class, LocationsMapper.class})
public interface ProductInventoryMapper extends EntityMapper<ProductInventoryDTO, ProductInventory> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.locationName", target = "locationLocationName")
    ProductInventoryDTO toDto(ProductInventory productInventory);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "locationId", target = "location")
    ProductInventory toEntity(ProductInventoryDTO productInventoryDTO);

    default ProductInventory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductInventory productInventory = new ProductInventory();
        productInventory.setId(id);
        return productInventory;
    }
}
