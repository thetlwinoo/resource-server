package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.PurchaseOrderLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchaseOrderLines and its DTO PurchaseOrderLinesDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class, PackageTypesMapper.class, PurchaseOrdersMapper.class})
public interface PurchaseOrderLinesMapper extends EntityMapper<PurchaseOrderLinesDTO, PurchaseOrderLines> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "packageType.id", target = "packageTypeId")
    @Mapping(source = "packageType.packageTypeName", target = "packageTypePackageTypeName")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    PurchaseOrderLinesDTO toDto(PurchaseOrderLines purchaseOrderLines);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "packageTypeId", target = "packageType")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    PurchaseOrderLines toEntity(PurchaseOrderLinesDTO purchaseOrderLinesDTO);

    default PurchaseOrderLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrderLines purchaseOrderLines = new PurchaseOrderLines();
        purchaseOrderLines.setId(id);
        return purchaseOrderLines;
    }
}
