package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the StockItemTransactions entity.
 */
public class StockItemTransactionsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate transactionOccurredWhen;

    @NotNull
    private BigDecimal quantity;


    private Long customerId;

    private Long invoiceId;

    private Long purchaseOrderId;

    private Long productId;

    private String productProductName;

    private Long supplierId;

    private String supplierSupplierName;

    private Long transactionTypeId;

    private String transactionTypeTransactionTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransactionOccurredWhen() {
        return transactionOccurredWhen;
    }

    public void setTransactionOccurredWhen(LocalDate transactionOccurredWhen) {
        this.transactionOccurredWhen = transactionOccurredWhen;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoicesId) {
        this.invoiceId = invoicesId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrdersId) {
        this.purchaseOrderId = purchaseOrdersId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    public String getProductProductName() {
        return productProductName;
    }

    public void setProductProductName(String productsProductName) {
        this.productProductName = productsProductName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierSupplierName() {
        return supplierSupplierName;
    }

    public void setSupplierSupplierName(String suppliersSupplierName) {
        this.supplierSupplierName = suppliersSupplierName;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypesId) {
        this.transactionTypeId = transactionTypesId;
    }

    public String getTransactionTypeTransactionTypeName() {
        return transactionTypeTransactionTypeName;
    }

    public void setTransactionTypeTransactionTypeName(String transactionTypesTransactionTypeName) {
        this.transactionTypeTransactionTypeName = transactionTypesTransactionTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItemTransactionsDTO stockItemTransactionsDTO = (StockItemTransactionsDTO) o;
        if (stockItemTransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItemTransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItemTransactionsDTO{" +
            "id=" + getId() +
            ", transactionOccurredWhen='" + getTransactionOccurredWhen() + "'" +
            ", quantity=" + getQuantity() +
            ", customer=" + getCustomerId() +
            ", invoice=" + getInvoiceId() +
            ", purchaseOrder=" + getPurchaseOrderId() +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            ", supplier=" + getSupplierId() +
            ", supplier='" + getSupplierSupplierName() + "'" +
            ", transactionType=" + getTransactionTypeId() +
            ", transactionType='" + getTransactionTypeTransactionTypeName() + "'" +
            "}";
    }
}
