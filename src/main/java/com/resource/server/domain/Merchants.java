package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Merchants.
 */
@Entity
@Table(name = "merchants")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Merchants extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @NotNull
    @Column(name = "merchant_name", nullable = false)
    private String merchantName;

    @NotNull
    @Column(name = "credit_rating", nullable = false)
    private Integer creditRating;

    @NotNull
    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;

    @Column(name = "web_service_url")
    private String webServiceUrl;

    @Column(name = "web_site_url")
    private String webSiteUrl;

    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    @Column(name = "avatar_content_type")
    private String avatarContentType;

    @ManyToOne
    @JsonIgnoreProperties("merchants")
    private People person;

    @OneToOne(mappedBy = "merchant")
    @JsonIgnore
    private Products product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Merchants accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public Merchants merchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getCreditRating() {
        return creditRating;
    }

    public Merchants creditRating(Integer creditRating) {
        this.creditRating = creditRating;
        return this;
    }

    public void setCreditRating(Integer creditRating) {
        this.creditRating = creditRating;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public Merchants activeFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
        return this;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getWebServiceUrl() {
        return webServiceUrl;
    }

    public Merchants webServiceUrl(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
        return this;
    }

    public void setWebServiceUrl(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    public String getWebSiteUrl() {
        return webSiteUrl;
    }

    public Merchants webSiteUrl(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
        return this;
    }

    public void setWebSiteUrl(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public Merchants avatar(byte[] avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarContentType() {
        return avatarContentType;
    }

    public Merchants avatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
        return this;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }

    public People getPerson() {
        return person;
    }

    public Merchants person(People people) {
        this.person = people;
        return this;
    }

    public void setPerson(People people) {
        this.person = people;
    }

    public Products getProduct() {
        return product;
    }

    public Merchants product(Products products) {
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
        Merchants merchants = (Merchants) o;
        if (merchants.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), merchants.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Merchants{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", merchantName='" + getMerchantName() + "'" +
            ", creditRating=" + getCreditRating() +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", webServiceUrl='" + getWebServiceUrl() + "'" +
            ", webSiteUrl='" + getWebSiteUrl() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", avatarContentType='" + getAvatarContentType() + "'" +
            "}";
    }
}
