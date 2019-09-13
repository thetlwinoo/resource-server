package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductAttribute entity.
 */
public class ProductAttributeDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String value;


    private Long productAttributeSetId;

    private String productAttributeSetName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getProductAttributeSetId() {
        return productAttributeSetId;
    }

    public void setProductAttributeSetId(Long productAttributeSetId) {
        this.productAttributeSetId = productAttributeSetId;
    }

    public String getProductAttributeSetName() {
        return productAttributeSetName;
    }

    public void setProductAttributeSetName(String productAttributeSetName) {
        this.productAttributeSetName = productAttributeSetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductAttributeDTO productAttributeDTO = (ProductAttributeDTO) o;
        if (productAttributeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productAttributeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductAttributeDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", productAttributeSet=" + getProductAttributeSetId() +
            ", productAttributeSet='" + getProductAttributeSetName() + "'" +
            "}";
    }
}
