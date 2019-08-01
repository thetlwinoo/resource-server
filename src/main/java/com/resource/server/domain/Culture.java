package com.resource.server.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Culture.
 */
@Entity
@Table(name = "culture")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Culture extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "culture_code", nullable = false)
    private String cultureCode;

    @NotNull
    @Column(name = "culture_name", nullable = false)
    private String cultureName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCultureCode() {
        return cultureCode;
    }

    public Culture cultureCode(String cultureCode) {
        this.cultureCode = cultureCode;
        return this;
    }

    public void setCultureCode(String cultureCode) {
        this.cultureCode = cultureCode;
    }

    public String getCultureName() {
        return cultureName;
    }

    public Culture cultureName(String cultureName) {
        this.cultureName = cultureName;
        return this;
    }

    public void setCultureName(String cultureName) {
        this.cultureName = cultureName;
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
        Culture culture = (Culture) o;
        if (culture.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), culture.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Culture{" +
            "id=" + getId() +
            ", cultureCode='" + getCultureCode() + "'" +
            ", cultureName='" + getCultureName() + "'" +
            "}";
    }
}
