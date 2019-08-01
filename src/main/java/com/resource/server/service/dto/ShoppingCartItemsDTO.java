package com.resource.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ShoppingCartItems entity.
 */
public class ShoppingCartItemsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Integer quantity;


    private Long productId;

    private String productProductName;

    private Long cartId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    public String getProductProductName() {
        return productProductName;
    }

    public void setProductProductName(String productsProductName) {
        this.productProductName = productsProductName;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long shoppingCartsId) {
        this.cartId = shoppingCartsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingCartItemsDTO shoppingCartItemsDTO = (ShoppingCartItemsDTO) o;
        if (shoppingCartItemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingCartItemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingCartItemsDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            ", cart=" + getCartId() +
            "}";
    }
}
