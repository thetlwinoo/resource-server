package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Products and its DTO ProductsDTO.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, MerchantsMapper.class, PackageTypesMapper.class, ProductModelMapper.class, ProductCategoryMapper.class, ProductBrandMapper.class, WarrantyTypesMapper.class})
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierSupplierName")
    @Mapping(source = "merchant.id", target = "merchantId")
    @Mapping(source = "merchant.merchantName", target = "merchantMerchantName")
    @Mapping(source = "unitPackage.id", target = "unitPackageId")
    @Mapping(source = "unitPackage.packageTypeName", target = "unitPackagePackageTypeName")
    @Mapping(source = "outerPackage.id", target = "outerPackageId")
    @Mapping(source = "outerPackage.packageTypeName", target = "outerPackagePackageTypeName")
    @Mapping(source = "productModel.id", target = "productModelId")
    @Mapping(source = "productModel.productModelName", target = "productModelProductModelName")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "productBrand.id", target = "productBrandId")
    @Mapping(source = "productBrand.productBrandName", target = "productBrandProductBrandName")
    @Mapping(source = "warrantyType.id", target = "warrantyTypeId")
    @Mapping(source = "warrantyType.warrantyTypeName", target = "warrantyTypeWarrantyTypeName")
    ProductsDTO toDto(Products products);

    @Mapping(target = "stockItemLists", ignore = true)
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "merchantId", target = "merchant")
    @Mapping(source = "unitPackageId", target = "unitPackage")
    @Mapping(source = "outerPackageId", target = "outerPackage")
    @Mapping(source = "productModelId", target = "productModel")
    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "productBrandId", target = "productBrand")
    @Mapping(source = "warrantyTypeId", target = "warrantyType")
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
