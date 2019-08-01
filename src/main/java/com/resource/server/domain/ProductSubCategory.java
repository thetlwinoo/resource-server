package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductSubCategory.
 */
@Entity
@Table(name = "product_sub_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSubCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "product_sub_category_name", nullable = false)
    private String productSubCategoryName;

    @ManyToOne
    @JsonIgnoreProperties("productSubCategories")
    private ProductCategory productCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductSubCategoryName() {
        return productSubCategoryName;
    }

    public ProductSubCategory productSubCategoryName(String productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
        return this;
    }

    public void setProductSubCategoryName(String productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public ProductSubCategory productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
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
        ProductSubCategory productSubCategory = (ProductSubCategory) o;
        if (productSubCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productSubCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductSubCategory{" +
            "id=" + getId() +
            ", productSubCategoryName='" + getProductSubCategoryName() + "'" +
            "}";
    }
}
