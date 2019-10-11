package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Products.
 */
@Entity
@Table(name = "products")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Products extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "handle")
    private String handle;

    @Column(name = "product_number")
    private String productNumber;

    @Lob
    @Column(name = "search_details")
    private String searchDetails;

    @Column(name = "sell_count")
    private Integer sellCount;

    @Column(name = "active_ind")
    private Boolean activeInd;

    @OneToOne
    @JoinColumn(unique = true)
    private ProductDocument document;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StockItems> stockItemLists = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("products")
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private ProductBrand productBrand;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public Products productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getHandle() {
        return handle;
    }

    public Products handle(String handle) {
        this.handle = handle;
        return this;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public Products productNumber(String productNumber) {
        this.productNumber = productNumber;
        return this;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getSearchDetails() {
        return searchDetails;
    }

    public Products searchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
        return this;
    }

    public void setSearchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public Products sellCount(Integer sellCount) {
        this.sellCount = sellCount;
        return this;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public Products activeInd(Boolean activeInd) {
        this.activeInd = activeInd;
        return this;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public ProductDocument getDocument() {
        return document;
    }

    public Products document(ProductDocument productDocument) {
        this.document = productDocument;
        return this;
    }

    public void setDocument(ProductDocument productDocument) {
        this.document = productDocument;
    }

    public Set<StockItems> getStockItemLists() {
        return stockItemLists;
    }

    public Products stockItemLists(Set<StockItems> stockItems) {
        this.stockItemLists = stockItems;
        return this;
    }

    public Products addStockItemList(StockItems stockItems) {
        this.stockItemLists.add(stockItems);
        stockItems.setProduct(this);
        return this;
    }

    public Products removeStockItemList(StockItems stockItems) {
        this.stockItemLists.remove(stockItems);
        stockItems.setProduct(null);
        return this;
    }

    public void setStockItemLists(Set<StockItems> stockItems) {
        this.stockItemLists = stockItems;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public Products supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public Products productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductBrand getProductBrand() {
        return productBrand;
    }

    public Products productBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
        return this;
    }

    public void setProductBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
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
        Products products = (Products) o;
        if (products.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), products.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Products{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", handle='" + getHandle() + "'" +
            ", productNumber='" + getProductNumber() + "'" +
            ", searchDetails='" + getSearchDetails() + "'" +
            ", sellCount=" + getSellCount() +
            ", activeInd='" + isActiveInd() + "'" +
            "}";
    }
}
