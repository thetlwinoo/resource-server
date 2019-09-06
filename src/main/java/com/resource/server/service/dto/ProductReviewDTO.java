package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductReview entity.
 */
public class ProductReviewDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String reviewerName;

    @NotNull
    private LocalDate reviewDate;

    @NotNull
    private String emailAddress;

    @NotNull
    private Integer rating;

    private String comments;


    private Long productId;

    private String productProductName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

        ProductReviewDTO productReviewDTO = (ProductReviewDTO) o;
        if (productReviewDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productReviewDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductReviewDTO{" +
            "id=" + getId() +
            ", reviewerName='" + getReviewerName() + "'" +
            ", reviewDate='" + getReviewDate() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", rating=" + getRating() +
            ", comments='" + getComments() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            "}";
    }
}
