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
 * Criteria class for the WarrantyTypes entity. This class is used in WarrantyTypesResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /warranty-types?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WarrantyTypesCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter warrantyTypeName;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getWarrantyTypeName() {
        return warrantyTypeName;
    }

    public void setWarrantyTypeName(StringFilter warrantyTypeName) {
        this.warrantyTypeName = warrantyTypeName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WarrantyTypesCriteria that = (WarrantyTypesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(warrantyTypeName, that.warrantyTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        warrantyTypeName
        );
    }

    @Override
    public String toString() {
        return "WarrantyTypesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (warrantyTypeName != null ? "warrantyTypeName=" + warrantyTypeName + ", " : "") +
            "}";
    }

}
