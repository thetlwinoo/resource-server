package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SupplierImportedDocument.
 */
@Entity
@Table(name = "supplier_imported_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SupplierImportedDocument extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "imported_template")
    private byte[] importedTemplate;

    @Column(name = "imported_template_content_type")
    private String importedTemplateContentType;

    @Lob
    @Column(name = "imported_failed_template")
    private byte[] importedFailedTemplate;

    @Column(name = "imported_failed_template_content_type")
    private String importedFailedTemplateContentType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnoreProperties("importDocumentLists")
    private UploadTransactions uploadTransaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImportedTemplate() {
        return importedTemplate;
    }

    public SupplierImportedDocument importedTemplate(byte[] importedTemplate) {
        this.importedTemplate = importedTemplate;
        return this;
    }

    public void setImportedTemplate(byte[] importedTemplate) {
        this.importedTemplate = importedTemplate;
    }

    public String getImportedTemplateContentType() {
        return importedTemplateContentType;
    }

    public SupplierImportedDocument importedTemplateContentType(String importedTemplateContentType) {
        this.importedTemplateContentType = importedTemplateContentType;
        return this;
    }

    public void setImportedTemplateContentType(String importedTemplateContentType) {
        this.importedTemplateContentType = importedTemplateContentType;
    }

    public byte[] getImportedFailedTemplate() {
        return importedFailedTemplate;
    }

    public SupplierImportedDocument importedFailedTemplate(byte[] importedFailedTemplate) {
        this.importedFailedTemplate = importedFailedTemplate;
        return this;
    }

    public void setImportedFailedTemplate(byte[] importedFailedTemplate) {
        this.importedFailedTemplate = importedFailedTemplate;
    }

    public String getImportedFailedTemplateContentType() {
        return importedFailedTemplateContentType;
    }

    public SupplierImportedDocument importedFailedTemplateContentType(String importedFailedTemplateContentType) {
        this.importedFailedTemplateContentType = importedFailedTemplateContentType;
        return this;
    }

    public void setImportedFailedTemplateContentType(String importedFailedTemplateContentType) {
        this.importedFailedTemplateContentType = importedFailedTemplateContentType;
    }

    public UploadTransactions getUploadTransaction() {
        return uploadTransaction;
    }

    public SupplierImportedDocument uploadTransaction(UploadTransactions uploadTransactions) {
        this.uploadTransaction = uploadTransactions;
        return this;
    }

    public void setUploadTransaction(UploadTransactions uploadTransactions) {
        this.uploadTransaction = uploadTransactions;
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
        SupplierImportedDocument supplierImportedDocument = (SupplierImportedDocument) o;
        if (supplierImportedDocument.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplierImportedDocument.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplierImportedDocument{" +
            "id=" + getId() +
            ", importedTemplate='" + getImportedTemplate() + "'" +
            ", importedTemplateContentType='" + getImportedTemplateContentType() + "'" +
            ", importedFailedTemplate='" + getImportedFailedTemplate() + "'" +
            ", importedFailedTemplateContentType='" + getImportedFailedTemplateContentType() + "'" +
            "}";
    }
}
