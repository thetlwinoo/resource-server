package com.resource.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SpecialDeals.
 */
@Entity
@Table(name = "special_deals")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SpecialDeals extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "deal_description", nullable = false)
    private String dealDescription;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "discount_percentage")
    private Float discountPercentage;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @OneToMany(mappedBy = "specialDeals")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShoppingCarts> cartDiscounts = new HashSet<>();
    @OneToMany(mappedBy = "specialDeals")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Orders> orderDiscounts = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("specialDeals")
    private BuyingGroups buyingGroup;

    @ManyToOne
    @JsonIgnoreProperties("specialDeals")
    private CustomerCategories customerCategory;

    @ManyToOne
    @JsonIgnoreProperties("specialDeals")
    private Customers customer;

    @ManyToOne
    @JsonIgnoreProperties("specialDeals")
    private StockGroups stockGroup;

    @ManyToOne
    @JsonIgnoreProperties("specialDeals")
    private Products product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public SpecialDeals dealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
        return this;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public SpecialDeals startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SpecialDeals endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public SpecialDeals discountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Float getDiscountPercentage() {
        return discountPercentage;
    }

    public SpecialDeals discountPercentage(Float discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }

    public void setDiscountPercentage(Float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public SpecialDeals discountCode(String discountCode) {
        this.discountCode = discountCode;
        return this;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public SpecialDeals unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Set<ShoppingCarts> getCartDiscounts() {
        return cartDiscounts;
    }

    public SpecialDeals cartDiscounts(Set<ShoppingCarts> shoppingCarts) {
        this.cartDiscounts = shoppingCarts;
        return this;
    }

    public SpecialDeals addCartDiscount(ShoppingCarts shoppingCarts) {
        this.cartDiscounts.add(shoppingCarts);
        shoppingCarts.setSpecialDeals(this);
        return this;
    }

    public SpecialDeals removeCartDiscount(ShoppingCarts shoppingCarts) {
        this.cartDiscounts.remove(shoppingCarts);
        shoppingCarts.setSpecialDeals(null);
        return this;
    }

    public void setCartDiscounts(Set<ShoppingCarts> shoppingCarts) {
        this.cartDiscounts = shoppingCarts;
    }

    public Set<Orders> getOrderDiscounts() {
        return orderDiscounts;
    }

    public SpecialDeals orderDiscounts(Set<Orders> orders) {
        this.orderDiscounts = orders;
        return this;
    }

    public SpecialDeals addOrderDiscount(Orders orders) {
        this.orderDiscounts.add(orders);
        orders.setSpecialDeals(this);
        return this;
    }

    public SpecialDeals removeOrderDiscount(Orders orders) {
        this.orderDiscounts.remove(orders);
        orders.setSpecialDeals(null);
        return this;
    }

    public void setOrderDiscounts(Set<Orders> orders) {
        this.orderDiscounts = orders;
    }

    public BuyingGroups getBuyingGroup() {
        return buyingGroup;
    }

    public SpecialDeals buyingGroup(BuyingGroups buyingGroups) {
        this.buyingGroup = buyingGroups;
        return this;
    }

    public void setBuyingGroup(BuyingGroups buyingGroups) {
        this.buyingGroup = buyingGroups;
    }

    public CustomerCategories getCustomerCategory() {
        return customerCategory;
    }

    public SpecialDeals customerCategory(CustomerCategories customerCategories) {
        this.customerCategory = customerCategories;
        return this;
    }

    public void setCustomerCategory(CustomerCategories customerCategories) {
        this.customerCategory = customerCategories;
    }

    public Customers getCustomer() {
        return customer;
    }

    public SpecialDeals customer(Customers customers) {
        this.customer = customers;
        return this;
    }

    public void setCustomer(Customers customers) {
        this.customer = customers;
    }

    public StockGroups getStockGroup() {
        return stockGroup;
    }

    public SpecialDeals stockGroup(StockGroups stockGroups) {
        this.stockGroup = stockGroups;
        return this;
    }

    public void setStockGroup(StockGroups stockGroups) {
        this.stockGroup = stockGroups;
    }

    public Products getProduct() {
        return product;
    }

    public SpecialDeals product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
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
        SpecialDeals specialDeals = (SpecialDeals) o;
        if (specialDeals.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialDeals.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpecialDeals{" +
            "id=" + getId() +
            ", dealDescription='" + getDealDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", discountAmount=" + getDiscountAmount() +
            ", discountPercentage=" + getDiscountPercentage() +
            ", discountCode='" + getDiscountCode() + "'" +
            ", unitPrice=" + getUnitPrice() +
            "}";
    }
}
