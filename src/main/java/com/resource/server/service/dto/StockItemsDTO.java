package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StockItems entity.
 */
public class StockItemsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String stockItemName;

    private String sellerSKU;

    private String generatedSKU;

    private String barcode;

    @NotNull
    private Float unitPrice;

    private Float recommendedRetailPrice;

    @NotNull
    private Integer quantityPerOuter;

    private Float typicalWeightPerUnit;

    private Integer typicalLengthPerUnit;

    private Integer typicalWidthPerUnit;

    private Integer typicalHeightPerUnit;

    private String marketingComments;

    private String internalComments;

    private LocalDate discontinuedDate;

    private Integer sellCount;

    private String customFields;

    private String thumbnailUrl;


    private Long reviewLineId;

    private Long productId;

    private String productProductName;

    private Long lengthUnitMeasureCodeId;

    private String lengthUnitMeasureCodeUnitMeasureCode;

    private Long weightUnitMeasureCodeId;

    private String weightUnitMeasureCodeUnitMeasureCode;

    private Long widthUnitMeasureCodeId;

    private String widthUnitMeasureCodeUnitMeasureCode;

    private Long heightUnitMeasureCodeId;

    private String heightUnitMeasureCodeUnitMeasureCode;

    private Long productAttributeId;

    private String productAttributeValue;

    private Long productOptionId;

    private String productOptionValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(String stockItemName) {
        this.stockItemName = stockItemName;
    }

    public String getSellerSKU() {
        return sellerSKU;
    }

    public void setSellerSKU(String sellerSKU) {
        this.sellerSKU = sellerSKU;
    }

    public String getGeneratedSKU() {
        return generatedSKU;
    }

    public void setGeneratedSKU(String generatedSKU) {
        this.generatedSKU = generatedSKU;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getRecommendedRetailPrice() {
        return recommendedRetailPrice;
    }

    public void setRecommendedRetailPrice(Float recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
    }

    public Integer getQuantityPerOuter() {
        return quantityPerOuter;
    }

    public void setQuantityPerOuter(Integer quantityPerOuter) {
        this.quantityPerOuter = quantityPerOuter;
    }

    public Float getTypicalWeightPerUnit() {
        return typicalWeightPerUnit;
    }

    public void setTypicalWeightPerUnit(Float typicalWeightPerUnit) {
        this.typicalWeightPerUnit = typicalWeightPerUnit;
    }

    public Integer getTypicalLengthPerUnit() {
        return typicalLengthPerUnit;
    }

    public void setTypicalLengthPerUnit(Integer typicalLengthPerUnit) {
        this.typicalLengthPerUnit = typicalLengthPerUnit;
    }

    public Integer getTypicalWidthPerUnit() {
        return typicalWidthPerUnit;
    }

    public void setTypicalWidthPerUnit(Integer typicalWidthPerUnit) {
        this.typicalWidthPerUnit = typicalWidthPerUnit;
    }

    public Integer getTypicalHeightPerUnit() {
        return typicalHeightPerUnit;
    }

    public void setTypicalHeightPerUnit(Integer typicalHeightPerUnit) {
        this.typicalHeightPerUnit = typicalHeightPerUnit;
    }

    public String getMarketingComments() {
        return marketingComments;
    }

    public void setMarketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public LocalDate getDiscontinuedDate() {
        return discontinuedDate;
    }

    public void setDiscontinuedDate(LocalDate discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getCustomFields() {
        return customFields;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getReviewLineId() {
        return reviewLineId;
    }

    public void setReviewLineId(Long reviewLinesId) {
        this.reviewLineId = reviewLinesId;
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

    public Long getLengthUnitMeasureCodeId() {
        return lengthUnitMeasureCodeId;
    }

    public void setLengthUnitMeasureCodeId(Long unitMeasureId) {
        this.lengthUnitMeasureCodeId = unitMeasureId;
    }

    public String getLengthUnitMeasureCodeUnitMeasureCode() {
        return lengthUnitMeasureCodeUnitMeasureCode;
    }

    public void setLengthUnitMeasureCodeUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.lengthUnitMeasureCodeUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getWeightUnitMeasureCodeId() {
        return weightUnitMeasureCodeId;
    }

    public void setWeightUnitMeasureCodeId(Long unitMeasureId) {
        this.weightUnitMeasureCodeId = unitMeasureId;
    }

    public String getWeightUnitMeasureCodeUnitMeasureCode() {
        return weightUnitMeasureCodeUnitMeasureCode;
    }

    public void setWeightUnitMeasureCodeUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.weightUnitMeasureCodeUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getWidthUnitMeasureCodeId() {
        return widthUnitMeasureCodeId;
    }

    public void setWidthUnitMeasureCodeId(Long unitMeasureId) {
        this.widthUnitMeasureCodeId = unitMeasureId;
    }

    public String getWidthUnitMeasureCodeUnitMeasureCode() {
        return widthUnitMeasureCodeUnitMeasureCode;
    }

    public void setWidthUnitMeasureCodeUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.widthUnitMeasureCodeUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getHeightUnitMeasureCodeId() {
        return heightUnitMeasureCodeId;
    }

    public void setHeightUnitMeasureCodeId(Long unitMeasureId) {
        this.heightUnitMeasureCodeId = unitMeasureId;
    }

    public String getHeightUnitMeasureCodeUnitMeasureCode() {
        return heightUnitMeasureCodeUnitMeasureCode;
    }

    public void setHeightUnitMeasureCodeUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.heightUnitMeasureCodeUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getProductAttributeValue() {
        return productAttributeValue;
    }

    public void setProductAttributeValue(String productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getProductOptionValue() {
        return productOptionValue;
    }

    public void setProductOptionValue(String productOptionValue) {
        this.productOptionValue = productOptionValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItemsDTO stockItemsDTO = (StockItemsDTO) o;
        if (stockItemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItemsDTO{" +
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
            ", reviewLine=" + getReviewLineId() +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            ", lengthUnitMeasureCode=" + getLengthUnitMeasureCodeId() +
            ", lengthUnitMeasureCode='" + getLengthUnitMeasureCodeUnitMeasureCode() + "'" +
            ", weightUnitMeasureCode=" + getWeightUnitMeasureCodeId() +
            ", weightUnitMeasureCode='" + getWeightUnitMeasureCodeUnitMeasureCode() + "'" +
            ", widthUnitMeasureCode=" + getWidthUnitMeasureCodeId() +
            ", widthUnitMeasureCode='" + getWidthUnitMeasureCodeUnitMeasureCode() + "'" +
            ", heightUnitMeasureCode=" + getHeightUnitMeasureCodeId() +
            ", heightUnitMeasureCode='" + getHeightUnitMeasureCodeUnitMeasureCode() + "'" +
            ", productAttribute=" + getProductAttributeId() +
            ", productAttribute='" + getProductAttributeValue() + "'" +
            ", productOption=" + getProductOptionId() +
            ", productOption='" + getProductOptionValue() + "'" +
            "}";
    }
}
