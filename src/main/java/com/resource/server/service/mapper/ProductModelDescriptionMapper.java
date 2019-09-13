package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductModelDescriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductModelDescription and its DTO ProductModelDescriptionDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductModelMapper.class})
public interface ProductModelDescriptionMapper extends EntityMapper<ProductModelDescriptionDTO, ProductModelDescription> {

    @Mapping(source = "productModel.id", target = "productModelId")
    ProductModelDescriptionDTO toDto(ProductModelDescription productModelDescription);

    @Mapping(source = "productModelId", target = "productModel")
    ProductModelDescription toEntity(ProductModelDescriptionDTO productModelDescriptionDTO);

    default ProductModelDescription fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductModelDescription productModelDescription = new ProductModelDescription();
        productModelDescription.setId(id);
        return productModelDescription;
    }
}
