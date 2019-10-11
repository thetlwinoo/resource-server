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
 * Criteria class for the BarcodeTypes entity. This class is used in BarcodeTypesResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /barcode-types?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BarcodeTypesCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter barcodeTypeName;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBarcodeTypeName() {
        return barcodeTypeName;
    }

    public void setBarcodeTypeName(StringFilter barcodeTypeName) {
        this.barcodeTypeName = barcodeTypeName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BarcodeTypesCriteria that = (BarcodeTypesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(barcodeTypeName, that.barcodeTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        barcodeTypeName
        );
    }

    @Override
    public String toString() {
        return "BarcodeTypesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (barcodeTypeName != null ? "barcodeTypeName=" + barcodeTypeName + ", " : "") +
            "}";
    }

}
