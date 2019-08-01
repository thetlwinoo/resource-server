package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ProductTransactions.
 */
@Entity
@Table(name = "product_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductTransactions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "transaction_occured_when", nullable = false)
    private LocalDate transactionOccuredWhen;

    @Column(name = "quantity")
    private Float quantity;

    @ManyToOne
    @JsonIgnoreProperties("productTransactions")
    private Products product;

    @ManyToOne
    @JsonIgnoreProperties("productTransactions")
    private Customers customer;

    @ManyToOne
    @JsonIgnoreProperties("productTransactions")
    private Invoices invoice;

    @ManyToOne
    @JsonIgnoreProperties("productTransactions")
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties("productTransactions")
    private TransactionTypes transactionType;

    @ManyToOne
    @JsonIgnoreProperties("productTransactions")
    private PurchaseOrders purchaseOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransactionOccuredWhen() {
        return transactionOccuredWhen;
    }

    public ProductTransactions transactionOccuredWhen(LocalDate transactionOccuredWhen) {
        this.transactionOccuredWhen = transactionOccuredWhen;
        return this;
    }

    public void setTransactionOccuredWhen(LocalDate transactionOccuredWhen) {
        this.transactionOccuredWhen = transactionOccuredWhen;
    }

    public Float getQuantity() {
        return quantity;
    }

    public ProductTransactions quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Products getProduct() {
        return product;
    }

    public ProductTransactions product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }

    public Customers getCustomer() {
        return customer;
    }

    public ProductTransactions customer(Customers customers) {
        this.customer = customers;
        return this;
    }

    public void setCustomer(Customers customers) {
        this.customer = customers;
    }

    public Invoices getInvoice() {
        return invoice;
    }

    public ProductTransactions invoice(Invoices invoices) {
        this.invoice = invoices;
        return this;
    }

    public void setInvoice(Invoices invoices) {
        this.invoice = invoices;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public ProductTransactions supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public TransactionTypes getTransactionType() {
        return transactionType;
    }

    public ProductTransactions transactionType(TransactionTypes transactionTypes) {
        this.transactionType = transactionTypes;
        return this;
    }

    public void setTransactionType(TransactionTypes transactionTypes) {
        this.transactionType = transactionTypes;
    }

    public PurchaseOrders getPurchaseOrder() {
        return purchaseOrder;
    }

    public ProductTransactions purchaseOrder(PurchaseOrders purchaseOrders) {
        this.purchaseOrder = purchaseOrders;
        return this;
    }

    public void setPurchaseOrder(PurchaseOrders purchaseOrders) {
        this.purchaseOrder = purchaseOrders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductTransactions productTransactions = (ProductTransactions) o;
        if (productTransactions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productTransactions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductTransactions{" +
            "id=" + getId() +
            ", transactionOccuredWhen='" + getTransactionOccuredWhen() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
