package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.StockItemTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockItemTransactions and its DTO StockItemTransactionsDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomersMapper.class, InvoicesMapper.class, PurchaseOrdersMapper.class, ProductsMapper.class, SuppliersMapper.class, TransactionTypesMapper.class})
public interface StockItemTransactionsMapper extends EntityMapper<StockItemTransactionsDTO, StockItemTransactions> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierSupplierName")
    @Mapping(source = "transactionType.id", target = "transactionTypeId")
    @Mapping(source = "transactionType.transactionTypeName", target = "transactionTypeTransactionTypeName")
    StockItemTransactionsDTO toDto(StockItemTransactions stockItemTransactions);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "invoiceId", target = "invoice")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "transactionTypeId", target = "transactionType")
    StockItemTransactions toEntity(StockItemTransactionsDTO stockItemTransactionsDTO);

    default StockItemTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItemTransactions stockItemTransactions = new StockItemTransactions();
        stockItemTransactions.setId(id);
        return stockItemTransactions;
    }
}
