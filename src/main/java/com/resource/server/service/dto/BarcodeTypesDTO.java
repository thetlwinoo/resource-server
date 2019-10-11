package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BarcodeTypes entity.
 */
public class BarcodeTypesDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String barcodeTypeName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcodeTypeName() {
        return barcodeTypeName;
    }

    public void setBarcodeTypeName(String barcodeTypeName) {
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

        BarcodeTypesDTO barcodeTypesDTO = (BarcodeTypesDTO) o;
        if (barcodeTypesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), barcodeTypesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BarcodeTypesDTO{" +
            "id=" + getId() +
            ", barcodeTypeName='" + getBarcodeTypeName() + "'" +
            "}";
    }
}
