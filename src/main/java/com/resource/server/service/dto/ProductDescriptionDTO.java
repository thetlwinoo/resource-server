package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductDescription entity.
 */
public class ProductDescriptionDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;


    private Long productModelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductModelId() {
        return productModelId;
    }

    public void setProductModelId(Long productModelId) {
        this.productModelId = productModelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDescriptionDTO productDescriptionDTO = (ProductDescriptionDTO) o;
        if (productDescriptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDescriptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDescriptionDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", productModel=" + getProductModelId() +
            "}";
    }
}
