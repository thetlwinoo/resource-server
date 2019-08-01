package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductPhoto.
 */
@Entity
@Table(name = "product_photo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductPhoto extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "thumbnail_photo")
    private String thumbnailPhoto;

    @Column(name = "original_photo")
    private String originalPhoto;

    @Column(name = "banner_tall_photo")
    private String bannerTallPhoto;

    @Column(name = "banner_wide_photo")
    private String bannerWidePhoto;

    @Column(name = "circle_photo")
    private String circlePhoto;

    @Column(name = "sharpened_photo")
    private String sharpenedPhoto;

    @Column(name = "square_photo")
    private String squarePhoto;

    @Column(name = "watermark_photo")
    private String watermarkPhoto;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "default_ind")
    private Boolean defaultInd;

    @Size(min = 1, max = 1024)
    @Column(name = "delete_token", length = 1024)
    private String deleteToken;

    @ManyToOne
    @JsonIgnoreProperties("productPhotos")
    private Products product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public ProductPhoto thumbnailPhoto(String thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
        return this;
    }

    public void setThumbnailPhoto(String thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
    }

    public String getOriginalPhoto() {
        return originalPhoto;
    }

    public ProductPhoto originalPhoto(String originalPhoto) {
        this.originalPhoto = originalPhoto;
        return this;
    }

    public void setOriginalPhoto(String originalPhoto) {
        this.originalPhoto = originalPhoto;
    }

    public String getBannerTallPhoto() {
        return bannerTallPhoto;
    }

    public ProductPhoto bannerTallPhoto(String bannerTallPhoto) {
        this.bannerTallPhoto = bannerTallPhoto;
        return this;
    }

    public void setBannerTallPhoto(String bannerTallPhoto) {
        this.bannerTallPhoto = bannerTallPhoto;
    }

    public String getBannerWidePhoto() {
        return bannerWidePhoto;
    }

    public ProductPhoto bannerWidePhoto(String bannerWidePhoto) {
        this.bannerWidePhoto = bannerWidePhoto;
        return this;
    }

    public void setBannerWidePhoto(String bannerWidePhoto) {
        this.bannerWidePhoto = bannerWidePhoto;
    }

    public String getCirclePhoto() {
        return circlePhoto;
    }

    public ProductPhoto circlePhoto(String circlePhoto) {
        this.circlePhoto = circlePhoto;
        return this;
    }

    public void setCirclePhoto(String circlePhoto) {
        this.circlePhoto = circlePhoto;
    }

    public String getSharpenedPhoto() {
        return sharpenedPhoto;
    }

    public ProductPhoto sharpenedPhoto(String sharpenedPhoto) {
        this.sharpenedPhoto = sharpenedPhoto;
        return this;
    }

    public void setSharpenedPhoto(String sharpenedPhoto) {
        this.sharpenedPhoto = sharpenedPhoto;
    }

    public String getSquarePhoto() {
        return squarePhoto;
    }

    public ProductPhoto squarePhoto(String squarePhoto) {
        this.squarePhoto = squarePhoto;
        return this;
    }

    public void setSquarePhoto(String squarePhoto) {
        this.squarePhoto = squarePhoto;
    }

    public String getWatermarkPhoto() {
        return watermarkPhoto;
    }

    public ProductPhoto watermarkPhoto(String watermarkPhoto) {
        this.watermarkPhoto = watermarkPhoto;
        return this;
    }

    public void setWatermarkPhoto(String watermarkPhoto) {
        this.watermarkPhoto = watermarkPhoto;
    }

    public Integer getPriority() {
        return priority;
    }

    public ProductPhoto priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public ProductPhoto defaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
        return this;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public String getDeleteToken() {
        return deleteToken;
    }

    public ProductPhoto deleteToken(String deleteToken) {
        this.deleteToken = deleteToken;
        return this;
    }

    public void setDeleteToken(String deleteToken) {
        this.deleteToken = deleteToken;
    }

    public Products getProduct() {
        return product;
    }

    public ProductPhoto product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPhoto productPhoto = (ProductPhoto) o;
        if (productPhoto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productPhoto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductPhoto{" +
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
            "}";
    }
}
