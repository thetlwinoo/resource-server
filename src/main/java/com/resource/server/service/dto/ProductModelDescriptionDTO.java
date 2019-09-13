package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.resource.server.domain.enumeration.Language;

/**
 * A DTO for the ProductModelDescription entity.
 */
public class ProductModelDescriptionDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private Language language;


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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
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

        ProductModelDescriptionDTO productModelDescriptionDTO = (ProductModelDescriptionDTO) o;
        if (productModelDescriptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productModelDescriptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductModelDescriptionDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", language='" + getLanguage() + "'" +
            ", productModel=" + getProductModelId() +
            "}";
    }
}
