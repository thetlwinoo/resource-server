package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductReview and its DTO ProductReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class})
public interface ProductReviewMapper extends EntityMapper<ProductReviewDTO, ProductReview> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    ProductReviewDTO toDto(ProductReview productReview);

    @Mapping(source = "productId", target = "product")
    ProductReview toEntity(ProductReviewDTO productReviewDTO);

    default ProductReview fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductReview productReview = new ProductReview();
        productReview.setId(id);
        return productReview;
    }
}
