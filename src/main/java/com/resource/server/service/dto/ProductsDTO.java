package com.resource.server.service.dto;
import java.time.LocalDate;
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

    @NotNull
    private Boolean makeFlag;

    @NotNull
    private Boolean finishedGoodsFlag;

    private String color;

    @NotNull
    private Integer safetyStockLevel;

    @NotNull
    private Integer reorderPoint;

    @NotNull
    private Float standardCost;

    @NotNull
    private Float unitPrice;

    private Float recommendedRetailPrice;

    private String brand;

    private String specifySize;

    private Float weight;

    @NotNull
    private Integer daysToManufacture;

    private String productLine;

    private String classType;

    private String style;

    private String customFields;

    private String photo;

    @NotNull
    private LocalDate sellStartDate;

    private LocalDate sellEndDate;

    private String marketingComments;

    private String internalComments;

    private LocalDate discontinuedDate;

    private Integer sellCount;


    private Long productReviewId;

    private Long unitPackageId;

    private String unitPackagePackageTypeName;

    private Long outerPackageId;

    private String outerPackagePackageTypeName;

    private Long supplierId;

    private String supplierSupplierName;

    private Long productSubCategoryId;

    private String productSubCategoryProductSubCategoryName;

    private Long sizeUnitMeasureCodeId;

    private String sizeUnitMeasureCodeUnitMeasureCode;

    private Long weightUnitMeasureCodeId;

    private String weightUnitMeasureCodeUnitMeasureCode;

    private Long productModelId;

    private String productModelProductModelName;

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

    public Boolean isMakeFlag() {
        return makeFlag;
    }

    public void setMakeFlag(Boolean makeFlag) {
        this.makeFlag = makeFlag;
    }

    public Boolean isFinishedGoodsFlag() {
        return finishedGoodsFlag;
    }

    public void setFinishedGoodsFlag(Boolean finishedGoodsFlag) {
        this.finishedGoodsFlag = finishedGoodsFlag;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSafetyStockLevel() {
        return safetyStockLevel;
    }

    public void setSafetyStockLevel(Integer safetyStockLevel) {
        this.safetyStockLevel = safetyStockLevel;
    }

    public Integer getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(Integer reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Float getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(Float standardCost) {
        this.standardCost = standardCost;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getRecommendedRetailPrice() {
        return recommendedRetailPrice;
    }

    public void setRecommendedRetailPrice(Float recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpecifySize() {
        return specifySize;
    }

    public void setSpecifySize(String specifySize) {
        this.specifySize = specifySize;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getDaysToManufacture() {
        return daysToManufacture;
    }

    public void setDaysToManufacture(Integer daysToManufacture) {
        this.daysToManufacture = daysToManufacture;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCustomFields() {
        return customFields;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDate getSellStartDate() {
        return sellStartDate;
    }

    public void setSellStartDate(LocalDate sellStartDate) {
        this.sellStartDate = sellStartDate;
    }

    public LocalDate getSellEndDate() {
        return sellEndDate;
    }

    public void setSellEndDate(LocalDate sellEndDate) {
        this.sellEndDate = sellEndDate;
    }

    public String getMarketingComments() {
        return marketingComments;
    }

    public void setMarketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public LocalDate getDiscontinuedDate() {
        return discontinuedDate;
    }

    public void setDiscontinuedDate(LocalDate discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public Long getProductReviewId() {
        return productReviewId;
    }

    public void setProductReviewId(Long reviewLinesId) {
        this.productReviewId = reviewLinesId;
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

    public Long getProductSubCategoryId() {
        return productSubCategoryId;
    }

    public void setProductSubCategoryId(Long productSubCategoryId) {
        this.productSubCategoryId = productSubCategoryId;
    }

    public String getProductSubCategoryProductSubCategoryName() {
        return productSubCategoryProductSubCategoryName;
    }

    public void setProductSubCategoryProductSubCategoryName(String productSubCategoryProductSubCategoryName) {
        this.productSubCategoryProductSubCategoryName = productSubCategoryProductSubCategoryName;
    }

    public Long getSizeUnitMeasureCodeId() {
        return sizeUnitMeasureCodeId;
    }

    public void setSizeUnitMeasureCodeId(Long unitMeasureId) {
        this.sizeUnitMeasureCodeId = unitMeasureId;
    }

    public String getSizeUnitMeasureCodeUnitMeasureCode() {
        return sizeUnitMeasureCodeUnitMeasureCode;
    }

    public void setSizeUnitMeasureCodeUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.sizeUnitMeasureCodeUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getWeightUnitMeasureCodeId() {
        return weightUnitMeasureCodeId;
    }

    public void setWeightUnitMeasureCodeId(Long unitMeasureId) {
        this.weightUnitMeasureCodeId = unitMeasureId;
    }

    public String getWeightUnitMeasureCodeUnitMeasureCode() {
        return weightUnitMeasureCodeUnitMeasureCode;
    }

    public void setWeightUnitMeasureCodeUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.weightUnitMeasureCodeUnitMeasureCode = unitMeasureUnitMeasureCode;
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
            ", makeFlag='" + isMakeFlag() + "'" +
            ", finishedGoodsFlag='" + isFinishedGoodsFlag() + "'" +
            ", color='" + getColor() + "'" +
            ", safetyStockLevel=" + getSafetyStockLevel() +
            ", reorderPoint=" + getReorderPoint() +
            ", standardCost=" + getStandardCost() +
            ", unitPrice=" + getUnitPrice() +
            ", recommendedRetailPrice=" + getRecommendedRetailPrice() +
            ", brand='" + getBrand() + "'" +
            ", specifySize='" + getSpecifySize() + "'" +
            ", weight=" + getWeight() +
            ", daysToManufacture=" + getDaysToManufacture() +
            ", productLine='" + getProductLine() + "'" +
            ", classType='" + getClassType() + "'" +
            ", style='" + getStyle() + "'" +
            ", customFields='" + getCustomFields() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", sellStartDate='" + getSellStartDate() + "'" +
            ", sellEndDate='" + getSellEndDate() + "'" +
            ", marketingComments='" + getMarketingComments() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", discontinuedDate='" + getDiscontinuedDate() + "'" +
            ", sellCount=" + getSellCount() +
            ", productReview=" + getProductReviewId() +
            ", unitPackage=" + getUnitPackageId() +
            ", unitPackage='" + getUnitPackagePackageTypeName() + "'" +
            ", outerPackage=" + getOuterPackageId() +
            ", outerPackage='" + getOuterPackagePackageTypeName() + "'" +
            ", supplier=" + getSupplierId() +
            ", supplier='" + getSupplierSupplierName() + "'" +
            ", productSubCategory=" + getProductSubCategoryId() +
            ", productSubCategory='" + getProductSubCategoryProductSubCategoryName() + "'" +
            ", sizeUnitMeasureCode=" + getSizeUnitMeasureCodeId() +
            ", sizeUnitMeasureCode='" + getSizeUnitMeasureCodeUnitMeasureCode() + "'" +
            ", weightUnitMeasureCode=" + getWeightUnitMeasureCodeId() +
            ", weightUnitMeasureCode='" + getWeightUnitMeasureCodeUnitMeasureCode() + "'" +
            ", productModel=" + getProductModelId() +
            ", productModel='" + getProductModelProductModelName() + "'" +
            "}";
    }
}
