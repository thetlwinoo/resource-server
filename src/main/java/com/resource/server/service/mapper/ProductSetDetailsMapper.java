package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductSetDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductSetDetails and its DTO ProductSetDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductSetDetailsMapper extends EntityMapper<ProductSetDetailsDTO, ProductSetDetails> {



    default ProductSetDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductSetDetails productSetDetails = new ProductSetDetails();
        productSetDetails.setId(id);
        return productSetDetails;
    }
}
