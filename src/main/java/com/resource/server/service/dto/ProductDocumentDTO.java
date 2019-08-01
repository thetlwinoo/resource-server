package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ProductDocument entity.
 */
public class ProductDocumentDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    
    @Lob
    private String documentNode;


    private Long productId;

    private String productProductName;

    private Long cultureId;

    private String cultureCultureName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNode() {
        return documentNode;
    }

    public void setDocumentNode(String documentNode) {
        this.documentNode = documentNode;
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

    public Long getCultureId() {
        return cultureId;
    }

    public void setCultureId(Long cultureId) {
        this.cultureId = cultureId;
    }

    public String getCultureCultureName() {
        return cultureCultureName;
    }

    public void setCultureCultureName(String cultureCultureName) {
        this.cultureCultureName = cultureCultureName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDocumentDTO productDocumentDTO = (ProductDocumentDTO) o;
        if (productDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDocumentDTO{" +
            "id=" + getId() +
            ", documentNode='" + getDocumentNode() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            ", culture=" + getCultureId() +
            ", culture='" + getCultureCultureName() + "'" +
            "}";
    }
}
