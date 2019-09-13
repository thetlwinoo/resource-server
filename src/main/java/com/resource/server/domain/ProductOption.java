package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductOption.
 */
@Entity
@Table(name = "product_option")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductOption extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @ManyToOne
    @JsonIgnoreProperties("productOptions")
    private ProductOptionSet productOptionSet;

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

    public ProductOption value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProductOptionSet getProductOptionSet() {
        return productOptionSet;
    }

    public ProductOption productOptionSet(ProductOptionSet productOptionSet) {
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
        ProductOption productOption = (ProductOption) o;
        if (productOption.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productOption.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductOption{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
