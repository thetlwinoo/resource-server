package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductInventory entity.
 */
public class ProductInventoryDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String shelf;

    @NotNull
    private Integer bin;

    @NotNull
    private Integer quantity;


    private Long productId;

    private String productProductName;

    private Long locationId;

    private String locationLocationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public Integer getBin() {
        return bin;
    }

    public void setBin(Integer bin) {
        this.bin = bin;
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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationsId) {
        this.locationId = locationsId;
    }

    public String getLocationLocationName() {
        return locationLocationName;
    }

    public void setLocationLocationName(String locationsLocationName) {
        this.locationLocationName = locationsLocationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductInventoryDTO productInventoryDTO = (ProductInventoryDTO) o;
        if (productInventoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productInventoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductInventoryDTO{" +
            "id=" + getId() +
            ", shelf='" + getShelf() + "'" +
            ", bin=" + getBin() +
            ", quantity=" + getQuantity() +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            ", location=" + getLocationId() +
            ", location='" + getLocationLocationName() + "'" +
            "}";
    }
}
