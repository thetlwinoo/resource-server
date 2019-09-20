package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Products entity.
 */
public class ProductsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String productName;

    private String productNumber;

    @NotNull
    private String searchDetails;

    private String thumbnailUrl;

    private String warrantyPeriod;

    private String warrantyPolicy;

    private Integer sellCount;

    @NotNull
    private String whatInTheBox;


    private Long supplierId;

    private String supplierSupplierName;

    private Long merchantId;

    private String merchantMerchantName;

    private Long unitPackageId;

    private String unitPackagePackageTypeName;

    private Long outerPackageId;

    private String outerPackagePackageTypeName;

    private Long productModelId;

    private String productModelProductModelName;

    private Long productCategoryId;

    private String productCategoryName;

    private Long productBrandId;

    private String productBrandProductBrandName;

    private Long warrantyTypeId;

    private String warrantyTypeWarrantyTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getSearchDetails() {
        return searchDetails;
    }

    public void setSearchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPolicy() {
        return warrantyPolicy;
    }

    public void setWarrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getWhatInTheBox() {
        return whatInTheBox;
    }

    public void setWhatInTheBox(String whatInTheBox) {
        this.whatInTheBox = whatInTheBox;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierSupplierName() {
        return supplierSupplierName;
    }

    public void setSupplierSupplierName(String suppliersSupplierName) {
        this.supplierSupplierName = suppliersSupplierName;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantsId) {
        this.merchantId = merchantsId;
    }

    public String getMerchantMerchantName() {
        return merchantMerchantName;
    }

    public void setMerchantMerchantName(String merchantsMerchantName) {
        this.merchantMerchantName = merchantsMerchantName;
    }

    public Long getUnitPackageId() {
        return unitPackageId;
    }

    public void setUnitPackageId(Long packageTypesId) {
        this.unitPackageId = packageTypesId;
    }

    public String getUnitPackagePackageTypeName() {
        return unitPackagePackageTypeName;
    }

    public void setUnitPackagePackageTypeName(String packageTypesPackageTypeName) {
        this.unitPackagePackageTypeName = packageTypesPackageTypeName;
    }

    public Long getOuterPackageId() {
        return outerPackageId;
    }

    public void setOuterPackageId(Long packageTypesId) {
        this.outerPackageId = packageTypesId;
    }

    public String getOuterPackagePackageTypeName() {
        return outerPackagePackageTypeName;
    }

    public void setOuterPackagePackageTypeName(String packageTypesPackageTypeName) {
        this.outerPackagePackageTypeName = packageTypesPackageTypeName;
    }

    public Long getProductModelId() {
        return productModelId;
    }

    public void setProductModelId(Long productModelId) {
        this.productModelId = productModelId;
    }

    public String getProductModelProductModelName() {
        return productModelProductModelName;
    }

    public void setProductModelProductModelName(String productModelProductModelName) {
        this.productModelProductModelName = productModelProductModelName;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Long getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(Long productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getProductBrandProductBrandName() {
        return productBrandProductBrandName;
    }

    public void setProductBrandProductBrandName(String productBrandProductBrandName) {
        this.productBrandProductBrandName = productBrandProductBrandName;
    }

    public Long getWarrantyTypeId() {
        return warrantyTypeId;
    }

    public void setWarrantyTypeId(Long warrantyTypesId) {
        this.warrantyTypeId = warrantyTypesId;
    }

    public String getWarrantyTypeWarrantyTypeName() {
        return warrantyTypeWarrantyTypeName;
    }

    public void setWarrantyTypeWarrantyTypeName(String warrantyTypesWarrantyTypeName) {
        this.warrantyTypeWarrantyTypeName = warrantyTypesWarrantyTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductsDTO productsDTO = (ProductsDTO) o;
        if (productsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductsDTO{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productNumber='" + getProductNumber() + "'" +
            ", searchDetails='" + getSearchDetails() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", warrantyPeriod='" + getWarrantyPeriod() + "'" +
            ", warrantyPolicy='" + getWarrantyPolicy() + "'" +
            ", sellCount=" + getSellCount() +
            ", whatInTheBox='" + getWhatInTheBox() + "'" +
            ", supplier=" + getSupplierId() +
            ", supplier='" + getSupplierSupplierName() + "'" +
            ", merchant=" + getMerchantId() +
            ", merchant='" + getMerchantMerchantName() + "'" +
            ", unitPackage=" + getUnitPackageId() +
            ", unitPackage='" + getUnitPackagePackageTypeName() + "'" +
            ", outerPackage=" + getOuterPackageId() +
            ", outerPackage='" + getOuterPackagePackageTypeName() + "'" +
            ", productModel=" + getProductModelId() +
            ", productModel='" + getProductModelProductModelName() + "'" +
            ", productCategory=" + getProductCategoryId() +
            ", productCategory='" + getProductCategoryName() + "'" +
            ", productBrand=" + getProductBrandId() +
            ", productBrand='" + getProductBrandProductBrandName() + "'" +
            ", warrantyType=" + getWarrantyTypeId() +
            ", warrantyType='" + getWarrantyTypeWarrantyTypeName() + "'" +
            "}";
    }
}
