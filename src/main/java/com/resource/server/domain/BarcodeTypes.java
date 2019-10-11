package com.resource.server.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BarcodeTypes.
 */
@Entity
@Table(name = "barcode_types")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BarcodeTypes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "barcode_type_name", nullable = false)
    private String barcodeTypeName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcodeTypeName() {
        return barcodeTypeName;
    }

    public BarcodeTypes barcodeTypeName(String barcodeTypeName) {
        this.barcodeTypeName = barcodeTypeName;
        return this;
    }

    public void setBarcodeTypeName(String barcodeTypeName) {
        this.barcodeTypeName = barcodeTypeName;
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
        BarcodeTypes barcodeTypes = (BarcodeTypes) o;
        if (barcodeTypes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), barcodeTypes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BarcodeTypes{" +
            "id=" + getId() +
            ", barcodeTypeName='" + getBarcodeTypeName() + "'" +
            "}";
    }
}
