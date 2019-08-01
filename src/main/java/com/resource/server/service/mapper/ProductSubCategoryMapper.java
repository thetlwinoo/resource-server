package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductSubCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductSubCategory and its DTO ProductSubCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductCategoryMapper.class})
public interface ProductSubCategoryMapper extends EntityMapper<ProductSubCategoryDTO, ProductSubCategory> {

    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.productCategoryName", target = "productCategoryProductCategoryName")
    ProductSubCategoryDTO toDto(ProductSubCategory productSubCategory);

    @Mapping(source = "productCategoryId", target = "productCategory")
    ProductSubCategory toEntity(ProductSubCategoryDTO productSubCategoryDTO);

    default ProductSubCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductSubCategory productSubCategory = new ProductSubCategory();
        productSubCategory.setId(id);
        return productSubCategory;
    }
}
