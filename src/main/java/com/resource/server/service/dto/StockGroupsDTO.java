package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StockGroups entity.
 */
public class StockGroupsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String stockGroupName;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockGroupName() {
        return stockGroupName;
    }

    public void setStockGroupName(String stockGroupName) {
        this.stockGroupName = stockGroupName;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockGroupsDTO stockGroupsDTO = (StockGroupsDTO) o;
        if (stockGroupsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockGroupsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockGroupsDTO{" +
            "id=" + getId() +
            ", stockGroupName='" + getStockGroupName() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
