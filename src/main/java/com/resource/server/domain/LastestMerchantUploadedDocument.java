package com.resource.server.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LastestMerchantUploadedDocument.
 */
@Entity
@Table(name = "lastest_merchant_uploaded_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LastestMerchantUploadedDocument extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "product_create_template")
    private byte[] productCreateTemplate;

    @Column(name = "product_create_template_content_type")
    private String productCreateTemplateContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getProductCreateTemplate() {
        return productCreateTemplate;
    }

    public LastestMerchantUploadedDocument productCreateTemplate(byte[] productCreateTemplate) {
        this.productCreateTemplate = productCreateTemplate;
        return this;
    }

    public void setProductCreateTemplate(byte[] productCreateTemplate) {
        this.productCreateTemplate = productCreateTemplate;
    }

    public String getProductCreateTemplateContentType() {
        return productCreateTemplateContentType;
    }

    public LastestMerchantUploadedDocument productCreateTemplateContentType(String productCreateTemplateContentType) {
        this.productCreateTemplateContentType = productCreateTemplateContentType;
        return this;
    }

    public void setProductCreateTemplateContentType(String productCreateTemplateContentType) {
        this.productCreateTemplateContentType = productCreateTemplateContentType;
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
        LastestMerchantUploadedDocument lastestMerchantUploadedDocument = (LastestMerchantUploadedDocument) o;
        if (lastestMerchantUploadedDocument.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lastestMerchantUploadedDocument.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LastestMerchantUploadedDocument{" +
            "id=" + getId() +
            ", productCreateTemplate='" + getProductCreateTemplate() + "'" +
            ", productCreateTemplateContentType='" + getProductCreateTemplateContentType() + "'" +
            "}";
    }
}
