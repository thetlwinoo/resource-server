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

    @Column(name = "product_number")
    private String productNumber;

    @NotNull
    @Column(name = "search_details", nullable = false)
    private String searchDetails;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "warranty_period")
    private String warrantyPeriod;

    @Column(name = "warranty_policy")
    private String warrantyPolicy;

    @Column(name = "sell_count")
    private Integer sellCount;

    @NotNull
    @Column(name = "what_in_the_box", nullable = false)
    private String whatInTheBox;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StockItems> stockItemLists = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("products")
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Merchants merchant;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private PackageTypes unitPackage;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private PackageTypes outerPackage;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private ProductModel productModel;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private ProductBrand productBrand;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private WarrantyTypes warrantyType;

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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Products thumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public Products warrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
        return this;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPolicy() {
        return warrantyPolicy;
    }

    public Products warrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
        return this;
    }

    public void setWarrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
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

    public String getWhatInTheBox() {
        return whatInTheBox;
    }

    public Products whatInTheBox(String whatInTheBox) {
        this.whatInTheBox = whatInTheBox;
        return this;
    }

    public void setWhatInTheBox(String whatInTheBox) {
        this.whatInTheBox = whatInTheBox;
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

    public Merchants getMerchant() {
        return merchant;
    }

    public Products merchant(Merchants merchants) {
        this.merchant = merchants;
        return this;
    }

    public void setMerchant(Merchants merchants) {
        this.merchant = merchants;
    }

    public PackageTypes getUnitPackage() {
        return unitPackage;
    }

    public Products unitPackage(PackageTypes packageTypes) {
        this.unitPackage = packageTypes;
        return this;
    }

    public void setUnitPackage(PackageTypes packageTypes) {
        this.unitPackage = packageTypes;
    }

    public PackageTypes getOuterPackage() {
        return outerPackage;
    }

    public Products outerPackage(PackageTypes packageTypes) {
        this.outerPackage = packageTypes;
        return this;
    }

    public void setOuterPackage(PackageTypes packageTypes) {
        this.outerPackage = packageTypes;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public Products productModel(ProductModel productModel) {
        this.productModel = productModel;
        return this;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
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

    public WarrantyTypes getWarrantyType() {
        return warrantyType;
    }

    public Products warrantyType(WarrantyTypes warrantyTypes) {
        this.warrantyType = warrantyTypes;
        return this;
    }

    public void setWarrantyType(WarrantyTypes warrantyTypes) {
        this.warrantyType = warrantyTypes;
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
            ", productNumber='" + getProductNumber() + "'" +
            ", searchDetails='" + getSearchDetails() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", warrantyPeriod='" + getWarrantyPeriod() + "'" +
            ", warrantyPolicy='" + getWarrantyPolicy() + "'" +
            ", sellCount=" + getSellCount() +
            ", whatInTheBox='" + getWhatInTheBox() + "'" +
            "}";
    }
}
