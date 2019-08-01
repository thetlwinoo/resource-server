package com.resource.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Products entity. This class is used in ProductsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /products?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductsCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productName;

    private StringFilter productNumber;

    private StringFilter searchDetails;

    private BooleanFilter makeFlag;

    private BooleanFilter finishedGoodsFlag;

    private StringFilter color;

    private IntegerFilter safetyStockLevel;

    private IntegerFilter reorderPoint;

    private FloatFilter standardCost;

    private FloatFilter unitPrice;

    private FloatFilter recommendedRetailPrice;

    private StringFilter brand;

    private StringFilter specifySize;

    private FloatFilter weight;

    private IntegerFilter daysToManufacture;

    private StringFilter productLine;

    private StringFilter classType;

    private StringFilter style;

    private StringFilter customFields;

    private StringFilter tags;

    private StringFilter photo;

    private LocalDateFilter sellStartDate;

    private LocalDateFilter sellEndDate;

    private StringFilter marketingComments;

    private StringFilter internalComments;

    private LocalDateFilter discontinuedDate;

    private IntegerFilter sellCount;

    private LongFilter unitPackageId;

    private LongFilter outerPackageId;

    private LongFilter supplierId;

    private LongFilter productSubCategoryId;

    private LongFilter sizeUnitMeasureCodeId;

    private LongFilter weightUnitMeasureCodeId;

    private LongFilter productModelId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductName() {
        return productName;
    }

    public void setProductName(StringFilter productName) {
        this.productName = productName;
    }

    public StringFilter getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(StringFilter productNumber) {
        this.productNumber = productNumber;
    }

    public StringFilter getSearchDetails() {
        return searchDetails;
    }

    public void setSearchDetails(StringFilter searchDetails) {
        this.searchDetails = searchDetails;
    }

    public BooleanFilter getMakeFlag() {
        return makeFlag;
    }

    public void setMakeFlag(BooleanFilter makeFlag) {
        this.makeFlag = makeFlag;
    }

    public BooleanFilter getFinishedGoodsFlag() {
        return finishedGoodsFlag;
    }

    public void setFinishedGoodsFlag(BooleanFilter finishedGoodsFlag) {
        this.finishedGoodsFlag = finishedGoodsFlag;
    }

    public StringFilter getColor() {
        return color;
    }

    public void setColor(StringFilter color) {
        this.color = color;
    }

    public IntegerFilter getSafetyStockLevel() {
        return safetyStockLevel;
    }

    public void setSafetyStockLevel(IntegerFilter safetyStockLevel) {
        this.safetyStockLevel = safetyStockLevel;
    }

    public IntegerFilter getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(IntegerFilter reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public FloatFilter getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(FloatFilter standardCost) {
        this.standardCost = standardCost;
    }

    public FloatFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(FloatFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public FloatFilter getRecommendedRetailPrice() {
        return recommendedRetailPrice;
    }

    public void setRecommendedRetailPrice(FloatFilter recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
    }

    public StringFilter getBrand() {
        return brand;
    }

    public void setBrand(StringFilter brand) {
        this.brand = brand;
    }

    public StringFilter getSpecifySize() {
        return specifySize;
    }

    public void setSpecifySize(StringFilter specifySize) {
        this.specifySize = specifySize;
    }

    public FloatFilter getWeight() {
        return weight;
    }

    public void setWeight(FloatFilter weight) {
        this.weight = weight;
    }

    public IntegerFilter getDaysToManufacture() {
        return daysToManufacture;
    }

    public void setDaysToManufacture(IntegerFilter daysToManufacture) {
        this.daysToManufacture = daysToManufacture;
    }

    public StringFilter getProductLine() {
        return productLine;
    }

    public void setProductLine(StringFilter productLine) {
        this.productLine = productLine;
    }

    public StringFilter getClassType() {
        return classType;
    }

    public void setClassType(StringFilter classType) {
        this.classType = classType;
    }

    public StringFilter getStyle() {
        return style;
    }

    public void setStyle(StringFilter style) {
        this.style = style;
    }

    public StringFilter getCustomFields() {
        return customFields;
    }

    public void setCustomFields(StringFilter customFields) {
        this.customFields = customFields;
    }

    public StringFilter getTags() {
        return tags;
    }

    public void setTags(StringFilter tags) {
        this.tags = tags;
    }

    public StringFilter getPhoto() {
        return photo;
    }

    public void setPhoto(StringFilter photo) {
        this.photo = photo;
    }

    public LocalDateFilter getSellStartDate() {
        return sellStartDate;
    }

    public void setSellStartDate(LocalDateFilter sellStartDate) {
        this.sellStartDate = sellStartDate;
    }

    public LocalDateFilter getSellEndDate() {
        return sellEndDate;
    }

    public void setSellEndDate(LocalDateFilter sellEndDate) {
        this.sellEndDate = sellEndDate;
    }

    public StringFilter getMarketingComments() {
        return marketingComments;
    }

    public void setMarketingComments(StringFilter marketingComments) {
        this.marketingComments = marketingComments;
    }

    public StringFilter getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(StringFilter internalComments) {
        this.internalComments = internalComments;
    }

    public LocalDateFilter getDiscontinuedDate() {
        return discontinuedDate;
    }

    public void setDiscontinuedDate(LocalDateFilter discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public IntegerFilter getSellCount() {
        return sellCount;
    }

    public void setSellCount(IntegerFilter sellCount) {
        this.sellCount = sellCount;
    }

    public LongFilter getUnitPackageId() {
        return unitPackageId;
    }

    public void setUnitPackageId(LongFilter unitPackageId) {
        this.unitPackageId = unitPackageId;
    }

    public LongFilter getOuterPackageId() {
        return outerPackageId;
    }

    public void setOuterPackageId(LongFilter outerPackageId) {
        this.outerPackageId = outerPackageId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getProductSubCategoryId() {
        return productSubCategoryId;
    }

    public void setProductSubCategoryId(LongFilter productSubCategoryId) {
        this.productSubCategoryId = productSubCategoryId;
    }

    public LongFilter getSizeUnitMeasureCodeId() {
        return sizeUnitMeasureCodeId;
    }

    public void setSizeUnitMeasureCodeId(LongFilter sizeUnitMeasureCodeId) {
        this.sizeUnitMeasureCodeId = sizeUnitMeasureCodeId;
    }

    public LongFilter getWeightUnitMeasureCodeId() {
        return weightUnitMeasureCodeId;
    }

    public void setWeightUnitMeasureCodeId(LongFilter weightUnitMeasureCodeId) {
        this.weightUnitMeasureCodeId = weightUnitMeasureCodeId;
    }

    public LongFilter getProductModelId() {
        return productModelId;
    }

    public void setProductModelId(LongFilter productModelId) {
        this.productModelId = productModelId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductsCriteria that = (ProductsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productName, that.productName) &&
            Objects.equals(productNumber, that.productNumber) &&
            Objects.equals(searchDetails, that.searchDetails) &&
            Objects.equals(makeFlag, that.makeFlag) &&
            Objects.equals(finishedGoodsFlag, that.finishedGoodsFlag) &&
            Objects.equals(color, that.color) &&
            Objects.equals(safetyStockLevel, that.safetyStockLevel) &&
            Objects.equals(reorderPoint, that.reorderPoint) &&
            Objects.equals(standardCost, that.standardCost) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(recommendedRetailPrice, that.recommendedRetailPrice) &&
            Objects.equals(brand, that.brand) &&
            Objects.equals(specifySize, that.specifySize) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(daysToManufacture, that.daysToManufacture) &&
            Objects.equals(productLine, that.productLine) &&
            Objects.equals(classType, that.classType) &&
            Objects.equals(style, that.style) &&
            Objects.equals(customFields, that.customFields) &&
            Objects.equals(tags, that.tags) &&
            Objects.equals(photo, that.photo) &&
            Objects.equals(sellStartDate, that.sellStartDate) &&
            Objects.equals(sellEndDate, that.sellEndDate) &&
            Objects.equals(marketingComments, that.marketingComments) &&
            Objects.equals(internalComments, that.internalComments) &&
            Objects.equals(discontinuedDate, that.discontinuedDate) &&
            Objects.equals(sellCount, that.sellCount) &&
            Objects.equals(unitPackageId, that.unitPackageId) &&
            Objects.equals(outerPackageId, that.outerPackageId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(productSubCategoryId, that.productSubCategoryId) &&
            Objects.equals(sizeUnitMeasureCodeId, that.sizeUnitMeasureCodeId) &&
            Objects.equals(weightUnitMeasureCodeId, that.weightUnitMeasureCodeId) &&
            Objects.equals(productModelId, that.productModelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productName,
        productNumber,
        searchDetails,
        makeFlag,
        finishedGoodsFlag,
        color,
        safetyStockLevel,
        reorderPoint,
        standardCost,
        unitPrice,
        recommendedRetailPrice,
        brand,
        specifySize,
        weight,
        daysToManufacture,
        productLine,
        classType,
        style,
        customFields,
        tags,
        photo,
        sellStartDate,
        sellEndDate,
        marketingComments,
        internalComments,
        discontinuedDate,
        sellCount,
        unitPackageId,
        outerPackageId,
        supplierId,
        productSubCategoryId,
        sizeUnitMeasureCodeId,
        weightUnitMeasureCodeId,
        productModelId
        );
    }

    @Override
    public String toString() {
        return "ProductsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productName != null ? "productName=" + productName + ", " : "") +
                (productNumber != null ? "productNumber=" + productNumber + ", " : "") +
                (searchDetails != null ? "searchDetails=" + searchDetails + ", " : "") +
                (makeFlag != null ? "makeFlag=" + makeFlag + ", " : "") +
                (finishedGoodsFlag != null ? "finishedGoodsFlag=" + finishedGoodsFlag + ", " : "") +
                (color != null ? "color=" + color + ", " : "") +
                (safetyStockLevel != null ? "safetyStockLevel=" + safetyStockLevel + ", " : "") +
                (reorderPoint != null ? "reorderPoint=" + reorderPoint + ", " : "") +
                (standardCost != null ? "standardCost=" + standardCost + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (recommendedRetailPrice != null ? "recommendedRetailPrice=" + recommendedRetailPrice + ", " : "") +
                (brand != null ? "brand=" + brand + ", " : "") +
                (specifySize != null ? "specifySize=" + specifySize + ", " : "") +
                (weight != null ? "weight=" + weight + ", " : "") +
                (daysToManufacture != null ? "daysToManufacture=" + daysToManufacture + ", " : "") +
                (productLine != null ? "productLine=" + productLine + ", " : "") +
                (classType != null ? "classType=" + classType + ", " : "") +
                (style != null ? "style=" + style + ", " : "") +
                (customFields != null ? "customFields=" + customFields + ", " : "") +
                (tags != null ? "tags=" + tags + ", " : "") +
                (photo != null ? "photo=" + photo + ", " : "") +
                (sellStartDate != null ? "sellStartDate=" + sellStartDate + ", " : "") +
                (sellEndDate != null ? "sellEndDate=" + sellEndDate + ", " : "") +
                (marketingComments != null ? "marketingComments=" + marketingComments + ", " : "") +
                (internalComments != null ? "internalComments=" + internalComments + ", " : "") +
                (discontinuedDate != null ? "discontinuedDate=" + discontinuedDate + ", " : "") +
                (sellCount != null ? "sellCount=" + sellCount + ", " : "") +
                (unitPackageId != null ? "unitPackageId=" + unitPackageId + ", " : "") +
                (outerPackageId != null ? "outerPackageId=" + outerPackageId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (productSubCategoryId != null ? "productSubCategoryId=" + productSubCategoryId + ", " : "") +
                (sizeUnitMeasureCodeId != null ? "sizeUnitMeasureCodeId=" + sizeUnitMeasureCodeId + ", " : "") +
                (weightUnitMeasureCodeId != null ? "weightUnitMeasureCodeId=" + weightUnitMeasureCodeId + ", " : "") +
                (productModelId != null ? "productModelId=" + productModelId + ", " : "") +
            "}";
    }

}
