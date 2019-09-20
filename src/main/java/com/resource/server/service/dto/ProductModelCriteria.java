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
 * Criteria class for the ProductModel entity. This class is used in ProductModelResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /product-models?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductModelCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productModelName;

    private StringFilter calalogDescription;

    private StringFilter instructions;

    private LongFilter descriptionId;

    private LongFilter merchantId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductModelName() {
        return productModelName;
    }

    public void setProductModelName(StringFilter productModelName) {
        this.productModelName = productModelName;
    }

    public StringFilter getCalalogDescription() {
        return calalogDescription;
    }

    public void setCalalogDescription(StringFilter calalogDescription) {
        this.calalogDescription = calalogDescription;
    }

    public StringFilter getInstructions() {
        return instructions;
    }

    public void setInstructions(StringFilter instructions) {
        this.instructions = instructions;
    }

    public LongFilter getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(LongFilter descriptionId) {
        this.descriptionId = descriptionId;
    }

    public LongFilter getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(LongFilter merchantId) {
        this.merchantId = merchantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductModelCriteria that = (ProductModelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productModelName, that.productModelName) &&
            Objects.equals(calalogDescription, that.calalogDescription) &&
            Objects.equals(instructions, that.instructions) &&
            Objects.equals(descriptionId, that.descriptionId) &&
            Objects.equals(merchantId, that.merchantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productModelName,
        calalogDescription,
        instructions,
        descriptionId,
        merchantId
        );
    }

    @Override
    public String toString() {
        return "ProductModelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productModelName != null ? "productModelName=" + productModelName + ", " : "") +
                (calalogDescription != null ? "calalogDescription=" + calalogDescription + ", " : "") +
                (instructions != null ? "instructions=" + instructions + ", " : "") +
                (descriptionId != null ? "descriptionId=" + descriptionId + ", " : "") +
                (merchantId != null ? "merchantId=" + merchantId + ", " : "") +
            "}";
    }

}
