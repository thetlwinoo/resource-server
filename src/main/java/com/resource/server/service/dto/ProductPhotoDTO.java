package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductPhoto entity.
 */
public class ProductPhotoDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String thumbnailPhoto;

    private String originalPhoto;

    private String bannerTallPhoto;

    private String bannerWidePhoto;

    private String circlePhoto;

    private String sharpenedPhoto;

    private String squarePhoto;

    private String watermarkPhoto;

    private Integer priority;

    private Boolean defaultInd;

    @Size(min = 1, max = 1024)
    private String deleteToken;


    private Long productId;

    private String productProductName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public void setThumbnailPhoto(String thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
    }

    public String getOriginalPhoto() {
        return originalPhoto;
    }

    public void setOriginalPhoto(String originalPhoto) {
        this.originalPhoto = originalPhoto;
    }

    public String getBannerTallPhoto() {
        return bannerTallPhoto;
    }

    public void setBannerTallPhoto(String bannerTallPhoto) {
        this.bannerTallPhoto = bannerTallPhoto;
    }

    public String getBannerWidePhoto() {
        return bannerWidePhoto;
    }

    public void setBannerWidePhoto(String bannerWidePhoto) {
        this.bannerWidePhoto = bannerWidePhoto;
    }

    public String getCirclePhoto() {
        return circlePhoto;
    }

    public void setCirclePhoto(String circlePhoto) {
        this.circlePhoto = circlePhoto;
    }

    public String getSharpenedPhoto() {
        return sharpenedPhoto;
    }

    public void setSharpenedPhoto(String sharpenedPhoto) {
        this.sharpenedPhoto = sharpenedPhoto;
    }

    public String getSquarePhoto() {
        return squarePhoto;
    }

    public void setSquarePhoto(String squarePhoto) {
        this.squarePhoto = squarePhoto;
    }

    public String getWatermarkPhoto() {
        return watermarkPhoto;
    }

    public void setWatermarkPhoto(String watermarkPhoto) {
        this.watermarkPhoto = watermarkPhoto;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public String getDeleteToken() {
        return deleteToken;
    }

    public void setDeleteToken(String deleteToken) {
        this.deleteToken = deleteToken;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    public String getProductProductName() {
        return productProductName;
    }

    public void setProductProductName(String productsProductName) {
        this.productProductName = productsProductName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductPhotoDTO productPhotoDTO = (ProductPhotoDTO) o;
        if (productPhotoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productPhotoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductPhotoDTO{" +
            "id=" + getId() +
            ", thumbnailPhoto='" + getThumbnailPhoto() + "'" +
            ", originalPhoto='" + getOriginalPhoto() + "'" +
            ", bannerTallPhoto='" + getBannerTallPhoto() + "'" +
            ", bannerWidePhoto='" + getBannerWidePhoto() + "'" +
            ", circlePhoto='" + getCirclePhoto() + "'" +
            ", sharpenedPhoto='" + getSharpenedPhoto() + "'" +
            ", squarePhoto='" + getSquarePhoto() + "'" +
            ", watermarkPhoto='" + getWatermarkPhoto() + "'" +
            ", priority=" + getPriority() +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", deleteToken='" + getDeleteToken() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            "}";
    }
}
