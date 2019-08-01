package com.resource.server.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Locations.
 */
@Entity
@Table(name = "locations")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Locations extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "location_name", nullable = false)
    private String locationName;

    @NotNull
    @Column(name = "cost_rate", nullable = false)
    private Float costRate;

    @NotNull
    @Column(name = "availability", nullable = false)
    private Float availability;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public Locations locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Float getCostRate() {
        return costRate;
    }

    public Locations costRate(Float costRate) {
        this.costRate = costRate;
        return this;
    }

    public void setCostRate(Float costRate) {
        this.costRate = costRate;
    }

    public Float getAvailability() {
        return availability;
    }

    public Locations availability(Float availability) {
        this.availability = availability;
        return this;
    }

    public void setAvailability(Float availability) {
        this.availability = availability;
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
        Locations locations = (Locations) o;
        if (locations.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locations.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Locations{" +
            "id=" + getId() +
            ", locationName='" + getLocationName() + "'" +
            ", costRate=" + getCostRate() +
            ", availability=" + getAvailability() +
            "}";
    }
}
