package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductAttributeSet.
 */
@Entity
@Table(name = "product_attribute_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductAttributeSet extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("productAttributeSets")
    private ProductOptionSet productOptionSet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProductAttributeSet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductOptionSet getProductOptionSet() {
        return productOptionSet;
    }

    public ProductAttributeSet productOptionSet(ProductOptionSet productOptionSet) {
        this.productOptionSet = productOptionSet;
        return this;
    }

    public void setProductOptionSet(ProductOptionSet productOptionSet) {
        this.productOptionSet = productOptionSet;
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
        ProductAttributeSet productAttributeSet = (ProductAttributeSet) o;
        if (productAttributeSet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productAttributeSet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductAttributeSet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
