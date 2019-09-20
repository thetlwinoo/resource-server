package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductBrandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductBrand and its DTO ProductBrandDTO.
 */
@Mapper(componentModel = "spring", uses = {MerchantsMapper.class})
public interface ProductBrandMapper extends EntityMapper<ProductBrandDTO, ProductBrand> {

    @Mapping(source = "merchant.id", target = "merchantId")
    @Mapping(source = "merchant.merchantName", target = "merchantMerchantName")
    ProductBrandDTO toDto(ProductBrand productBrand);

    @Mapping(source = "merchantId", target = "merchant")
    ProductBrand toEntity(ProductBrandDTO productBrandDTO);

    default ProductBrand fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductBrand productBrand = new ProductBrand();
        productBrand.setId(id);
        return productBrand;
    }
}
