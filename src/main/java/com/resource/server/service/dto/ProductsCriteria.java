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

    private StringFilter thumbnailUrl;

    private LocalDateFilter sellStartDate;

    private LocalDateFilter sellEndDate;

    private StringFilter warrantyPeriod;

    private StringFilter warrantyPolicy;

    private IntegerFilter sellCount;

    private StringFilter whatInTheBox;

    private LongFilter supplierId;

    private LongFilter merchantId;

    private LongFilter unitPackageId;

    private LongFilter outerPackageId;

    private LongFilter productModelId;

    private LongFilter productCategoryId;

    private LongFilter productBrandId;

    private LongFilter warrantyTypeId;

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

    public StringFilter getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(StringFilter thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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

    public StringFilter getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(StringFilter warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public StringFilter getWarrantyPolicy() {
        return warrantyPolicy;
    }

    public void setWarrantyPolicy(StringFilter warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
    }

    public IntegerFilter getSellCount() {
        return sellCount;
    }

    public void setSellCount(IntegerFilter sellCount) {
        this.sellCount = sellCount;
    }

    public StringFilter getWhatInTheBox() {
        return whatInTheBox;
    }

    public void setWhatInTheBox(StringFilter whatInTheBox) {
        this.whatInTheBox = whatInTheBox;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(LongFilter merchantId) {
        this.merchantId = merchantId;
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

    public LongFilter getProductModelId() {
        return productModelId;
    }

    public void setProductModelId(LongFilter productModelId) {
        this.productModelId = productModelId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(LongFilter productBrandId) {
        this.productBrandId = productBrandId;
    }

    public LongFilter getWarrantyTypeId() {
        return warrantyTypeId;
    }

    public void setWarrantyTypeId(LongFilter warrantyTypeId) {
        this.warrantyTypeId = warrantyTypeId;
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
            Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
            Objects.equals(sellStartDate, that.sellStartDate) &&
            Objects.equals(sellEndDate, that.sellEndDate) &&
            Objects.equals(warrantyPeriod, that.warrantyPeriod) &&
            Objects.equals(warrantyPolicy, that.warrantyPolicy) &&
            Objects.equals(sellCount, that.sellCount) &&
            Objects.equals(whatInTheBox, that.whatInTheBox) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(merchantId, that.merchantId) &&
            Objects.equals(unitPackageId, that.unitPackageId) &&
            Objects.equals(outerPackageId, that.outerPackageId) &&
            Objects.equals(productModelId, that.productModelId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productBrandId, that.productBrandId) &&
            Objects.equals(warrantyTypeId, that.warrantyTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productName,
        productNumber,
        searchDetails,
        thumbnailUrl,
        sellStartDate,
        sellEndDate,
        warrantyPeriod,
        warrantyPolicy,
        sellCount,
        whatInTheBox,
        supplierId,
        merchantId,
        unitPackageId,
        outerPackageId,
        productModelId,
        productCategoryId,
        productBrandId,
        warrantyTypeId
        );
    }

    @Override
    public String toString() {
        return "ProductsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productName != null ? "productName=" + productName + ", " : "") +
                (productNumber != null ? "productNumber=" + productNumber + ", " : "") +
                (searchDetails != null ? "searchDetails=" + searchDetails + ", " : "") +
                (thumbnailUrl != null ? "thumbnailUrl=" + thumbnailUrl + ", " : "") +
                (sellStartDate != null ? "sellStartDate=" + sellStartDate + ", " : "") +
                (sellEndDate != null ? "sellEndDate=" + sellEndDate + ", " : "") +
                (warrantyPeriod != null ? "warrantyPeriod=" + warrantyPeriod + ", " : "") +
                (warrantyPolicy != null ? "warrantyPolicy=" + warrantyPolicy + ", " : "") +
                (sellCount != null ? "sellCount=" + sellCount + ", " : "") +
                (whatInTheBox != null ? "whatInTheBox=" + whatInTheBox + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (merchantId != null ? "merchantId=" + merchantId + ", " : "") +
                (unitPackageId != null ? "unitPackageId=" + unitPackageId + ", " : "") +
                (outerPackageId != null ? "outerPackageId=" + outerPackageId + ", " : "") +
                (productModelId != null ? "productModelId=" + productModelId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productBrandId != null ? "productBrandId=" + productBrandId + ", " : "") +
                (warrantyTypeId != null ? "warrantyTypeId=" + warrantyTypeId + ", " : "") +
            "}";
    }

}
