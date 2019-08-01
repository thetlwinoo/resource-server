package com.resource.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StockItemStockGroups entity.
 */
public class StockItemStockGroupsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;


    private Long stockGroupId;

    private String stockGroupStockGroupName;

    private Long productId;

    private String productProductName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockGroupId() {
        return stockGroupId;
    }

    public void setStockGroupId(Long stockGroupsId) {
        this.stockGroupId = stockGroupsId;
    }

    public String getStockGroupStockGroupName() {
        return stockGroupStockGroupName;
    }

    public void setStockGroupStockGroupName(String stockGroupsStockGroupName) {
        this.stockGroupStockGroupName = stockGroupsStockGroupName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItemStockGroupsDTO stockItemStockGroupsDTO = (StockItemStockGroupsDTO) o;
        if (stockItemStockGroupsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItemStockGroupsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItemStockGroupsDTO{" +
            "id=" + getId() +
            ", stockGroup=" + getStockGroupId() +
            ", stockGroup='" + getStockGroupStockGroupName() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            "}";
    }
}
