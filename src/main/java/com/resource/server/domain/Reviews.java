package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Reviews.
 */
@Entity
@Table(name = "reviews")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reviews extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "reviewer_name")
    private String reviewerName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @Column(name = "over_all_seller_rating")
    private Integer overAllSellerRating;

    @Column(name = "over_all_seller_review")
    private String overAllSellerReview;

    @Column(name = "over_all_delivery_rating")
    private Integer overAllDeliveryRating;

    @Column(name = "over_all_delivery_review")
    private String overAllDeliveryReview;

    @Column(name = "review_as_anonymous")
    private Boolean reviewAsAnonymous;

    @Column(name = "completed_review")
    private Boolean completedReview;

    @OneToMany(mappedBy = "review")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReviewLines> reviewLineLists = new HashSet<>();
    @OneToOne(mappedBy = "review")
    @JsonIgnore
    private Orders order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public Reviews reviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
        return this;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Reviews emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public Reviews reviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
        return this;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Integer getOverAllSellerRating() {
        return overAllSellerRating;
    }

    public Reviews overAllSellerRating(Integer overAllSellerRating) {
        this.overAllSellerRating = overAllSellerRating;
        return this;
    }

    public void setOverAllSellerRating(Integer overAllSellerRating) {
        this.overAllSellerRating = overAllSellerRating;
    }

    public String getOverAllSellerReview() {
        return overAllSellerReview;
    }

    public Reviews overAllSellerReview(String overAllSellerReview) {
        this.overAllSellerReview = overAllSellerReview;
        return this;
    }

    public void setOverAllSellerReview(String overAllSellerReview) {
        this.overAllSellerReview = overAllSellerReview;
    }

    public Integer getOverAllDeliveryRating() {
        return overAllDeliveryRating;
    }

    public Reviews overAllDeliveryRating(Integer overAllDeliveryRating) {
        this.overAllDeliveryRating = overAllDeliveryRating;
        return this;
    }

    public void setOverAllDeliveryRating(Integer overAllDeliveryRating) {
        this.overAllDeliveryRating = overAllDeliveryRating;
    }

    public String getOverAllDeliveryReview() {
        return overAllDeliveryReview;
    }

    public Reviews overAllDeliveryReview(String overAllDeliveryReview) {
        this.overAllDeliveryReview = overAllDeliveryReview;
        return this;
    }

    public void setOverAllDeliveryReview(String overAllDeliveryReview) {
        this.overAllDeliveryReview = overAllDeliveryReview;
    }

    public Boolean isReviewAsAnonymous() {
        return reviewAsAnonymous;
    }

    public Reviews reviewAsAnonymous(Boolean reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
        return this;
    }

    public void setReviewAsAnonymous(Boolean reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
    }

    public Boolean isCompletedReview() {
        return completedReview;
    }

    public Reviews completedReview(Boolean completedReview) {
        this.completedReview = completedReview;
        return this;
    }

    public void setCompletedReview(Boolean completedReview) {
        this.completedReview = completedReview;
    }

    public Set<ReviewLines> getReviewLineLists() {
        return reviewLineLists;
    }

    public Reviews reviewLineLists(Set<ReviewLines> reviewLines) {
        this.reviewLineLists = reviewLines;
        return this;
    }

    public Reviews addReviewLineList(ReviewLines reviewLines) {
        this.reviewLineLists.add(reviewLines);
        reviewLines.setReview(this);
        return this;
    }

    public Reviews removeReviewLineList(ReviewLines reviewLines) {
        this.reviewLineLists.remove(reviewLines);
        reviewLines.setReview(null);
        return this;
    }

    public void setReviewLineLists(Set<ReviewLines> reviewLines) {
        this.reviewLineLists = reviewLines;
    }

    public Orders getOrder() {
        return order;
    }

    public Reviews order(Orders orders) {
        this.order = orders;
        return this;
    }

    public void setOrder(Orders orders) {
        this.order = orders;
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
        Reviews reviews = (Reviews) o;
        if (reviews.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reviews.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reviews{" +
            "id=" + getId() +
            ", reviewerName='" + getReviewerName() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", reviewDate='" + getReviewDate() + "'" +
            ", overAllSellerRating=" + getOverAllSellerRating() +
            ", overAllSellerReview='" + getOverAllSellerReview() + "'" +
            ", overAllDeliveryRating=" + getOverAllDeliveryRating() +
            ", overAllDeliveryReview='" + getOverAllDeliveryReview() + "'" +
            ", reviewAsAnonymous='" + isReviewAsAnonymous() + "'" +
            ", completedReview='" + isCompletedReview() + "'" +
            "}";
    }
}
