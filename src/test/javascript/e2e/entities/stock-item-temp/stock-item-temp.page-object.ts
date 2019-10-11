import { element, by, ElementFinder } from 'protractor';

export class StockItemTempComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-stock-item-temp div table .btn-danger'));
    title = element.all(by.css('jhi-stock-item-temp div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class StockItemTempUpdatePage {
    pageTitle = element(by.id('jhi-stock-item-temp-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    stockItemNameInput = element(by.id('field_stockItemName'));
    vendorCodeInput = element(by.id('field_vendorCode'));
    vendorSKUInput = element(by.id('field_vendorSKU'));
    barcodeInput = element(by.id('field_barcode'));
    barcodeTypeIdInput = element(by.id('field_barcodeTypeId'));
    barcodeTypeNameInput = element(by.id('field_barcodeTypeName'));
    productTypeInput = element(by.id('field_productType'));
    productCategoryIdInput = element(by.id('field_productCategoryId'));
    productCategoryNameInput = element(by.id('field_productCategoryName'));
    productAttributeSetIdInput = element(by.id('field_productAttributeSetId'));
    productAttributeIdInput = element(by.id('field_productAttributeId'));
    productAttributeValueInput = element(by.id('field_productAttributeValue'));
    productOptionSetIdInput = element(by.id('field_productOptionSetId'));
    productOptionIdInput = element(by.id('field_productOptionId'));
    productOptionValueInput = element(by.id('field_productOptionValue'));
    modelNameInput = element(by.id('field_modelName'));
    modelNumberInput = element(by.id('field_modelNumber'));
    materialIdInput = element(by.id('field_materialId'));
    materialNameInput = element(by.id('field_materialName'));
    shortDescriptionInput = element(by.id('field_shortDescription'));
    descriptionInput = element(by.id('field_description'));
    productBrandIdInput = element(by.id('field_productBrandId'));
    productBrandNameInput = element(by.id('field_productBrandName'));
    highlightsInput = element(by.id('field_highlights'));
    searchDetailsInput = element(by.id('field_searchDetails'));
    careInstructionsInput = element(by.id('field_careInstructions'));
    dangerousGoodsInput = element(by.id('field_dangerousGoods'));
    videoUrlInput = element(by.id('field_videoUrl'));
    unitPriceInput = element(by.id('field_unitPrice'));
    remommendedRetailPriceInput = element(by.id('field_remommendedRetailPrice'));
    currencyCodeInput = element(by.id('field_currencyCode'));
    quantityOnHandInput = element(by.id('field_quantityOnHand'));
    warrantyPeriodInput = element(by.id('field_warrantyPeriod'));
    warrantyPolicyInput = element(by.id('field_warrantyPolicy'));
    warrantyTypeIdInput = element(by.id('field_warrantyTypeId'));
    warrantyTypeNameInput = element(by.id('field_warrantyTypeName'));
    whatInTheBoxInput = element(by.id('field_whatInTheBox'));
    itemLengthInput = element(by.id('field_itemLength'));
    itemWidthInput = element(by.id('field_itemWidth'));
    itemHeightInput = element(by.id('field_itemHeight'));
    itemWeightInput = element(by.id('field_itemWeight'));
    itemPackageLengthInput = element(by.id('field_itemPackageLength'));
    itemPackageWidthInput = element(by.id('field_itemPackageWidth'));
    itemPackageHeightInput = element(by.id('field_itemPackageHeight'));
    itemPackageWeightInput = element(by.id('field_itemPackageWeight'));
    itemLengthUnitMeasureIdInput = element(by.id('field_itemLengthUnitMeasureId'));
    itemLengthUnitMeasureCodeInput = element(by.id('field_itemLengthUnitMeasureCode'));
    itemWidthUnitMeasureIdInput = element(by.id('field_itemWidthUnitMeasureId'));
    itemWidthUnitMeasureCodeInput = element(by.id('field_itemWidthUnitMeasureCode'));
    itemHeightUnitMeasureIdInput = element(by.id('field_itemHeightUnitMeasureId'));
    itemHeightUnitMeasureCodeInput = element(by.id('field_itemHeightUnitMeasureCode'));
    itemWeightUnitMeasureIdInput = element(by.id('field_itemWeightUnitMeasureId'));
    itemWeightUnitMeasureCodeInput = element(by.id('field_itemWeightUnitMeasureCode'));
    itemPackageLengthUnitMeasureIdInput = element(by.id('field_itemPackageLengthUnitMeasureId'));
    itemPackageLengthUnitMeasureCodeInput = element(by.id('field_itemPackageLengthUnitMeasureCode'));
    itemPackageWidthUnitMeasureIdInput = element(by.id('field_itemPackageWidthUnitMeasureId'));
    itemPackageWidthUnitMeasureCodeInput = element(by.id('field_itemPackageWidthUnitMeasureCode'));
    itemPackageHeightUnitMeasureIdInput = element(by.id('field_itemPackageHeightUnitMeasureId'));
    itemPackageHeightUnitMeasureCodeInput = element(by.id('field_itemPackageHeightUnitMeasureCode'));
    itemPackageWeightUnitMeasureIdInput = element(by.id('field_itemPackageWeightUnitMeasureId'));
    itemPackageWeightUnitMeasureCodeInput = element(by.id('field_itemPackageWeightUnitMeasureCode'));
    noOfPiecesInput = element(by.id('field_noOfPieces'));
    noOfItemsInput = element(by.id('field_noOfItems'));
    manufactureInput = element(by.id('field_manufacture'));
    specialFeacturesInput = element(by.id('field_specialFeactures'));
    productComplianceCertificateInput = element(by.id('field_productComplianceCertificate'));
    genuineAndLegalInput = element(by.id('field_genuineAndLegal'));
    countryOfOriginInput = element(by.id('field_countryOfOrigin'));
    usageAndSideEffectsInput = element(by.id('field_usageAndSideEffects'));
    safetyWarnningInput = element(by.id('field_safetyWarnning'));
    sellStartDateInput = element(by.id('field_sellStartDate'));
    sellEndDateInput = element(by.id('field_sellEndDate'));
    statusInput = element(by.id('field_status'));
    uploadTransactionSelect = element(by.id('field_uploadTransaction'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setStockItemNameInput(stockItemName) {
        await this.stockItemNameInput.sendKeys(stockItemName);
    }

    async getStockItemNameInput() {
        return this.stockItemNameInput.getAttribute('value');
    }

    async setVendorCodeInput(vendorCode) {
        await this.vendorCodeInput.sendKeys(vendorCode);
    }

    async getVendorCodeInput() {
        return this.vendorCodeInput.getAttribute('value');
    }

    async setVendorSKUInput(vendorSKU) {
        await this.vendorSKUInput.sendKeys(vendorSKU);
    }

    async getVendorSKUInput() {
        return this.vendorSKUInput.getAttribute('value');
    }

    async setBarcodeInput(barcode) {
        await this.barcodeInput.sendKeys(barcode);
    }

    async getBarcodeInput() {
        return this.barcodeInput.getAttribute('value');
    }

    async setBarcodeTypeIdInput(barcodeTypeId) {
        await this.barcodeTypeIdInput.sendKeys(barcodeTypeId);
    }

    async getBarcodeTypeIdInput() {
        return this.barcodeTypeIdInput.getAttribute('value');
    }

    async setBarcodeTypeNameInput(barcodeTypeName) {
        await this.barcodeTypeNameInput.sendKeys(barcodeTypeName);
    }

    async getBarcodeTypeNameInput() {
        return this.barcodeTypeNameInput.getAttribute('value');
    }

    async setProductTypeInput(productType) {
        await this.productTypeInput.sendKeys(productType);
    }

    async getProductTypeInput() {
        return this.productTypeInput.getAttribute('value');
    }

    async setProductCategoryIdInput(productCategoryId) {
        await this.productCategoryIdInput.sendKeys(productCategoryId);
    }

    async getProductCategoryIdInput() {
        return this.productCategoryIdInput.getAttribute('value');
    }

    async setProductCategoryNameInput(productCategoryName) {
        await this.productCategoryNameInput.sendKeys(productCategoryName);
    }

    async getProductCategoryNameInput() {
        return this.productCategoryNameInput.getAttribute('value');
    }

    async setProductAttributeSetIdInput(productAttributeSetId) {
        await this.productAttributeSetIdInput.sendKeys(productAttributeSetId);
    }

    async getProductAttributeSetIdInput() {
        return this.productAttributeSetIdInput.getAttribute('value');
    }

    async setProductAttributeIdInput(productAttributeId) {
        await this.productAttributeIdInput.sendKeys(productAttributeId);
    }

    async getProductAttributeIdInput() {
        return this.productAttributeIdInput.getAttribute('value');
    }

    async setProductAttributeValueInput(productAttributeValue) {
        await this.productAttributeValueInput.sendKeys(productAttributeValue);
    }

    async getProductAttributeValueInput() {
        return this.productAttributeValueInput.getAttribute('value');
    }

    async setProductOptionSetIdInput(productOptionSetId) {
        await this.productOptionSetIdInput.sendKeys(productOptionSetId);
    }

    async getProductOptionSetIdInput() {
        return this.productOptionSetIdInput.getAttribute('value');
    }

    async setProductOptionIdInput(productOptionId) {
        await this.productOptionIdInput.sendKeys(productOptionId);
    }

    async getProductOptionIdInput() {
        return this.productOptionIdInput.getAttribute('value');
    }

    async setProductOptionValueInput(productOptionValue) {
        await this.productOptionValueInput.sendKeys(productOptionValue);
    }

    async getProductOptionValueInput() {
        return this.productOptionValueInput.getAttribute('value');
    }

    async setModelNameInput(modelName) {
        await this.modelNameInput.sendKeys(modelName);
    }

    async getModelNameInput() {
        return this.modelNameInput.getAttribute('value');
    }

    async setModelNumberInput(modelNumber) {
        await this.modelNumberInput.sendKeys(modelNumber);
    }

    async getModelNumberInput() {
        return this.modelNumberInput.getAttribute('value');
    }

    async setMaterialIdInput(materialId) {
        await this.materialIdInput.sendKeys(materialId);
    }

    async getMaterialIdInput() {
        return this.materialIdInput.getAttribute('value');
    }

    async setMaterialNameInput(materialName) {
        await this.materialNameInput.sendKeys(materialName);
    }

    async getMaterialNameInput() {
        return this.materialNameInput.getAttribute('value');
    }

    async setShortDescriptionInput(shortDescription) {
        await this.shortDescriptionInput.sendKeys(shortDescription);
    }

    async getShortDescriptionInput() {
        return this.shortDescriptionInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setProductBrandIdInput(productBrandId) {
        await this.productBrandIdInput.sendKeys(productBrandId);
    }

    async getProductBrandIdInput() {
        return this.productBrandIdInput.getAttribute('value');
    }

    async setProductBrandNameInput(productBrandName) {
        await this.productBrandNameInput.sendKeys(productBrandName);
    }

    async getProductBrandNameInput() {
        return this.productBrandNameInput.getAttribute('value');
    }

    async setHighlightsInput(highlights) {
        await this.highlightsInput.sendKeys(highlights);
    }

    async getHighlightsInput() {
        return this.highlightsInput.getAttribute('value');
    }

    async setSearchDetailsInput(searchDetails) {
        await this.searchDetailsInput.sendKeys(searchDetails);
    }

    async getSearchDetailsInput() {
        return this.searchDetailsInput.getAttribute('value');
    }

    async setCareInstructionsInput(careInstructions) {
        await this.careInstructionsInput.sendKeys(careInstructions);
    }

    async getCareInstructionsInput() {
        return this.careInstructionsInput.getAttribute('value');
    }

    async setDangerousGoodsInput(dangerousGoods) {
        await this.dangerousGoodsInput.sendKeys(dangerousGoods);
    }

    async getDangerousGoodsInput() {
        return this.dangerousGoodsInput.getAttribute('value');
    }

    async setVideoUrlInput(videoUrl) {
        await this.videoUrlInput.sendKeys(videoUrl);
    }

    async getVideoUrlInput() {
        return this.videoUrlInput.getAttribute('value');
    }

    async setUnitPriceInput(unitPrice) {
        await this.unitPriceInput.sendKeys(unitPrice);
    }

    async getUnitPriceInput() {
        return this.unitPriceInput.getAttribute('value');
    }

    async setRemommendedRetailPriceInput(remommendedRetailPrice) {
        await this.remommendedRetailPriceInput.sendKeys(remommendedRetailPrice);
    }

    async getRemommendedRetailPriceInput() {
        return this.remommendedRetailPriceInput.getAttribute('value');
    }

    async setCurrencyCodeInput(currencyCode) {
        await this.currencyCodeInput.sendKeys(currencyCode);
    }

    async getCurrencyCodeInput() {
        return this.currencyCodeInput.getAttribute('value');
    }

    async setQuantityOnHandInput(quantityOnHand) {
        await this.quantityOnHandInput.sendKeys(quantityOnHand);
    }

    async getQuantityOnHandInput() {
        return this.quantityOnHandInput.getAttribute('value');
    }

    async setWarrantyPeriodInput(warrantyPeriod) {
        await this.warrantyPeriodInput.sendKeys(warrantyPeriod);
    }

    async getWarrantyPeriodInput() {
        return this.warrantyPeriodInput.getAttribute('value');
    }

    async setWarrantyPolicyInput(warrantyPolicy) {
        await this.warrantyPolicyInput.sendKeys(warrantyPolicy);
    }

    async getWarrantyPolicyInput() {
        return this.warrantyPolicyInput.getAttribute('value');
    }

    async setWarrantyTypeIdInput(warrantyTypeId) {
        await this.warrantyTypeIdInput.sendKeys(warrantyTypeId);
    }

    async getWarrantyTypeIdInput() {
        return this.warrantyTypeIdInput.getAttribute('value');
    }

    async setWarrantyTypeNameInput(warrantyTypeName) {
        await this.warrantyTypeNameInput.sendKeys(warrantyTypeName);
    }

    async getWarrantyTypeNameInput() {
        return this.warrantyTypeNameInput.getAttribute('value');
    }

    async setWhatInTheBoxInput(whatInTheBox) {
        await this.whatInTheBoxInput.sendKeys(whatInTheBox);
    }

    async getWhatInTheBoxInput() {
        return this.whatInTheBoxInput.getAttribute('value');
    }

    async setItemLengthInput(itemLength) {
        await this.itemLengthInput.sendKeys(itemLength);
    }

    async getItemLengthInput() {
        return this.itemLengthInput.getAttribute('value');
    }

    async setItemWidthInput(itemWidth) {
        await this.itemWidthInput.sendKeys(itemWidth);
    }

    async getItemWidthInput() {
        return this.itemWidthInput.getAttribute('value');
    }

    async setItemHeightInput(itemHeight) {
        await this.itemHeightInput.sendKeys(itemHeight);
    }

    async getItemHeightInput() {
        return this.itemHeightInput.getAttribute('value');
    }

    async setItemWeightInput(itemWeight) {
        await this.itemWeightInput.sendKeys(itemWeight);
    }

    async getItemWeightInput() {
        return this.itemWeightInput.getAttribute('value');
    }

    async setItemPackageLengthInput(itemPackageLength) {
        await this.itemPackageLengthInput.sendKeys(itemPackageLength);
    }

    async getItemPackageLengthInput() {
        return this.itemPackageLengthInput.getAttribute('value');
    }

    async setItemPackageWidthInput(itemPackageWidth) {
        await this.itemPackageWidthInput.sendKeys(itemPackageWidth);
    }

    async getItemPackageWidthInput() {
        return this.itemPackageWidthInput.getAttribute('value');
    }

    async setItemPackageHeightInput(itemPackageHeight) {
        await this.itemPackageHeightInput.sendKeys(itemPackageHeight);
    }

    async getItemPackageHeightInput() {
        return this.itemPackageHeightInput.getAttribute('value');
    }

    async setItemPackageWeightInput(itemPackageWeight) {
        await this.itemPackageWeightInput.sendKeys(itemPackageWeight);
    }

    async getItemPackageWeightInput() {
        return this.itemPackageWeightInput.getAttribute('value');
    }

    async setItemLengthUnitMeasureIdInput(itemLengthUnitMeasureId) {
        await this.itemLengthUnitMeasureIdInput.sendKeys(itemLengthUnitMeasureId);
    }

    async getItemLengthUnitMeasureIdInput() {
        return this.itemLengthUnitMeasureIdInput.getAttribute('value');
    }

    async setItemLengthUnitMeasureCodeInput(itemLengthUnitMeasureCode) {
        await this.itemLengthUnitMeasureCodeInput.sendKeys(itemLengthUnitMeasureCode);
    }

    async getItemLengthUnitMeasureCodeInput() {
        return this.itemLengthUnitMeasureCodeInput.getAttribute('value');
    }

    async setItemWidthUnitMeasureIdInput(itemWidthUnitMeasureId) {
        await this.itemWidthUnitMeasureIdInput.sendKeys(itemWidthUnitMeasureId);
    }

    async getItemWidthUnitMeasureIdInput() {
        return this.itemWidthUnitMeasureIdInput.getAttribute('value');
    }

    async setItemWidthUnitMeasureCodeInput(itemWidthUnitMeasureCode) {
        await this.itemWidthUnitMeasureCodeInput.sendKeys(itemWidthUnitMeasureCode);
    }

    async getItemWidthUnitMeasureCodeInput() {
        return this.itemWidthUnitMeasureCodeInput.getAttribute('value');
    }

    async setItemHeightUnitMeasureIdInput(itemHeightUnitMeasureId) {
        await this.itemHeightUnitMeasureIdInput.sendKeys(itemHeightUnitMeasureId);
    }

    async getItemHeightUnitMeasureIdInput() {
        return this.itemHeightUnitMeasureIdInput.getAttribute('value');
    }

    async setItemHeightUnitMeasureCodeInput(itemHeightUnitMeasureCode) {
        await this.itemHeightUnitMeasureCodeInput.sendKeys(itemHeightUnitMeasureCode);
    }

    async getItemHeightUnitMeasureCodeInput() {
        return this.itemHeightUnitMeasureCodeInput.getAttribute('value');
    }

    async setItemWeightUnitMeasureIdInput(itemWeightUnitMeasureId) {
        await this.itemWeightUnitMeasureIdInput.sendKeys(itemWeightUnitMeasureId);
    }

    async getItemWeightUnitMeasureIdInput() {
        return this.itemWeightUnitMeasureIdInput.getAttribute('value');
    }

    async setItemWeightUnitMeasureCodeInput(itemWeightUnitMeasureCode) {
        await this.itemWeightUnitMeasureCodeInput.sendKeys(itemWeightUnitMeasureCode);
    }

    async getItemWeightUnitMeasureCodeInput() {
        return this.itemWeightUnitMeasureCodeInput.getAttribute('value');
    }

    async setItemPackageLengthUnitMeasureIdInput(itemPackageLengthUnitMeasureId) {
        await this.itemPackageLengthUnitMeasureIdInput.sendKeys(itemPackageLengthUnitMeasureId);
    }

    async getItemPackageLengthUnitMeasureIdInput() {
        return this.itemPackageLengthUnitMeasureIdInput.getAttribute('value');
    }

    async setItemPackageLengthUnitMeasureCodeInput(itemPackageLengthUnitMeasureCode) {
        await this.itemPackageLengthUnitMeasureCodeInput.sendKeys(itemPackageLengthUnitMeasureCode);
    }

    async getItemPackageLengthUnitMeasureCodeInput() {
        return this.itemPackageLengthUnitMeasureCodeInput.getAttribute('value');
    }

    async setItemPackageWidthUnitMeasureIdInput(itemPackageWidthUnitMeasureId) {
        await this.itemPackageWidthUnitMeasureIdInput.sendKeys(itemPackageWidthUnitMeasureId);
    }

    async getItemPackageWidthUnitMeasureIdInput() {
        return this.itemPackageWidthUnitMeasureIdInput.getAttribute('value');
    }

    async setItemPackageWidthUnitMeasureCodeInput(itemPackageWidthUnitMeasureCode) {
        await this.itemPackageWidthUnitMeasureCodeInput.sendKeys(itemPackageWidthUnitMeasureCode);
    }

    async getItemPackageWidthUnitMeasureCodeInput() {
        return this.itemPackageWidthUnitMeasureCodeInput.getAttribute('value');
    }

    async setItemPackageHeightUnitMeasureIdInput(itemPackageHeightUnitMeasureId) {
        await this.itemPackageHeightUnitMeasureIdInput.sendKeys(itemPackageHeightUnitMeasureId);
    }

    async getItemPackageHeightUnitMeasureIdInput() {
        return this.itemPackageHeightUnitMeasureIdInput.getAttribute('value');
    }

    async setItemPackageHeightUnitMeasureCodeInput(itemPackageHeightUnitMeasureCode) {
        await this.itemPackageHeightUnitMeasureCodeInput.sendKeys(itemPackageHeightUnitMeasureCode);
    }

    async getItemPackageHeightUnitMeasureCodeInput() {
        return this.itemPackageHeightUnitMeasureCodeInput.getAttribute('value');
    }

    async setItemPackageWeightUnitMeasureIdInput(itemPackageWeightUnitMeasureId) {
        await this.itemPackageWeightUnitMeasureIdInput.sendKeys(itemPackageWeightUnitMeasureId);
    }

    async getItemPackageWeightUnitMeasureIdInput() {
        return this.itemPackageWeightUnitMeasureIdInput.getAttribute('value');
    }

    async setItemPackageWeightUnitMeasureCodeInput(itemPackageWeightUnitMeasureCode) {
        await this.itemPackageWeightUnitMeasureCodeInput.sendKeys(itemPackageWeightUnitMeasureCode);
    }

    async getItemPackageWeightUnitMeasureCodeInput() {
        return this.itemPackageWeightUnitMeasureCodeInput.getAttribute('value');
    }

    async setNoOfPiecesInput(noOfPieces) {
        await this.noOfPiecesInput.sendKeys(noOfPieces);
    }

    async getNoOfPiecesInput() {
        return this.noOfPiecesInput.getAttribute('value');
    }

    async setNoOfItemsInput(noOfItems) {
        await this.noOfItemsInput.sendKeys(noOfItems);
    }

    async getNoOfItemsInput() {
        return this.noOfItemsInput.getAttribute('value');
    }

    async setManufactureInput(manufacture) {
        await this.manufactureInput.sendKeys(manufacture);
    }

    async getManufactureInput() {
        return this.manufactureInput.getAttribute('value');
    }

    async setSpecialFeacturesInput(specialFeactures) {
        await this.specialFeacturesInput.sendKeys(specialFeactures);
    }

    async getSpecialFeacturesInput() {
        return this.specialFeacturesInput.getAttribute('value');
    }

    async setProductComplianceCertificateInput(productComplianceCertificate) {
        await this.productComplianceCertificateInput.sendKeys(productComplianceCertificate);
    }

    async getProductComplianceCertificateInput() {
        return this.productComplianceCertificateInput.getAttribute('value');
    }

    getGenuineAndLegalInput() {
        return this.genuineAndLegalInput;
    }
    async setCountryOfOriginInput(countryOfOrigin) {
        await this.countryOfOriginInput.sendKeys(countryOfOrigin);
    }

    async getCountryOfOriginInput() {
        return this.countryOfOriginInput.getAttribute('value');
    }

    async setUsageAndSideEffectsInput(usageAndSideEffects) {
        await this.usageAndSideEffectsInput.sendKeys(usageAndSideEffects);
    }

    async getUsageAndSideEffectsInput() {
        return this.usageAndSideEffectsInput.getAttribute('value');
    }

    async setSafetyWarnningInput(safetyWarnning) {
        await this.safetyWarnningInput.sendKeys(safetyWarnning);
    }

    async getSafetyWarnningInput() {
        return this.safetyWarnningInput.getAttribute('value');
    }

    async setSellStartDateInput(sellStartDate) {
        await this.sellStartDateInput.sendKeys(sellStartDate);
    }

    async getSellStartDateInput() {
        return this.sellStartDateInput.getAttribute('value');
    }

    async setSellEndDateInput(sellEndDate) {
        await this.sellEndDateInput.sendKeys(sellEndDate);
    }

    async getSellEndDateInput() {
        return this.sellEndDateInput.getAttribute('value');
    }

    async setStatusInput(status) {
        await this.statusInput.sendKeys(status);
    }

    async getStatusInput() {
        return this.statusInput.getAttribute('value');
    }

    async uploadTransactionSelectLastOption() {
        await this.uploadTransactionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async uploadTransactionSelectOption(option) {
        await this.uploadTransactionSelect.sendKeys(option);
    }

    getUploadTransactionSelect(): ElementFinder {
        return this.uploadTransactionSelect;
    }

    async getUploadTransactionSelectedOption() {
        return this.uploadTransactionSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class StockItemTempDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-stockItemTemp-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-stockItemTemp'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
