package com.resource.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Photos entity. This class is used in PhotosResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /photos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PhotosCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter thumbnailPhoto;

    private StringFilter originalPhoto;

    private StringFilter bannerTallPhoto;

    private StringFilter bannerWidePhoto;

    private StringFilter circlePhoto;

    private StringFilter sharpenedPhoto;

    private StringFilter squarePhoto;

    private StringFilter watermarkPhoto;

    private IntegerFilter priority;

    private BooleanFilter defaultInd;

    private StringFilter deleteToken;

    private LongFilter stockItemId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public void setThumbnailPhoto(StringFilter thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
    }

    public StringFilter getOriginalPhoto() {
        return originalPhoto;
    }

    public void setOriginalPhoto(StringFilter originalPhoto) {
        this.originalPhoto = originalPhoto;
    }

    public StringFilter getBannerTallPhoto() {
        return bannerTallPhoto;
    }

    public void setBannerTallPhoto(StringFilter bannerTallPhoto) {
        this.bannerTallPhoto = bannerTallPhoto;
    }

    public StringFilter getBannerWidePhoto() {
        return bannerWidePhoto;
    }

    public void setBannerWidePhoto(StringFilter bannerWidePhoto) {
        this.bannerWidePhoto = bannerWidePhoto;
    }

    public StringFilter getCirclePhoto() {
        return circlePhoto;
    }

    public void setCirclePhoto(StringFilter circlePhoto) {
        this.circlePhoto = circlePhoto;
    }

    public StringFilter getSharpenedPhoto() {
        return sharpenedPhoto;
    }

    public void setSharpenedPhoto(StringFilter sharpenedPhoto) {
        this.sharpenedPhoto = sharpenedPhoto;
    }

    public StringFilter getSquarePhoto() {
        return squarePhoto;
    }

    public void setSquarePhoto(StringFilter squarePhoto) {
        this.squarePhoto = squarePhoto;
    }

    public StringFilter getWatermarkPhoto() {
        return watermarkPhoto;
    }

    public void setWatermarkPhoto(StringFilter watermarkPhoto) {
        this.watermarkPhoto = watermarkPhoto;
    }

    public IntegerFilter getPriority() {
        return priority;
    }

    public void setPriority(IntegerFilter priority) {
        this.priority = priority;
    }

    public BooleanFilter getDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(BooleanFilter defaultInd) {
        this.defaultInd = defaultInd;
    }

    public StringFilter getDeleteToken() {
        return deleteToken;
    }

    public void setDeleteToken(StringFilter deleteToken) {
        this.deleteToken = deleteToken;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PhotosCriteria that = (PhotosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(thumbnailPhoto, that.thumbnailPhoto) &&
            Objects.equals(originalPhoto, that.originalPhoto) &&
            Objects.equals(bannerTallPhoto, that.bannerTallPhoto) &&
            Objects.equals(bannerWidePhoto, that.bannerWidePhoto) &&
            Objects.equals(circlePhoto, that.circlePhoto) &&
            Objects.equals(sharpenedPhoto, that.sharpenedPhoto) &&
            Objects.equals(squarePhoto, that.squarePhoto) &&
            Objects.equals(watermarkPhoto, that.watermarkPhoto) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(defaultInd, that.defaultInd) &&
            Objects.equals(deleteToken, that.deleteToken) &&
            Objects.equals(stockItemId, that.stockItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        thumbnailPhoto,
        originalPhoto,
        bannerTallPhoto,
        bannerWidePhoto,
        circlePhoto,
        sharpenedPhoto,
        squarePhoto,
        watermarkPhoto,
        priority,
        defaultInd,
        deleteToken,
        stockItemId
        );
    }

    @Override
    public String toString() {
        return "PhotosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (thumbnailPhoto != null ? "thumbnailPhoto=" + thumbnailPhoto + ", " : "") +
                (originalPhoto != null ? "originalPhoto=" + originalPhoto + ", " : "") +
                (bannerTallPhoto != null ? "bannerTallPhoto=" + bannerTallPhoto + ", " : "") +
                (bannerWidePhoto != null ? "bannerWidePhoto=" + bannerWidePhoto + ", " : "") +
                (circlePhoto != null ? "circlePhoto=" + circlePhoto + ", " : "") +
                (sharpenedPhoto != null ? "sharpenedPhoto=" + sharpenedPhoto + ", " : "") +
                (squarePhoto != null ? "squarePhoto=" + squarePhoto + ", " : "") +
                (watermarkPhoto != null ? "watermarkPhoto=" + watermarkPhoto + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (defaultInd != null ? "defaultInd=" + defaultInd + ", " : "") +
                (deleteToken != null ? "deleteToken=" + deleteToken + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
            "}";
    }

}
