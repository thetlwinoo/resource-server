package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ProductSubCategory entity.
 */
public class ProductSubCategoryDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String productSubCategoryName;

    @Lob
    private byte[] photo;

    private String photoContentType;

    private Long productCategoryId;

    private String productCategoryProductCategoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductSubCategoryName() {
        return productSubCategoryName;
    }

    public void setProductSubCategoryName(String productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryProductCategoryName() {
        return productCategoryProductCategoryName;
    }

    public void setProductCategoryProductCategoryName(String productCategoryProductCategoryName) {
        this.productCategoryProductCategoryName = productCategoryProductCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductSubCategoryDTO productSubCategoryDTO = (ProductSubCategoryDTO) o;
        if (productSubCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productSubCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductSubCategoryDTO{" +
            "id=" + getId() +
            ", productSubCategoryName='" + getProductSubCategoryName() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", productCategory=" + getProductCategoryId() +
            ", productCategory='" + getProductCategoryProductCategoryName() + "'" +
            "}";
    }
}
