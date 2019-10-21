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
 * Criteria class for the StockItems entity. This class is used in StockItemsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /stock-items?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockItemsCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter stockItemName;

    private StringFilter vendorCode;

    private StringFilter vendorSKU;

    private StringFilter generatedSKU;

    private StringFilter barcode;

    private FloatFilter unitPrice;

    private FloatFilter recommendedRetailPrice;

    private IntegerFilter quantityOnHand;

    private IntegerFilter itemLength;

    private IntegerFilter itemWidth;

    private IntegerFilter itemHeight;

    private FloatFilter itemWeight;

    private IntegerFilter itemPackageLength;

    private IntegerFilter itemPackageWidth;

    private IntegerFilter itemPackageHeight;

    private FloatFilter itemPackageWeight;

    private IntegerFilter noOfPieces;

    private IntegerFilter noOfItems;

    private StringFilter manufacture;

    private StringFilter marketingComments;

    private StringFilter internalComments;

    private LocalDateFilter sellStartDate;

    private LocalDateFilter sellEndDate;

    private IntegerFilter sellCount;

    private StringFilter customFields;

    private StringFilter thumbnailUrl;

    private BooleanFilter activeInd;

    private LongFilter stockItemOnReviewLineId;

    private LongFilter photoListId;

    private LongFilter dangerousGoodListId;

    private LongFilter specialDiscountId;

    private LongFilter itemLengthUnitId;

    private LongFilter itemWidthUnitId;

    private LongFilter itemHeightUnitId;

    private LongFilter packageLengthUnitId;

    private LongFilter packageWidthUnitId;

    private LongFilter packageHeightUnitId;

    private LongFilter itemPackageWeightUnitId;

    private LongFilter productAttributeId;

    private LongFilter productOptionId;

    private LongFilter materialId;

    private LongFilter currencyId;

    private LongFilter barcodeTypeId;

    private LongFilter stockItemHoldingId;

    private LongFilter productId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(StringFilter stockItemName) {
        this.stockItemName = stockItemName;
    }

    public StringFilter getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(StringFilter vendorCode) {
        this.vendorCode = vendorCode;
    }

    public StringFilter getVendorSKU() {
        return vendorSKU;
    }

    public void setVendorSKU(StringFilter vendorSKU) {
        this.vendorSKU = vendorSKU;
    }

    public StringFilter getGeneratedSKU() {
        return generatedSKU;
    }

    public void setGeneratedSKU(StringFilter generatedSKU) {
        this.generatedSKU = generatedSKU;
    }

    public StringFilter getBarcode() {
        return barcode;
    }

    public void setBarcode(StringFilter barcode) {
        this.barcode = barcode;
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

    public IntegerFilter getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(IntegerFilter quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public IntegerFilter getItemLength() {
        return itemLength;
    }

    public void setItemLength(IntegerFilter itemLength) {
        this.itemLength = itemLength;
    }

    public IntegerFilter getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(IntegerFilter itemWidth) {
        this.itemWidth = itemWidth;
    }

    public IntegerFilter getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(IntegerFilter itemHeight) {
        this.itemHeight = itemHeight;
    }

    public FloatFilter getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(FloatFilter itemWeight) {
        this.itemWeight = itemWeight;
    }

    public IntegerFilter getItemPackageLength() {
        return itemPackageLength;
    }

    public void setItemPackageLength(IntegerFilter itemPackageLength) {
        this.itemPackageLength = itemPackageLength;
    }

    public IntegerFilter getItemPackageWidth() {
        return itemPackageWidth;
    }

    public void setItemPackageWidth(IntegerFilter itemPackageWidth) {
        this.itemPackageWidth = itemPackageWidth;
    }

    public IntegerFilter getItemPackageHeight() {
        return itemPackageHeight;
    }

    public void setItemPackageHeight(IntegerFilter itemPackageHeight) {
        this.itemPackageHeight = itemPackageHeight;
    }

    public FloatFilter getItemPackageWeight() {
        return itemPackageWeight;
    }

    public void setItemPackageWeight(FloatFilter itemPackageWeight) {
        this.itemPackageWeight = itemPackageWeight;
    }

    public IntegerFilter getNoOfPieces() {
        return noOfPieces;
    }

    public void setNoOfPieces(IntegerFilter noOfPieces) {
        this.noOfPieces = noOfPieces;
    }

    public IntegerFilter getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(IntegerFilter noOfItems) {
        this.noOfItems = noOfItems;
    }

    public StringFilter getManufacture() {
        return manufacture;
    }

    public void setManufacture(StringFilter manufacture) {
        this.manufacture = manufacture;
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

    public IntegerFilter getSellCount() {
        return sellCount;
    }

    public void setSellCount(IntegerFilter sellCount) {
        this.sellCount = sellCount;
    }

    public StringFilter getCustomFields() {
        return customFields;
    }

    public void setCustomFields(StringFilter customFields) {
        this.customFields = customFields;
    }

    public StringFilter getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(StringFilter thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public BooleanFilter getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(BooleanFilter activeInd) {
        this.activeInd = activeInd;
    }

    public LongFilter getStockItemOnReviewLineId() {
        return stockItemOnReviewLineId;
    }

    public void setStockItemOnReviewLineId(LongFilter stockItemOnReviewLineId) {
        this.stockItemOnReviewLineId = stockItemOnReviewLineId;
    }

    public LongFilter getPhotoListId() {
        return photoListId;
    }

    public void setPhotoListId(LongFilter photoListId) {
        this.photoListId = photoListId;
    }

    public LongFilter getDangerousGoodListId() {
        return dangerousGoodListId;
    }

    public void setDangerousGoodListId(LongFilter dangerousGoodListId) {
        this.dangerousGoodListId = dangerousGoodListId;
    }

    public LongFilter getSpecialDiscountId() {
        return specialDiscountId;
    }

    public void setSpecialDiscountId(LongFilter specialDiscountId) {
        this.specialDiscountId = specialDiscountId;
    }

    public LongFilter getItemLengthUnitId() {
        return itemLengthUnitId;
    }

    public void setItemLengthUnitId(LongFilter itemLengthUnitId) {
        this.itemLengthUnitId = itemLengthUnitId;
    }

    public LongFilter getItemWidthUnitId() {
        return itemWidthUnitId;
    }

    public void setItemWidthUnitId(LongFilter itemWidthUnitId) {
        this.itemWidthUnitId = itemWidthUnitId;
    }

    public LongFilter getItemHeightUnitId() {
        return itemHeightUnitId;
    }

    public void setItemHeightUnitId(LongFilter itemHeightUnitId) {
        this.itemHeightUnitId = itemHeightUnitId;
    }

    public LongFilter getPackageLengthUnitId() {
        return packageLengthUnitId;
    }

    public void setPackageLengthUnitId(LongFilter packageLengthUnitId) {
        this.packageLengthUnitId = packageLengthUnitId;
    }

    public LongFilter getPackageWidthUnitId() {
        return packageWidthUnitId;
    }

    public void setPackageWidthUnitId(LongFilter packageWidthUnitId) {
        this.packageWidthUnitId = packageWidthUnitId;
    }

    public LongFilter getPackageHeightUnitId() {
        return packageHeightUnitId;
    }

    public void setPackageHeightUnitId(LongFilter packageHeightUnitId) {
        this.packageHeightUnitId = packageHeightUnitId;
    }

    public LongFilter getItemPackageWeightUnitId() {
        return itemPackageWeightUnitId;
    }

    public void setItemPackageWeightUnitId(LongFilter itemPackageWeightUnitId) {
        this.itemPackageWeightUnitId = itemPackageWeightUnitId;
    }

    public LongFilter getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(LongFilter productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public LongFilter getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(LongFilter productOptionId) {
        this.productOptionId = productOptionId;
    }

    public LongFilter getMaterialId() {
        return materialId;
    }

    public void setMaterialId(LongFilter materialId) {
        this.materialId = materialId;
    }

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }

    public LongFilter getBarcodeTypeId() {
        return barcodeTypeId;
    }

    public void setBarcodeTypeId(LongFilter barcodeTypeId) {
        this.barcodeTypeId = barcodeTypeId;
    }

    public LongFilter getStockItemHoldingId() {
        return stockItemHoldingId;
    }

    public void setStockItemHoldingId(LongFilter stockItemHoldingId) {
        this.stockItemHoldingId = stockItemHoldingId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockItemsCriteria that = (StockItemsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stockItemName, that.stockItemName) &&
            Objects.equals(vendorCode, that.vendorCode) &&
            Objects.equals(vendorSKU, that.vendorSKU) &&
            Objects.equals(generatedSKU, that.generatedSKU) &&
            Objects.equals(barcode, that.barcode) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(recommendedRetailPrice, that.recommendedRetailPrice) &&
            Objects.equals(quantityOnHand, that.quantityOnHand) &&
            Objects.equals(itemLength, that.itemLength) &&
            Objects.equals(itemWidth, that.itemWidth) &&
            Objects.equals(itemHeight, that.itemHeight) &&
            Objects.equals(itemWeight, that.itemWeight) &&
            Objects.equals(itemPackageLength, that.itemPackageLength) &&
            Objects.equals(itemPackageWidth, that.itemPackageWidth) &&
            Objects.equals(itemPackageHeight, that.itemPackageHeight) &&
            Objects.equals(itemPackageWeight, that.itemPackageWeight) &&
            Objects.equals(noOfPieces, that.noOfPieces) &&
            Objects.equals(noOfItems, that.noOfItems) &&
            Objects.equals(manufacture, that.manufacture) &&
            Objects.equals(marketingComments, that.marketingComments) &&
            Objects.equals(internalComments, that.internalComments) &&
            Objects.equals(sellStartDate, that.sellStartDate) &&
            Objects.equals(sellEndDate, that.sellEndDate) &&
            Objects.equals(sellCount, that.sellCount) &&
            Objects.equals(customFields, that.customFields) &&
            Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(stockItemOnReviewLineId, that.stockItemOnReviewLineId) &&
            Objects.equals(photoListId, that.photoListId) &&
            Objects.equals(dangerousGoodListId, that.dangerousGoodListId) &&
            Objects.equals(specialDiscountId, that.specialDiscountId) &&
            Objects.equals(itemLengthUnitId, that.itemLengthUnitId) &&
            Objects.equals(itemWidthUnitId, that.itemWidthUnitId) &&
            Objects.equals(itemHeightUnitId, that.itemHeightUnitId) &&
            Objects.equals(packageLengthUnitId, that.packageLengthUnitId) &&
            Objects.equals(packageWidthUnitId, that.packageWidthUnitId) &&
            Objects.equals(packageHeightUnitId, that.packageHeightUnitId) &&
            Objects.equals(itemPackageWeightUnitId, that.itemPackageWeightUnitId) &&
            Objects.equals(productAttributeId, that.productAttributeId) &&
            Objects.equals(productOptionId, that.productOptionId) &&
            Objects.equals(materialId, that.materialId) &&
            Objects.equals(currencyId, that.currencyId) &&
            Objects.equals(barcodeTypeId, that.barcodeTypeId) &&
            Objects.equals(stockItemHoldingId, that.stockItemHoldingId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stockItemName,
        vendorCode,
        vendorSKU,
        generatedSKU,
        barcode,
        unitPrice,
        recommendedRetailPrice,
        quantityOnHand,
        itemLength,
        itemWidth,
        itemHeight,
        itemWeight,
        itemPackageLength,
        itemPackageWidth,
        itemPackageHeight,
        itemPackageWeight,
        noOfPieces,
        noOfItems,
        manufacture,
        marketingComments,
        internalComments,
        sellStartDate,
        sellEndDate,
        sellCount,
        customFields,
        thumbnailUrl,
        activeInd,
        stockItemOnReviewLineId,
        photoListId,
        dangerousGoodListId,
        specialDiscountId,
        itemLengthUnitId,
        itemWidthUnitId,
        itemHeightUnitId,
        packageLengthUnitId,
        packageWidthUnitId,
        packageHeightUnitId,
        itemPackageWeightUnitId,
        productAttributeId,
        productOptionId,
        materialId,
        currencyId,
        barcodeTypeId,
        stockItemHoldingId,
        productId
        );
    }

    @Override
    public String toString() {
        return "StockItemsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stockItemName != null ? "stockItemName=" + stockItemName + ", " : "") +
                (vendorCode != null ? "vendorCode=" + vendorCode + ", " : "") +
                (vendorSKU != null ? "vendorSKU=" + vendorSKU + ", " : "") +
                (generatedSKU != null ? "generatedSKU=" + generatedSKU + ", " : "") +
                (barcode != null ? "barcode=" + barcode + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (recommendedRetailPrice != null ? "recommendedRetailPrice=" + recommendedRetailPrice + ", " : "") +
                (quantityOnHand != null ? "quantityOnHand=" + quantityOnHand + ", " : "") +
                (itemLength != null ? "itemLength=" + itemLength + ", " : "") +
                (itemWidth != null ? "itemWidth=" + itemWidth + ", " : "") +
                (itemHeight != null ? "itemHeight=" + itemHeight + ", " : "") +
                (itemWeight != null ? "itemWeight=" + itemWeight + ", " : "") +
                (itemPackageLength != null ? "itemPackageLength=" + itemPackageLength + ", " : "") +
                (itemPackageWidth != null ? "itemPackageWidth=" + itemPackageWidth + ", " : "") +
                (itemPackageHeight != null ? "itemPackageHeight=" + itemPackageHeight + ", " : "") +
                (itemPackageWeight != null ? "itemPackageWeight=" + itemPackageWeight + ", " : "") +
                (noOfPieces != null ? "noOfPieces=" + noOfPieces + ", " : "") +
                (noOfItems != null ? "noOfItems=" + noOfItems + ", " : "") +
                (manufacture != null ? "manufacture=" + manufacture + ", " : "") +
                (marketingComments != null ? "marketingComments=" + marketingComments + ", " : "") +
                (internalComments != null ? "internalComments=" + internalComments + ", " : "") +
                (sellStartDate != null ? "sellStartDate=" + sellStartDate + ", " : "") +
                (sellEndDate != null ? "sellEndDate=" + sellEndDate + ", " : "") +
                (sellCount != null ? "sellCount=" + sellCount + ", " : "") +
                (customFields != null ? "customFields=" + customFields + ", " : "") +
                (thumbnailUrl != null ? "thumbnailUrl=" + thumbnailUrl + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (stockItemOnReviewLineId != null ? "stockItemOnReviewLineId=" + stockItemOnReviewLineId + ", " : "") +
                (photoListId != null ? "photoListId=" + photoListId + ", " : "") +
                (dangerousGoodListId != null ? "dangerousGoodListId=" + dangerousGoodListId + ", " : "") +
                (specialDiscountId != null ? "specialDiscountId=" + specialDiscountId + ", " : "") +
                (itemLengthUnitId != null ? "itemLengthUnitId=" + itemLengthUnitId + ", " : "") +
                (itemWidthUnitId != null ? "itemWidthUnitId=" + itemWidthUnitId + ", " : "") +
                (itemHeightUnitId != null ? "itemHeightUnitId=" + itemHeightUnitId + ", " : "") +
                (packageLengthUnitId != null ? "packageLengthUnitId=" + packageLengthUnitId + ", " : "") +
                (packageWidthUnitId != null ? "packageWidthUnitId=" + packageWidthUnitId + ", " : "") +
                (packageHeightUnitId != null ? "packageHeightUnitId=" + packageHeightUnitId + ", " : "") +
                (itemPackageWeightUnitId != null ? "itemPackageWeightUnitId=" + itemPackageWeightUnitId + ", " : "") +
                (productAttributeId != null ? "productAttributeId=" + productAttributeId + ", " : "") +
                (productOptionId != null ? "productOptionId=" + productOptionId + ", " : "") +
                (materialId != null ? "materialId=" + materialId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
                (barcodeTypeId != null ? "barcodeTypeId=" + barcodeTypeId + ", " : "") +
                (stockItemHoldingId != null ? "stockItemHoldingId=" + stockItemHoldingId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
