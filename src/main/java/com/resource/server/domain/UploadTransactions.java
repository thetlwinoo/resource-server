package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UploadTransactions.
 */
@Entity
@Table(name = "upload_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UploadTransactions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "template_url")
    private String templateUrl;

    @Column(name = "status")
    private Integer status;

    @Column(name = "generated_code")
    private String generatedCode;

    @OneToMany(mappedBy = "uploadTransaction",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SupplierImportedDocument> importDocumentLists = new HashSet<>();
    @OneToMany(mappedBy = "uploadTransaction",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StockItemTemp> stockItemTempLists = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("uploadTransactions")
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties("uploadTransactions")
    private UploadActionTypes actionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public UploadTransactions fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public UploadTransactions templateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
        return this;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public UploadTransactions status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public UploadTransactions generatedCode(String generatedCode) {
        this.generatedCode = generatedCode;
        return this;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    public Set<SupplierImportedDocument> getImportDocumentLists() {
        return importDocumentLists;
    }

    public UploadTransactions importDocumentLists(Set<SupplierImportedDocument> supplierImportedDocuments) {
        this.importDocumentLists = supplierImportedDocuments;
        return this;
    }

    public UploadTransactions addImportDocumentList(SupplierImportedDocument supplierImportedDocument) {
        this.importDocumentLists.add(supplierImportedDocument);
        supplierImportedDocument.setUploadTransaction(this);
        return this;
    }

    public UploadTransactions removeImportDocumentList(SupplierImportedDocument supplierImportedDocument) {
        this.importDocumentLists.remove(supplierImportedDocument);
        supplierImportedDocument.setUploadTransaction(null);
        return this;
    }

    public void setImportDocumentLists(Set<SupplierImportedDocument> supplierImportedDocuments) {
        this.importDocumentLists = supplierImportedDocuments;
    }

    public Set<StockItemTemp> getStockItemTempLists() {
        return stockItemTempLists;
    }

    public UploadTransactions stockItemTempLists(Set<StockItemTemp> stockItemTemps) {
        this.stockItemTempLists = stockItemTemps;
        return this;
    }

    public UploadTransactions addStockItemTempList(StockItemTemp stockItemTemp) {
        this.stockItemTempLists.add(stockItemTemp);
        stockItemTemp.setUploadTransaction(this);
        return this;
    }

    public UploadTransactions removeStockItemTempList(StockItemTemp stockItemTemp) {
        this.stockItemTempLists.remove(stockItemTemp);
        stockItemTemp.setUploadTransaction(null);
        return this;
    }

    public void setStockItemTempLists(Set<StockItemTemp> stockItemTemps) {
        this.stockItemTempLists = stockItemTemps;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public UploadTransactions supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public UploadActionTypes getActionType() {
        return actionType;
    }

    public UploadTransactions actionType(UploadActionTypes uploadActionTypes) {
        this.actionType = uploadActionTypes;
        return this;
    }

    public void setActionType(UploadActionTypes uploadActionTypes) {
        this.actionType = uploadActionTypes;
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
        UploadTransactions uploadTransactions = (UploadTransactions) o;
        if (uploadTransactions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uploadTransactions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UploadTransactions{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", templateUrl='" + getTemplateUrl() + "'" +
            ", status=" + getStatus() +
            ", generatedCode='" + getGeneratedCode() + "'" +
            "}";
    }
}
