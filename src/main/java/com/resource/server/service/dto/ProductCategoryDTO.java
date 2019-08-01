package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductCategory entity.
 */
public class ProductCategoryDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String productCategoryName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductCategoryDTO productCategoryDTO = (ProductCategoryDTO) o;
        if (productCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
            "id=" + getId() +
            ", productCategoryName='" + getProductCategoryName() + "'" +
            "}";
    }
}
