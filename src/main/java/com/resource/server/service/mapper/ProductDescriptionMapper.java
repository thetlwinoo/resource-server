package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductDescriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductDescription and its DTO ProductDescriptionDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductModelMapper.class})
public interface ProductDescriptionMapper extends EntityMapper<ProductDescriptionDTO, ProductDescription> {

    @Mapping(source = "productModel.id", target = "productModelId")
    ProductDescriptionDTO toDto(ProductDescription productDescription);

    @Mapping(source = "productModelId", target = "productModel")
    ProductDescription toEntity(ProductDescriptionDTO productDescriptionDTO);

    default ProductDescription fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductDescription productDescription = new ProductDescription();
        productDescription.setId(id);
        return productDescription;
    }
}
