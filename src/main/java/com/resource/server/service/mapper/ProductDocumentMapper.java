package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductDocument and its DTO ProductDocumentDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class, CultureMapper.class})
public interface ProductDocumentMapper extends EntityMapper<ProductDocumentDTO, ProductDocument> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.cultureName", target = "cultureCultureName")
    ProductDocumentDTO toDto(ProductDocument productDocument);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "cultureId", target = "culture")
    ProductDocument toEntity(ProductDocumentDTO productDocumentDTO);

    default ProductDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductDocument productDocument = new ProductDocument();
        productDocument.setId(id);
        return productDocument;
    }
}
