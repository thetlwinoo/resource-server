package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ProductBrand entity.
 */
public class ProductBrandDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String productBrandName;

    @Lob
    private byte[] photo;

    private String photoContentType;

    private Long merchantId;

    private String merchantMerchantName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantsId) {
        this.merchantId = merchantsId;
    }

    public String getMerchantMerchantName() {
        return merchantMerchantName;
    }

    public void setMerchantMerchantName(String merchantsMerchantName) {
        this.merchantMerchantName = merchantsMerchantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductBrandDTO productBrandDTO = (ProductBrandDTO) o;
        if (productBrandDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productBrandDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductBrandDTO{" +
            "id=" + getId() +
            ", productBrandName='" + getProductBrandName() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", merchant=" + getMerchantId() +
            ", merchant='" + getMerchantMerchantName() + "'" +
            "}";
    }
}
