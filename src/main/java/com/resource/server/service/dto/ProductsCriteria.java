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

    private StringFilter handle;

    private StringFilter productNumber;

    private IntegerFilter sellCount;

    private BooleanFilter activeInd;

    private LongFilter documentId;

    private LongFilter stockItemListId;

    private LongFilter supplierId;

    private LongFilter productCategoryId;

    private LongFilter productBrandId;

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

    public StringFilter getHandle() {
        return handle;
    }

    public void setHandle(StringFilter handle) {
        this.handle = handle;
    }

    public StringFilter getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(StringFilter productNumber) {
        this.productNumber = productNumber;
    }

    public IntegerFilter getSellCount() {
        return sellCount;
    }

    public void setSellCount(IntegerFilter sellCount) {
        this.sellCount = sellCount;
    }

    public BooleanFilter getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(BooleanFilter activeInd) {
        this.activeInd = activeInd;
    }

    public LongFilter getDocumentId() {
        return documentId;
    }

    public void setDocumentId(LongFilter documentId) {
        this.documentId = documentId;
    }

    public LongFilter getStockItemListId() {
        return stockItemListId;
    }

    public void setStockItemListId(LongFilter stockItemListId) {
        this.stockItemListId = stockItemListId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
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
            Objects.equals(handle, that.handle) &&
            Objects.equals(productNumber, that.productNumber) &&
            Objects.equals(sellCount, that.sellCount) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(documentId, that.documentId) &&
            Objects.equals(stockItemListId, that.stockItemListId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productBrandId, that.productBrandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productName,
        handle,
        productNumber,
        sellCount,
        activeInd,
        documentId,
        stockItemListId,
        supplierId,
        productCategoryId,
        productBrandId
        );
    }

    @Override
    public String toString() {
        return "ProductsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productName != null ? "productName=" + productName + ", " : "") +
                (handle != null ? "handle=" + handle + ", " : "") +
                (productNumber != null ? "productNumber=" + productNumber + ", " : "") +
                (sellCount != null ? "sellCount=" + sellCount + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (documentId != null ? "documentId=" + documentId + ", " : "") +
                (stockItemListId != null ? "stockItemListId=" + stockItemListId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productBrandId != null ? "productBrandId=" + productBrandId + ", " : "") +
            "}";
    }

}
