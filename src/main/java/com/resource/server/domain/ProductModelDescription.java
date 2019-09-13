package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.resource.server.domain.enumeration.Language;

/**
 * A ProductModelDescription.
 */
@Entity
@Table(name = "product_model_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductModelDescription extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @ManyToOne
    @JsonIgnoreProperties("descriptions")
    private ProductModel productModel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ProductModelDescription description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLanguage() {
        return language;
    }

    public ProductModelDescription language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public ProductModelDescription productModel(ProductModel productModel) {
        this.productModel = productModel;
        return this;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
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
        ProductModelDescription productModelDescription = (ProductModelDescription) o;
        if (productModelDescription.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productModelDescription.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductModelDescription{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
