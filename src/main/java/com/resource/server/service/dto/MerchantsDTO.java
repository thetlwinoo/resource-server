package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Merchants entity.
 */
public class MerchantsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountNumber;

    @NotNull
    private String merchantName;

    @NotNull
    private Integer creditRating;

    @NotNull
    private Boolean activeFlag;

    private String webServiceUrl;

    private String webSiteUrl;

    @Lob
    private byte[] avatar;

    private String avatarContentType;

    private Long personId;

    private String personFullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(Integer creditRating) {
        this.creditRating = creditRating;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getWebServiceUrl() {
        return webServiceUrl;
    }

    public void setWebServiceUrl(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    public String getWebSiteUrl() {
        return webSiteUrl;
    }

    public void setWebSiteUrl(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarContentType() {
        return avatarContentType;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long peopleId) {
        this.personId = peopleId;
    }

    public String getPersonFullName() {
        return personFullName;
    }

    public void setPersonFullName(String peopleFullName) {
        this.personFullName = peopleFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MerchantsDTO merchantsDTO = (MerchantsDTO) o;
        if (merchantsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), merchantsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MerchantsDTO{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", merchantName='" + getMerchantName() + "'" +
            ", creditRating=" + getCreditRating() +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", webServiceUrl='" + getWebServiceUrl() + "'" +
            ", webSiteUrl='" + getWebSiteUrl() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", person=" + getPersonId() +
            ", person='" + getPersonFullName() + "'" +
            "}";
    }
}
