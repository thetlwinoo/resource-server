package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductAttribute.
 */
@Entity
@Table(name = "product_attribute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductAttribute extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @ManyToOne
    @JsonIgnoreProperties("productAttributes")
    private ProductAttributeSet productAttributeSet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public ProductAttribute value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProductAttributeSet getProductAttributeSet() {
        return productAttributeSet;
    }

    public ProductAttribute productAttributeSet(ProductAttributeSet productAttributeSet) {
        this.productAttributeSet = productAttributeSet;
        return this;
    }

    public void setProductAttributeSet(ProductAttributeSet productAttributeSet) {
        this.productAttributeSet = productAttributeSet;
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
        ProductAttribute productAttribute = (ProductAttribute) o;
        if (productAttribute.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productAttribute.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductAttribute{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
