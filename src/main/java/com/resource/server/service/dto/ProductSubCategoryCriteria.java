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
 * Criteria class for the ProductSubCategory entity. This class is used in ProductSubCategoryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /product-sub-categories?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductSubCategoryCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productSubCategoryName;

    private LongFilter productCategoryId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductSubCategoryName() {
        return productSubCategoryName;
    }

    public void setProductSubCategoryName(StringFilter productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductSubCategoryCriteria that = (ProductSubCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productSubCategoryName, that.productSubCategoryName) &&
            Objects.equals(productCategoryId, that.productCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productSubCategoryName,
        productCategoryId
        );
    }

    @Override
    public String toString() {
        return "ProductSubCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productSubCategoryName != null ? "productSubCategoryName=" + productSubCategoryName + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
            "}";
    }

}
