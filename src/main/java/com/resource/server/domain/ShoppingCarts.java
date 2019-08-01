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
 * A ShoppingCarts.
 */
@Entity
@Table(name = "shopping_carts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShoppingCarts extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "total_cargo_price")
    private Float totalCargoPrice;

    @ManyToOne
    @JsonIgnoreProperties("cartDiscounts")
    private SpecialDeals specialDeals;

    @OneToOne
    @JoinColumn(unique = true)
    private People cartUser;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShoppingCartItems> cartItemLists = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("shoppingCarts")
    private Customers customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public ShoppingCarts totalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Float getTotalCargoPrice() {
        return totalCargoPrice;
    }

    public ShoppingCarts totalCargoPrice(Float totalCargoPrice) {
        this.totalCargoPrice = totalCargoPrice;
        return this;
    }

    public void setTotalCargoPrice(Float totalCargoPrice) {
        this.totalCargoPrice = totalCargoPrice;
    }

    public SpecialDeals getSpecialDeals() {
        return specialDeals;
    }

    public ShoppingCarts specialDeals(SpecialDeals specialDeals) {
        this.specialDeals = specialDeals;
        return this;
    }

    public void setSpecialDeals(SpecialDeals specialDeals) {
        this.specialDeals = specialDeals;
    }

    public People getCartUser() {
        return cartUser;
    }

    public ShoppingCarts cartUser(People people) {
        this.cartUser = people;
        return this;
    }

    public void setCartUser(People people) {
        this.cartUser = people;
    }

    public Set<ShoppingCartItems> getCartItemLists() {
        return cartItemLists;
    }

    public ShoppingCarts cartItemLists(Set<ShoppingCartItems> shoppingCartItems) {
        this.cartItemLists = shoppingCartItems;
        return this;
    }

    public ShoppingCarts addCartItemList(ShoppingCartItems shoppingCartItems) {
        this.cartItemLists.add(shoppingCartItems);
        shoppingCartItems.setCart(this);
        return this;
    }

    public ShoppingCarts removeCartItemList(ShoppingCartItems shoppingCartItems) {
        this.cartItemLists.remove(shoppingCartItems);
        shoppingCartItems.setCart(null);
        return this;
    }

    public void setCartItemLists(Set<ShoppingCartItems> shoppingCartItems) {
        this.cartItemLists = shoppingCartItems;
    }

    public Customers getCustomer() {
        return customer;
    }

    public ShoppingCarts customer(Customers customers) {
        this.customer = customers;
        return this;
    }

    public void setCustomer(Customers customers) {
        this.customer = customers;
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
        ShoppingCarts shoppingCarts = (ShoppingCarts) o;
        if (shoppingCarts.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingCarts.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingCarts{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", totalCargoPrice=" + getTotalCargoPrice() +
            "}";
    }
}
