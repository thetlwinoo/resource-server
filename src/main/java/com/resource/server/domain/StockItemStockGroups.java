package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A StockItemStockGroups.
 */
@Entity
@Table(name = "stock_item_stock_groups")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StockItemStockGroups extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("stockItemStockGroups")
    private StockGroups stockGroup;

    @ManyToOne
    @JsonIgnoreProperties("stockItemStockGroups")
    private Products product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StockGroups getStockGroup() {
        return stockGroup;
    }

    public StockItemStockGroups stockGroup(StockGroups stockGroups) {
        this.stockGroup = stockGroups;
        return this;
    }

    public void setStockGroup(StockGroups stockGroups) {
        this.stockGroup = stockGroups;
    }

    public Products getProduct() {
        return product;
    }

    public StockItemStockGroups product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
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
        StockItemStockGroups stockItemStockGroups = (StockItemStockGroups) o;
        if (stockItemStockGroups.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItemStockGroups.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItemStockGroups{" +
            "id=" + getId() +
            "}";
    }
}
