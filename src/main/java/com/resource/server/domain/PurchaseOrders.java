package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PurchaseOrders.
 */
@Entity
@Table(name = "purchase_orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseOrders extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "expected_delivery_date")
    private LocalDate expectedDeliveryDate;

    @Column(name = "supplier_reference")
    private String supplierReference;

    @Column(name = "is_order_finalized")
    private Boolean isOrderFinalized;

    @Column(name = "comments")
    private String comments;

    @Column(name = "internal_comments")
    private String internalComments;

    @OneToMany(mappedBy = "purchaseOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchaseOrderLines> purchaseOrderLists = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("purchaseOrders")
    private People contactPerson;

    @ManyToOne
    @JsonIgnoreProperties("purchaseOrders")
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties("purchaseOrders")
    private DeliveryMethods deliveryMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public PurchaseOrders orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public PurchaseOrders expectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getSupplierReference() {
        return supplierReference;
    }

    public PurchaseOrders supplierReference(String supplierReference) {
        this.supplierReference = supplierReference;
        return this;
    }

    public void setSupplierReference(String supplierReference) {
        this.supplierReference = supplierReference;
    }

    public Boolean isIsOrderFinalized() {
        return isOrderFinalized;
    }

    public PurchaseOrders isOrderFinalized(Boolean isOrderFinalized) {
        this.isOrderFinalized = isOrderFinalized;
        return this;
    }

    public void setIsOrderFinalized(Boolean isOrderFinalized) {
        this.isOrderFinalized = isOrderFinalized;
    }

    public String getComments() {
        return comments;
    }

    public PurchaseOrders comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public PurchaseOrders internalComments(String internalComments) {
        this.internalComments = internalComments;
        return this;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public Set<PurchaseOrderLines> getPurchaseOrderLists() {
        return purchaseOrderLists;
    }

    public PurchaseOrders purchaseOrderLists(Set<PurchaseOrderLines> purchaseOrderLines) {
        this.purchaseOrderLists = purchaseOrderLines;
        return this;
    }

    public PurchaseOrders addPurchaseOrderList(PurchaseOrderLines purchaseOrderLines) {
        this.purchaseOrderLists.add(purchaseOrderLines);
        purchaseOrderLines.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrders removePurchaseOrderList(PurchaseOrderLines purchaseOrderLines) {
        this.purchaseOrderLists.remove(purchaseOrderLines);
        purchaseOrderLines.setPurchaseOrder(null);
        return this;
    }

    public void setPurchaseOrderLists(Set<PurchaseOrderLines> purchaseOrderLines) {
        this.purchaseOrderLists = purchaseOrderLines;
    }

    public People getContactPerson() {
        return contactPerson;
    }

    public PurchaseOrders contactPerson(People people) {
        this.contactPerson = people;
        return this;
    }

    public void setContactPerson(People people) {
        this.contactPerson = people;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public PurchaseOrders supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public DeliveryMethods getDeliveryMethod() {
        return deliveryMethod;
    }

    public PurchaseOrders deliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
        return this;
    }

    public void setDeliveryMethod(DeliveryMethods deliveryMethods) {
        this.deliveryMethod = deliveryMethods;
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
        PurchaseOrders purchaseOrders = (PurchaseOrders) o;
        if (purchaseOrders.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseOrders.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseOrders{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", supplierReference='" + getSupplierReference() + "'" +
            ", isOrderFinalized='" + isIsOrderFinalized() + "'" +
            ", comments='" + getComments() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            "}";
    }
}
