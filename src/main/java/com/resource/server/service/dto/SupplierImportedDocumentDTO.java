package com.resource.server.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the SupplierImportedDocument entity.
 */
public class SupplierImportedDocumentDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] importedTemplate;

    private String importedTemplateContentType;
    @Lob
    private byte[] importedFailedTemplate;

    private String importedFailedTemplateContentType;

    private Long uploadTransactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImportedTemplate() {
        return importedTemplate;
    }

    public void setImportedTemplate(byte[] importedTemplate) {
        this.importedTemplate = importedTemplate;
    }

    public String getImportedTemplateContentType() {
        return importedTemplateContentType;
    }

    public void setImportedTemplateContentType(String importedTemplateContentType) {
        this.importedTemplateContentType = importedTemplateContentType;
    }

    public byte[] getImportedFailedTemplate() {
        return importedFailedTemplate;
    }

    public void setImportedFailedTemplate(byte[] importedFailedTemplate) {
        this.importedFailedTemplate = importedFailedTemplate;
    }

    public String getImportedFailedTemplateContentType() {
        return importedFailedTemplateContentType;
    }

    public void setImportedFailedTemplateContentType(String importedFailedTemplateContentType) {
        this.importedFailedTemplateContentType = importedFailedTemplateContentType;
    }

    public Long getUploadTransactionId() {
        return uploadTransactionId;
    }

    public void setUploadTransactionId(Long uploadTransactionsId) {
        this.uploadTransactionId = uploadTransactionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplierImportedDocumentDTO supplierImportedDocumentDTO = (SupplierImportedDocumentDTO) o;
        if (supplierImportedDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplierImportedDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplierImportedDocumentDTO{" +
            "id=" + getId() +
            ", importedTemplate='" + getImportedTemplate() + "'" +
            ", importedFailedTemplate='" + getImportedFailedTemplate() + "'" +
            ", uploadTransaction=" + getUploadTransactionId() +
            "}";
    }
}
