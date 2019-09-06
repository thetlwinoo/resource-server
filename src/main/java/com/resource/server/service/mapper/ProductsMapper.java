package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Products and its DTO ProductsDTO.
 */
@Mapper(componentModel = "spring", uses = {ReviewLinesMapper.class, PackageTypesMapper.class, SuppliersMapper.class, ProductSubCategoryMapper.class, UnitMeasureMapper.class, ProductModelMapper.class})
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {

    @Mapping(source = "productReview.id", target = "productReviewId")
    @Mapping(source = "unitPackage.id", target = "unitPackageId")
    @Mapping(source = "unitPackage.packageTypeName", target = "unitPackagePackageTypeName")
    @Mapping(source = "outerPackage.id", target = "outerPackageId")
    @Mapping(source = "outerPackage.packageTypeName", target = "outerPackagePackageTypeName")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierSupplierName")
    @Mapping(source = "productSubCategory.id", target = "productSubCategoryId")
    @Mapping(source = "productSubCategory.productSubCategoryName", target = "productSubCategoryProductSubCategoryName")
    @Mapping(source = "sizeUnitMeasureCode.id", target = "sizeUnitMeasureCodeId")
    @Mapping(source = "sizeUnitMeasureCode.unitMeasureCode", target = "sizeUnitMeasureCodeUnitMeasureCode")
    @Mapping(source = "weightUnitMeasureCode.id", target = "weightUnitMeasureCodeId")
    @Mapping(source = "weightUnitMeasureCode.unitMeasureCode", target = "weightUnitMeasureCodeUnitMeasureCode")
    @Mapping(source = "productModel.id", target = "productModelId")
    @Mapping(source = "productModel.productModelName", target = "productModelProductModelName")
    ProductsDTO toDto(Products products);

    @Mapping(source = "productReviewId", target = "productReview")
    @Mapping(source = "unitPackageId", target = "unitPackage")
    @Mapping(source = "outerPackageId", target = "outerPackage")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "productSubCategoryId", target = "productSubCategory")
    @Mapping(source = "sizeUnitMeasureCodeId", target = "sizeUnitMeasureCode")
    @Mapping(source = "weightUnitMeasureCodeId", target = "weightUnitMeasureCode")
    @Mapping(source = "productModelId", target = "productModel")
    Products toEntity(ProductsDTO productsDTO);

    default Products fromId(Long id) {
        if (id == null) {
            return null;
        }
        Products products = new Products();
        products.setId(id);
        return products;
    }
}
