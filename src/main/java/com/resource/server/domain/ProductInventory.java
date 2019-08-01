package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductInventory.
 */
@Entity
@Table(name = "product_inventory")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductInventory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "shelf", nullable = false)
    private String shelf;

    @NotNull
    @Column(name = "bin", nullable = false)
    private Integer bin;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JsonIgnoreProperties("productInventories")
    private Products product;

    @ManyToOne
    @JsonIgnoreProperties("productInventories")
    private Locations location;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShelf() {
        return shelf;
    }

    public ProductInventory shelf(String shelf) {
        this.shelf = shelf;
        return this;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public Integer getBin() {
        return bin;
    }

    public ProductInventory bin(Integer bin) {
        this.bin = bin;
        return this;
    }

    public void setBin(Integer bin) {
        this.bin = bin;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductInventory quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Products getProduct() {
        return product;
    }

    public ProductInventory product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }

    public Locations getLocation() {
        return location;
    }

    public ProductInventory location(Locations locations) {
        this.location = locations;
        return this;
    }

    public void setLocation(Locations locations) {
        this.location = locations;
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
        ProductInventory productInventory = (ProductInventory) o;
        if (productInventory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productInventory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductInventory{" +
            "id=" + getId() +
            ", shelf='" + getShelf() + "'" +
            ", bin=" + getBin() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
