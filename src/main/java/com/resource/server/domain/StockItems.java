package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A StockItems.
 */
@Entity
@Table(name = "stock_items")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StockItems extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "stock_item_name", nullable = false)
    private String stockItemName;

    @Column(name = "seller_sku")
    private String sellerSKU;

    @Column(name = "generated_sku")
    private String generatedSKU;

    @Column(name = "barcode")
    private String barcode;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Column(name = "recommended_retail_price")
    private Float recommendedRetailPrice;

    @NotNull
    @Column(name = "quantity_per_outer", nullable = false)
    private Integer quantityPerOuter;

    @Column(name = "typical_weight_per_unit")
    private Float typicalWeightPerUnit;

    @Column(name = "typical_length_per_unit")
    private Integer typicalLengthPerUnit;

    @Column(name = "typical_width_per_unit")
    private Integer typicalWidthPerUnit;

    @Column(name = "typical_height_per_unit")
    private Integer typicalHeightPerUnit;

    @Column(name = "marketing_comments")
    private String marketingComments;

    @Column(name = "internal_comments")
    private String internalComments;

    @Column(name = "discontinued_date")
    private LocalDate discontinuedDate;

    @Column(name = "sell_count")
    private Integer sellCount;

    @Column(name = "custom_fields")
    private String customFields;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @OneToOne
    @JoinColumn(unique = true)
    private ReviewLines reviewLine;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private Products product;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure lengthUnitMeasureCode;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure weightUnitMeasureCode;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure widthUnitMeasureCode;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private UnitMeasure heightUnitMeasureCode;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private ProductAttribute productAttribute;

    @ManyToOne
    @JsonIgnoreProperties("stockItems")
    private ProductOption productOption;

    @OneToOne(mappedBy = "stockItem")
    @JsonIgnore
    private StockItemHoldings stockItemHolding;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public StockItems stockItemName(String stockItemName) {
        this.stockItemName = stockItemName;
        return this;
    }

    public void setStockItemName(String stockItemName) {
        this.stockItemName = stockItemName;
    }

    public String getSellerSKU() {
        return sellerSKU;
    }

    public StockItems sellerSKU(String sellerSKU) {
        this.sellerSKU = sellerSKU;
        return this;
    }

    public void setSellerSKU(String sellerSKU) {
        this.sellerSKU = sellerSKU;
    }

    public String getGeneratedSKU() {
        return generatedSKU;
    }

    public StockItems generatedSKU(String generatedSKU) {
        this.generatedSKU = generatedSKU;
        return this;
    }

    public void setGeneratedSKU(String generatedSKU) {
        this.generatedSKU = generatedSKU;
    }

    public String getBarcode() {
        return barcode;
    }

    public StockItems barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public StockItems unitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getRecommendedRetailPrice() {
        return recommendedRetailPrice;
    }

    public StockItems recommendedRetailPrice(Float recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
        return this;
    }

    public void setRecommendedRetailPrice(Float recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
    }

    public Integer getQuantityPerOuter() {
        return quantityPerOuter;
    }

    public StockItems quantityPerOuter(Integer quantityPerOuter) {
        this.quantityPerOuter = quantityPerOuter;
        return this;
    }

    public void setQuantityPerOuter(Integer quantityPerOuter) {
        this.quantityPerOuter = quantityPerOuter;
    }

    public Float getTypicalWeightPerUnit() {
        return typicalWeightPerUnit;
    }

    public StockItems typicalWeightPerUnit(Float typicalWeightPerUnit) {
        this.typicalWeightPerUnit = typicalWeightPerUnit;
        return this;
    }

    public void setTypicalWeightPerUnit(Float typicalWeightPerUnit) {
        this.typicalWeightPerUnit = typicalWeightPerUnit;
    }

    public Integer getTypicalLengthPerUnit() {
        return typicalLengthPerUnit;
    }

    public StockItems typicalLengthPerUnit(Integer typicalLengthPerUnit) {
        this.typicalLengthPerUnit = typicalLengthPerUnit;
        return this;
    }

    public void setTypicalLengthPerUnit(Integer typicalLengthPerUnit) {
        this.typicalLengthPerUnit = typicalLengthPerUnit;
    }

    public Integer getTypicalWidthPerUnit() {
        return typicalWidthPerUnit;
    }

    public StockItems typicalWidthPerUnit(Integer typicalWidthPerUnit) {
        this.typicalWidthPerUnit = typicalWidthPerUnit;
        return this;
    }

    public void setTypicalWidthPerUnit(Integer typicalWidthPerUnit) {
        this.typicalWidthPerUnit = typicalWidthPerUnit;
    }

    public Integer getTypicalHeightPerUnit() {
        return typicalHeightPerUnit;
    }

    public StockItems typicalHeightPerUnit(Integer typicalHeightPerUnit) {
        this.typicalHeightPerUnit = typicalHeightPerUnit;
        return this;
    }

    public void setTypicalHeightPerUnit(Integer typicalHeightPerUnit) {
        this.typicalHeightPerUnit = typicalHeightPerUnit;
    }

    public String getMarketingComments() {
        return marketingComments;
    }

    public StockItems marketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
        return this;
    }

    public void setMarketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public StockItems internalComments(String internalComments) {
        this.internalComments = internalComments;
        return this;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public LocalDate getDiscontinuedDate() {
        return discontinuedDate;
    }

    public StockItems discontinuedDate(LocalDate discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
        return this;
    }

    public void setDiscontinuedDate(LocalDate discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public StockItems sellCount(Integer sellCount) {
        this.sellCount = sellCount;
        return this;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getCustomFields() {
        return customFields;
    }

    public StockItems customFields(String customFields) {
        this.customFields = customFields;
        return this;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public StockItems thumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public ReviewLines getReviewLine() {
        return reviewLine;
    }

    public StockItems reviewLine(ReviewLines reviewLines) {
        this.reviewLine = reviewLines;
        return this;
    }

    public void setReviewLine(ReviewLines reviewLines) {
        this.reviewLine = reviewLines;
    }

    public Products getProduct() {
        return product;
    }

    public StockItems product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }

    public UnitMeasure getLengthUnitMeasureCode() {
        return lengthUnitMeasureCode;
    }

    public StockItems lengthUnitMeasureCode(UnitMeasure unitMeasure) {
        this.lengthUnitMeasureCode = unitMeasure;
        return this;
    }

    public void setLengthUnitMeasureCode(UnitMeasure unitMeasure) {
        this.lengthUnitMeasureCode = unitMeasure;
    }

    public UnitMeasure getWeightUnitMeasureCode() {
        return weightUnitMeasureCode;
    }

    public StockItems weightUnitMeasureCode(UnitMeasure unitMeasure) {
        this.weightUnitMeasureCode = unitMeasure;
        return this;
    }

    public void setWeightUnitMeasureCode(UnitMeasure unitMeasure) {
        this.weightUnitMeasureCode = unitMeasure;
    }

    public UnitMeasure getWidthUnitMeasureCode() {
        return widthUnitMeasureCode;
    }

    public StockItems widthUnitMeasureCode(UnitMeasure unitMeasure) {
        this.widthUnitMeasureCode = unitMeasure;
        return this;
    }

    public void setWidthUnitMeasureCode(UnitMeasure unitMeasure) {
        this.widthUnitMeasureCode = unitMeasure;
    }

    public UnitMeasure getHeightUnitMeasureCode() {
        return heightUnitMeasureCode;
    }

    public StockItems heightUnitMeasureCode(UnitMeasure unitMeasure) {
        this.heightUnitMeasureCode = unitMeasure;
        return this;
    }

    public void setHeightUnitMeasureCode(UnitMeasure unitMeasure) {
        this.heightUnitMeasureCode = unitMeasure;
    }

    public ProductAttribute getProductAttribute() {
        return productAttribute;
    }

    public StockItems productAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
        return this;
    }

    public void setProductAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public StockItems productOption(ProductOption productOption) {
        this.productOption = productOption;
        return this;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public StockItemHoldings getStockItemHolding() {
        return stockItemHolding;
    }

    public StockItems stockItemHolding(StockItemHoldings stockItemHoldings) {
        this.stockItemHolding = stockItemHoldings;
        return this;
    }

    public void setStockItemHolding(StockItemHoldings stockItemHoldings) {
        this.stockItemHolding = stockItemHoldings;
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
        StockItems stockItems = (StockItems) o;
        if (stockItems.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItems.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItems{" +
            "id=" + getId() +
            ", stockItemName='" + getStockItemName() + "'" +
            ", sellerSKU='" + getSellerSKU() + "'" +
            ", generatedSKU='" + getGeneratedSKU() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", recommendedRetailPrice=" + getRecommendedRetailPrice() +
            ", quantityPerOuter=" + getQuantityPerOuter() +
            ", typicalWeightPerUnit=" + getTypicalWeightPerUnit() +
            ", typicalLengthPerUnit=" + getTypicalLengthPerUnit() +
            ", typicalWidthPerUnit=" + getTypicalWidthPerUnit() +
            ", typicalHeightPerUnit=" + getTypicalHeightPerUnit() +
            ", marketingComments='" + getMarketingComments() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", discontinuedDate='" + getDiscontinuedDate() + "'" +
            ", sellCount=" + getSellCount() +
            ", customFields='" + getCustomFields() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            "}";
    }
}
