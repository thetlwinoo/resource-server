package com.resource.server.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the LastestMerchantUploadedDocument entity.
 */
public class LastestMerchantUploadedDocumentDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] productCreateTemplate;

    private String productCreateTemplateContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getProductCreateTemplate() {
        return productCreateTemplate;
    }

    public void setProductCreateTemplate(byte[] productCreateTemplate) {
        this.productCreateTemplate = productCreateTemplate;
    }

    public String getProductCreateTemplateContentType() {
        return productCreateTemplateContentType;
    }

    public void setProductCreateTemplateContentType(String productCreateTemplateContentType) {
        this.productCreateTemplateContentType = productCreateTemplateContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LastestMerchantUploadedDocumentDTO lastestMerchantUploadedDocumentDTO = (LastestMerchantUploadedDocumentDTO) o;
        if (lastestMerchantUploadedDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lastestMerchantUploadedDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LastestMerchantUploadedDocumentDTO{" +
            "id=" + getId() +
            ", productCreateTemplate='" + getProductCreateTemplate() + "'" +
            "}";
    }
}
