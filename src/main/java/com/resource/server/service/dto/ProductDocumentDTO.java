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
    private byte[] documentNode;

    private String documentNodeContentType;
    private String videoUrl;

    
    @Lob
    private String highlights;


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

    public byte[] getDocumentNode() {
        return documentNode;
    }

    public void setDocumentNode(byte[] documentNode) {
        this.documentNode = documentNode;
    }

    public String getDocumentNodeContentType() {
        return documentNodeContentType;
    }

    public void setDocumentNodeContentType(String documentNodeContentType) {
        this.documentNodeContentType = documentNodeContentType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
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
            ", videoUrl='" + getVideoUrl() + "'" +
            ", highlights='" + getHighlights() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            ", culture=" + getCultureId() +
            ", culture='" + getCultureCultureName() + "'" +
            "}";
    }
}
