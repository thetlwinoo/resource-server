package com.resource.server.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AddressTypes.
 */
@Entity
@Table(name = "address_types")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AddressTypes extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "address_type_name", nullable = false)
    private String addressTypeName;

    @Column(name = "refer")
    private String refer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public AddressTypes addressTypeName(String addressTypeName) {
        this.addressTypeName = addressTypeName;
        return this;
    }

    public void setAddressTypeName(String addressTypeName) {
        this.addressTypeName = addressTypeName;
    }

    public String getRefer() {
        return refer;
    }

    public AddressTypes refer(String refer) {
        this.refer = refer;
        return this;
    }

    public void setRefer(String refer) {
        this.refer = refer;
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
        AddressTypes addressTypes = (AddressTypes) o;
        if (addressTypes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressTypes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressTypes{" +
            "id=" + getId() +
            ", addressTypeName='" + getAddressTypeName() + "'" +
            ", refer='" + getRefer() + "'" +
            "}";
    }
}
