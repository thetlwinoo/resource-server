package com.resource.server.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UnitMeasure.
 */
@Entity
@Table(name = "unit_measure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnitMeasure extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "unit_measure_code", nullable = false)
    private String unitMeasureCode;

    @NotNull
    @Column(name = "unit_measure_name", nullable = false)
    private String unitMeasureName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitMeasureCode() {
        return unitMeasureCode;
    }

    public UnitMeasure unitMeasureCode(String unitMeasureCode) {
        this.unitMeasureCode = unitMeasureCode;
        return this;
    }

    public void setUnitMeasureCode(String unitMeasureCode) {
        this.unitMeasureCode = unitMeasureCode;
    }

    public String getUnitMeasureName() {
        return unitMeasureName;
    }

    public UnitMeasure unitMeasureName(String unitMeasureName) {
        this.unitMeasureName = unitMeasureName;
        return this;
    }

    public void setUnitMeasureName(String unitMeasureName) {
        this.unitMeasureName = unitMeasureName;
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
        UnitMeasure unitMeasure = (UnitMeasure) o;
        if (unitMeasure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unitMeasure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnitMeasure{" +
            "id=" + getId() +
            ", unitMeasureCode='" + getUnitMeasureCode() + "'" +
            ", unitMeasureName='" + getUnitMeasureName() + "'" +
            "}";
    }
}
