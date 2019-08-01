package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductTransactions and its DTO ProductTransactionsDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class, CustomersMapper.class, InvoicesMapper.class, SuppliersMapper.class, TransactionTypesMapper.class, PurchaseOrdersMapper.class})
public interface ProductTransactionsMapper extends EntityMapper<ProductTransactionsDTO, ProductTransactions> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierSupplierName")
    @Mapping(source = "transactionType.id", target = "transactionTypeId")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    ProductTransactionsDTO toDto(ProductTransactions productTransactions);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "invoiceId", target = "invoice")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "transactionTypeId", target = "transactionType")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    ProductTransactions toEntity(ProductTransactionsDTO productTransactionsDTO);

    default ProductTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductTransactions productTransactions = new ProductTransactions();
        productTransactions.setId(id);
        return productTransactions;
    }
}
