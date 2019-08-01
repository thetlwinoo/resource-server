package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductDocument.
 */
@Entity
@Table(name = "product_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductDocument extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Column(name = "document_node", nullable = false)
    private String documentNode;

    @ManyToOne
    @JsonIgnoreProperties("productDocuments")
    private Products product;

    @ManyToOne
    @JsonIgnoreProperties("productDocuments")
    private Culture culture;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNode() {
        return documentNode;
    }

    public ProductDocument documentNode(String documentNode) {
        this.documentNode = documentNode;
        return this;
    }

    public void setDocumentNode(String documentNode) {
        this.documentNode = documentNode;
    }

    public Products getProduct() {
        return product;
    }

    public ProductDocument product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }

    public Culture getCulture() {
        return culture;
    }

    public ProductDocument culture(Culture culture) {
        this.culture = culture;
        return this;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
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
        ProductDocument productDocument = (ProductDocument) o;
        if (productDocument.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDocument.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDocument{" +
            "id=" + getId() +
            ", documentNode='" + getDocumentNode() + "'" +
            "}";
    }
}
