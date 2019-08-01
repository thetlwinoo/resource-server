package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductTransactions entity.
 */
public class ProductTransactionsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate transactionOccuredWhen;

    private Float quantity;


    private Long productId;

    private String productProductName;

    private Long customerId;

    private Long invoiceId;

    private Long supplierId;

    private String supplierSupplierName;

    private Long transactionTypeId;

    private Long purchaseOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransactionOccuredWhen() {
        return transactionOccuredWhen;
    }

    public void setTransactionOccuredWhen(LocalDate transactionOccuredWhen) {
        this.transactionOccuredWhen = transactionOccuredWhen;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
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

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrdersId) {
        this.purchaseOrderId = purchaseOrdersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductTransactionsDTO productTransactionsDTO = (ProductTransactionsDTO) o;
        if (productTransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productTransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductTransactionsDTO{" +
            "id=" + getId() +
            ", transactionOccuredWhen='" + getTransactionOccuredWhen() + "'" +
            ", quantity=" + getQuantity() +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            ", customer=" + getCustomerId() +
            ", invoice=" + getInvoiceId() +
            ", supplier=" + getSupplierId() +
            ", supplier='" + getSupplierSupplierName() + "'" +
            ", transactionType=" + getTransactionTypeId() +
            ", purchaseOrder=" + getPurchaseOrderId() +
            "}";
    }
}
