package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SystemParameters.
 */
@Entity
@Table(name = "system_parameters")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SystemParameters extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "application_settings", nullable = false)
    private String applicationSettings;

    @ManyToOne
    @JsonIgnoreProperties("systemParameters")
    private Cities deliveryCity;

    @ManyToOne
    @JsonIgnoreProperties("systemParameters")
    private Cities postalCity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationSettings() {
        return applicationSettings;
    }

    public SystemParameters applicationSettings(String applicationSettings) {
        this.applicationSettings = applicationSettings;
        return this;
    }

    public void setApplicationSettings(String applicationSettings) {
        this.applicationSettings = applicationSettings;
    }

    public Cities getDeliveryCity() {
        return deliveryCity;
    }

    public SystemParameters deliveryCity(Cities cities) {
        this.deliveryCity = cities;
        return this;
    }

    public void setDeliveryCity(Cities cities) {
        this.deliveryCity = cities;
    }

    public Cities getPostalCity() {
        return postalCity;
    }

    public SystemParameters postalCity(Cities cities) {
        this.postalCity = cities;
        return this;
    }

    public void setPostalCity(Cities cities) {
        this.postalCity = cities;
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
        SystemParameters systemParameters = (SystemParameters) o;
        if (systemParameters.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemParameters.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemParameters{" +
            "id=" + getId() +
            ", applicationSettings='" + getApplicationSettings() + "'" +
            "}";
    }
}
