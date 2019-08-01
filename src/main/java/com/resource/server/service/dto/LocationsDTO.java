package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Locations entity.
 */
public class LocationsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String locationName;

    @NotNull
    private Float costRate;

    @NotNull
    private Float availability;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Float getCostRate() {
        return costRate;
    }

    public void setCostRate(Float costRate) {
        this.costRate = costRate;
    }

    public Float getAvailability() {
        return availability;
    }

    public void setAvailability(Float availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationsDTO locationsDTO = (LocationsDTO) o;
        if (locationsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locationsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocationsDTO{" +
            "id=" + getId() +
            ", locationName='" + getLocationName() + "'" +
            ", costRate=" + getCostRate() +
            ", availability=" + getAvailability() +
            "}";
    }
}
