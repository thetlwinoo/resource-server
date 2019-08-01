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

    @NotNull
    @Column(name = "make_flag", nullable = false)
    private Boolean makeFlag;

    @NotNull
    @Column(name = "finished_goods_flag", nullable = false)
    private Boolean finishedGoodsFlag;

    @Column(name = "color")
    private String color;

    @NotNull
    @Column(name = "safety_stock_level", nullable = false)
    private Integer safetyStockLevel;

    @NotNull
    @Column(name = "reorder_point", nullable = false)
    private Integer reorderPoint;

    @NotNull
    @Column(name = "standard_cost", nullable = false)
    private Float standardCost;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Column(name = "recommended_retail_price")
    private Float recommendedRetailPrice;

    @Column(name = "brand")
    private String brand;

    @Column(name = "specify_size")
    private String specifySize;

    @Column(name = "weight")
    private Float weight;

    @NotNull
    @Column(name = "days_to_manufacture", nullable = false)
    private Integer daysToManufacture;

    @Column(name = "product_line")
    private String productLine;

    @Column(name = "class_type")
    private String classType;

    @Column(name = "style")
    private String style;

    @Column(name = "custom_fields")
    private String customFields;

    @Column(name = "tags")
    private String tags;

    @Column(name = "photo")
    private String photo;

    @NotNull
    @Column(name = "sell_start_date", nullable = false)
    private LocalDate sellStartDate;

    @Column(name = "sell_end_date")
    private LocalDate sellEndDate;

    @Column(name = "marketing_comments")
    private String marketingComments;

    @Column(name = "internal_comments")
    private String internalComments;

    @Column(name = "discontinued_date")
    private LocalDate discontinuedDate;

    @Column(name = "sell_count")
    private Integer sellCount;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private PackageTypes unitPackage;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private PackageTypes outerPackage;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private ProductSubCategory productSubCategory;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private UnitMeasure sizeUnitMeasureCode;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private UnitMeasure weightUnitMeasureCode;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private ProductModel productModel;

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

    public Boolean isMakeFlag() {
        return makeFlag;
    }

    public Products makeFlag(Boolean makeFlag) {
        this.makeFlag = makeFlag;
        return this;
    }

    public void setMakeFlag(Boolean makeFlag) {
        this.makeFlag = makeFlag;
    }

    public Boolean isFinishedGoodsFlag() {
        return finishedGoodsFlag;
    }

    public Products finishedGoodsFlag(Boolean finishedGoodsFlag) {
        this.finishedGoodsFlag = finishedGoodsFlag;
        return this;
    }

    public void setFinishedGoodsFlag(Boolean finishedGoodsFlag) {
        this.finishedGoodsFlag = finishedGoodsFlag;
    }

    public String getColor() {
        return color;
    }

    public Products color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSafetyStockLevel() {
        return safetyStockLevel;
    }

    public Products safetyStockLevel(Integer safetyStockLevel) {
        this.safetyStockLevel = safetyStockLevel;
        return this;
    }

    public void setSafetyStockLevel(Integer safetyStockLevel) {
        this.safetyStockLevel = safetyStockLevel;
    }

    public Integer getReorderPoint() {
        return reorderPoint;
    }

    public Products reorderPoint(Integer reorderPoint) {
        this.reorderPoint = reorderPoint;
        return this;
    }

    public void setReorderPoint(Integer reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Float getStandardCost() {
        return standardCost;
    }

    public Products standardCost(Float standardCost) {
        this.standardCost = standardCost;
        return this;
    }

    public void setStandardCost(Float standardCost) {
        this.standardCost = standardCost;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public Products unitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getRecommendedRetailPrice() {
        return recommendedRetailPrice;
    }

    public Products recommendedRetailPrice(Float recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
        return this;
    }

    public void setRecommendedRetailPrice(Float recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
    }

    public String getBrand() {
        return brand;
    }

    public Products brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpecifySize() {
        return specifySize;
    }

    public Products specifySize(String specifySize) {
        this.specifySize = specifySize;
        return this;
    }

    public void setSpecifySize(String specifySize) {
        this.specifySize = specifySize;
    }

    public Float getWeight() {
        return weight;
    }

    public Products weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getDaysToManufacture() {
        return daysToManufacture;
    }

    public Products daysToManufacture(Integer daysToManufacture) {
        this.daysToManufacture = daysToManufacture;
        return this;
    }

    public void setDaysToManufacture(Integer daysToManufacture) {
        this.daysToManufacture = daysToManufacture;
    }

    public String getProductLine() {
        return productLine;
    }

    public Products productLine(String productLine) {
        this.productLine = productLine;
        return this;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getClassType() {
        return classType;
    }

    public Products classType(String classType) {
        this.classType = classType;
        return this;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getStyle() {
        return style;
    }

    public Products style(String style) {
        this.style = style;
        return this;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCustomFields() {
        return customFields;
    }

    public Products customFields(String customFields) {
        this.customFields = customFields;
        return this;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getTags() {
        return tags;
    }

    public Products tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPhoto() {
        return photo;
    }

    public Products photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDate getSellStartDate() {
        return sellStartDate;
    }

    public Products sellStartDate(LocalDate sellStartDate) {
        this.sellStartDate = sellStartDate;
        return this;
    }

    public void setSellStartDate(LocalDate sellStartDate) {
        this.sellStartDate = sellStartDate;
    }

    public LocalDate getSellEndDate() {
        return sellEndDate;
    }

    public Products sellEndDate(LocalDate sellEndDate) {
        this.sellEndDate = sellEndDate;
        return this;
    }

    public void setSellEndDate(LocalDate sellEndDate) {
        this.sellEndDate = sellEndDate;
    }

    public String getMarketingComments() {
        return marketingComments;
    }

    public Products marketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
        return this;
    }

    public void setMarketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public Products internalComments(String internalComments) {
        this.internalComments = internalComments;
        return this;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public LocalDate getDiscontinuedDate() {
        return discontinuedDate;
    }

    public Products discontinuedDate(LocalDate discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
        return this;
    }

    public void setDiscontinuedDate(LocalDate discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
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

    public ProductSubCategory getProductSubCategory() {
        return productSubCategory;
    }

    public Products productSubCategory(ProductSubCategory productSubCategory) {
        this.productSubCategory = productSubCategory;
        return this;
    }

    public void setProductSubCategory(ProductSubCategory productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public UnitMeasure getSizeUnitMeasureCode() {
        return sizeUnitMeasureCode;
    }

    public Products sizeUnitMeasureCode(UnitMeasure unitMeasure) {
        this.sizeUnitMeasureCode = unitMeasure;
        return this;
    }

    public void setSizeUnitMeasureCode(UnitMeasure unitMeasure) {
        this.sizeUnitMeasureCode = unitMeasure;
    }

    public UnitMeasure getWeightUnitMeasureCode() {
        return weightUnitMeasureCode;
    }

    public Products weightUnitMeasureCode(UnitMeasure unitMeasure) {
        this.weightUnitMeasureCode = unitMeasure;
        return this;
    }

    public void setWeightUnitMeasureCode(UnitMeasure unitMeasure) {
        this.weightUnitMeasureCode = unitMeasure;
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
            ", makeFlag='" + isMakeFlag() + "'" +
            ", finishedGoodsFlag='" + isFinishedGoodsFlag() + "'" +
            ", color='" + getColor() + "'" +
            ", safetyStockLevel=" + getSafetyStockLevel() +
            ", reorderPoint=" + getReorderPoint() +
            ", standardCost=" + getStandardCost() +
            ", unitPrice=" + getUnitPrice() +
            ", recommendedRetailPrice=" + getRecommendedRetailPrice() +
            ", brand='" + getBrand() + "'" +
            ", specifySize='" + getSpecifySize() + "'" +
            ", weight=" + getWeight() +
            ", daysToManufacture=" + getDaysToManufacture() +
            ", productLine='" + getProductLine() + "'" +
            ", classType='" + getClassType() + "'" +
            ", style='" + getStyle() + "'" +
            ", customFields='" + getCustomFields() + "'" +
            ", tags='" + getTags() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", sellStartDate='" + getSellStartDate() + "'" +
            ", sellEndDate='" + getSellEndDate() + "'" +
            ", marketingComments='" + getMarketingComments() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", discontinuedDate='" + getDiscontinuedDate() + "'" +
            ", sellCount=" + getSellCount() +
            "}";
    }
}
