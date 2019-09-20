package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductModel and its DTO ProductModelDTO.
 */
@Mapper(componentModel = "spring", uses = {MerchantsMapper.class})
public interface ProductModelMapper extends EntityMapper<ProductModelDTO, ProductModel> {

    @Mapping(source = "merchant.id", target = "merchantId")
    @Mapping(source = "merchant.merchantName", target = "merchantMerchantName")
    ProductModelDTO toDto(ProductModel productModel);

    @Mapping(target = "descriptions", ignore = true)
    @Mapping(source = "merchantId", target = "merchant")
    ProductModel toEntity(ProductModelDTO productModelDTO);

    default ProductModel fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductModel productModel = new ProductModel();
        productModel.setId(id);
        return productModel;
    }
}
