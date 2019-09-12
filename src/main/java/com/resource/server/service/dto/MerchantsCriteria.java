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
 * Criteria class for the Merchants entity. This class is used in MerchantsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /merchants?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MerchantsCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter accountNumber;

    private StringFilter merchantName;

    private IntegerFilter creditRating;

    private BooleanFilter activeFlag;

    private StringFilter webServiceUrl;

    private StringFilter webSiteUrl;

    private LongFilter personId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StringFilter getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(StringFilter merchantName) {
        this.merchantName = merchantName;
    }

    public IntegerFilter getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(IntegerFilter creditRating) {
        this.creditRating = creditRating;
    }

    public BooleanFilter getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(BooleanFilter activeFlag) {
        this.activeFlag = activeFlag;
    }

    public StringFilter getWebServiceUrl() {
        return webServiceUrl;
    }

    public void setWebServiceUrl(StringFilter webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    public StringFilter getWebSiteUrl() {
        return webSiteUrl;
    }

    public void setWebSiteUrl(StringFilter webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

    public LongFilter getPersonId() {
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MerchantsCriteria that = (MerchantsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(merchantName, that.merchantName) &&
            Objects.equals(creditRating, that.creditRating) &&
            Objects.equals(activeFlag, that.activeFlag) &&
            Objects.equals(webServiceUrl, that.webServiceUrl) &&
            Objects.equals(webSiteUrl, that.webSiteUrl) &&
            Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        accountNumber,
        merchantName,
        creditRating,
        activeFlag,
        webServiceUrl,
        webSiteUrl,
        personId
        );
    }

    @Override
    public String toString() {
        return "MerchantsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (merchantName != null ? "merchantName=" + merchantName + ", " : "") +
                (creditRating != null ? "creditRating=" + creditRating + ", " : "") +
                (activeFlag != null ? "activeFlag=" + activeFlag + ", " : "") +
                (webServiceUrl != null ? "webServiceUrl=" + webServiceUrl + ", " : "") +
                (webSiteUrl != null ? "webSiteUrl=" + webSiteUrl + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
            "}";
    }

}
