import { element, by, ElementFinder } from 'protractor';

export class ProductsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-products div table .btn-danger'));
    title = element.all(by.css('jhi-products div h2#page-heading span')).first();

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

export class ProductsUpdatePage {
    pageTitle = element(by.id('jhi-products-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    productNameInput = element(by.id('field_productName'));
    productNumberInput = element(by.id('field_productNumber'));
    searchDetailsInput = element(by.id('field_searchDetails'));
    makeFlagInput = element(by.id('field_makeFlag'));
    finishedGoodsFlagInput = element(by.id('field_finishedGoodsFlag'));
    colorInput = element(by.id('field_color'));
    safetyStockLevelInput = element(by.id('field_safetyStockLevel'));
    reorderPointInput = element(by.id('field_reorderPoint'));
    standardCostInput = element(by.id('field_standardCost'));
    unitPriceInput = element(by.id('field_unitPrice'));
    recommendedRetailPriceInput = element(by.id('field_recommendedRetailPrice'));
    brandInput = element(by.id('field_brand'));
    specifySizeInput = element(by.id('field_specifySize'));
    weightInput = element(by.id('field_weight'));
    daysToManufactureInput = element(by.id('field_daysToManufacture'));
    productLineInput = element(by.id('field_productLine'));
    classTypeInput = element(by.id('field_classType'));
    styleInput = element(by.id('field_style'));
    customFieldsInput = element(by.id('field_customFields'));
    photoInput = element(by.id('field_photo'));
    sellStartDateInput = element(by.id('field_sellStartDate'));
    sellEndDateInput = element(by.id('field_sellEndDate'));
    marketingCommentsInput = element(by.id('field_marketingComments'));
    internalCommentsInput = element(by.id('field_internalComments'));
    discontinuedDateInput = element(by.id('field_discontinuedDate'));
    sellCountInput = element(by.id('field_sellCount'));
    reviewLineSelect = element(by.id('field_reviewLine'));
    merchantSelect = element(by.id('field_merchant'));
    unitPackageSelect = element(by.id('field_unitPackage'));
    outerPackageSelect = element(by.id('field_outerPackage'));
    supplierSelect = element(by.id('field_supplier'));
    productSubCategorySelect = element(by.id('field_productSubCategory'));
    sizeUnitMeasureCodeSelect = element(by.id('field_sizeUnitMeasureCode'));
    weightUnitMeasureCodeSelect = element(by.id('field_weightUnitMeasureCode'));
    productModelSelect = element(by.id('field_productModel'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setProductNameInput(productName) {
        await this.productNameInput.sendKeys(productName);
    }

    async getProductNameInput() {
        return this.productNameInput.getAttribute('value');
    }

    async setProductNumberInput(productNumber) {
        await this.productNumberInput.sendKeys(productNumber);
    }

    async getProductNumberInput() {
        return this.productNumberInput.getAttribute('value');
    }

    async setSearchDetailsInput(searchDetails) {
        await this.searchDetailsInput.sendKeys(searchDetails);
    }

    async getSearchDetailsInput() {
        return this.searchDetailsInput.getAttribute('value');
    }

    getMakeFlagInput() {
        return this.makeFlagInput;
    }
    getFinishedGoodsFlagInput() {
        return this.finishedGoodsFlagInput;
    }
    async setColorInput(color) {
        await this.colorInput.sendKeys(color);
    }

    async getColorInput() {
        return this.colorInput.getAttribute('value');
    }

    async setSafetyStockLevelInput(safetyStockLevel) {
        await this.safetyStockLevelInput.sendKeys(safetyStockLevel);
    }

    async getSafetyStockLevelInput() {
        return this.safetyStockLevelInput.getAttribute('value');
    }

    async setReorderPointInput(reorderPoint) {
        await this.reorderPointInput.sendKeys(reorderPoint);
    }

    async getReorderPointInput() {
        return this.reorderPointInput.getAttribute('value');
    }

    async setStandardCostInput(standardCost) {
        await this.standardCostInput.sendKeys(standardCost);
    }

    async getStandardCostInput() {
        return this.standardCostInput.getAttribute('value');
    }

    async setUnitPriceInput(unitPrice) {
        await this.unitPriceInput.sendKeys(unitPrice);
    }

    async getUnitPriceInput() {
        return this.unitPriceInput.getAttribute('value');
    }

    async setRecommendedRetailPriceInput(recommendedRetailPrice) {
        await this.recommendedRetailPriceInput.sendKeys(recommendedRetailPrice);
    }

    async getRecommendedRetailPriceInput() {
        return this.recommendedRetailPriceInput.getAttribute('value');
    }

    async setBrandInput(brand) {
        await this.brandInput.sendKeys(brand);
    }

    async getBrandInput() {
        return this.brandInput.getAttribute('value');
    }

    async setSpecifySizeInput(specifySize) {
        await this.specifySizeInput.sendKeys(specifySize);
    }

    async getSpecifySizeInput() {
        return this.specifySizeInput.getAttribute('value');
    }

    async setWeightInput(weight) {
        await this.weightInput.sendKeys(weight);
    }

    async getWeightInput() {
        return this.weightInput.getAttribute('value');
    }

    async setDaysToManufactureInput(daysToManufacture) {
        await this.daysToManufactureInput.sendKeys(daysToManufacture);
    }

    async getDaysToManufactureInput() {
        return this.daysToManufactureInput.getAttribute('value');
    }

    async setProductLineInput(productLine) {
        await this.productLineInput.sendKeys(productLine);
    }

    async getProductLineInput() {
        return this.productLineInput.getAttribute('value');
    }

    async setClassTypeInput(classType) {
        await this.classTypeInput.sendKeys(classType);
    }

    async getClassTypeInput() {
        return this.classTypeInput.getAttribute('value');
    }

    async setStyleInput(style) {
        await this.styleInput.sendKeys(style);
    }

    async getStyleInput() {
        return this.styleInput.getAttribute('value');
    }

    async setCustomFieldsInput(customFields) {
        await this.customFieldsInput.sendKeys(customFields);
    }

    async getCustomFieldsInput() {
        return this.customFieldsInput.getAttribute('value');
    }

    async setPhotoInput(photo) {
        await this.photoInput.sendKeys(photo);
    }

    async getPhotoInput() {
        return this.photoInput.getAttribute('value');
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

    async setMarketingCommentsInput(marketingComments) {
        await this.marketingCommentsInput.sendKeys(marketingComments);
    }

    async getMarketingCommentsInput() {
        return this.marketingCommentsInput.getAttribute('value');
    }

    async setInternalCommentsInput(internalComments) {
        await this.internalCommentsInput.sendKeys(internalComments);
    }

    async getInternalCommentsInput() {
        return this.internalCommentsInput.getAttribute('value');
    }

    async setDiscontinuedDateInput(discontinuedDate) {
        await this.discontinuedDateInput.sendKeys(discontinuedDate);
    }

    async getDiscontinuedDateInput() {
        return this.discontinuedDateInput.getAttribute('value');
    }

    async setSellCountInput(sellCount) {
        await this.sellCountInput.sendKeys(sellCount);
    }

    async getSellCountInput() {
        return this.sellCountInput.getAttribute('value');
    }

    async reviewLineSelectLastOption() {
        await this.reviewLineSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async reviewLineSelectOption(option) {
        await this.reviewLineSelect.sendKeys(option);
    }

    getReviewLineSelect(): ElementFinder {
        return this.reviewLineSelect;
    }

    async getReviewLineSelectedOption() {
        return this.reviewLineSelect.element(by.css('option:checked')).getText();
    }

    async merchantSelectLastOption() {
        await this.merchantSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async merchantSelectOption(option) {
        await this.merchantSelect.sendKeys(option);
    }

    getMerchantSelect(): ElementFinder {
        return this.merchantSelect;
    }

    async getMerchantSelectedOption() {
        return this.merchantSelect.element(by.css('option:checked')).getText();
    }

    async unitPackageSelectLastOption() {
        await this.unitPackageSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async unitPackageSelectOption(option) {
        await this.unitPackageSelect.sendKeys(option);
    }

    getUnitPackageSelect(): ElementFinder {
        return this.unitPackageSelect;
    }

    async getUnitPackageSelectedOption() {
        return this.unitPackageSelect.element(by.css('option:checked')).getText();
    }

    async outerPackageSelectLastOption() {
        await this.outerPackageSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async outerPackageSelectOption(option) {
        await this.outerPackageSelect.sendKeys(option);
    }

    getOuterPackageSelect(): ElementFinder {
        return this.outerPackageSelect;
    }

    async getOuterPackageSelectedOption() {
        return this.outerPackageSelect.element(by.css('option:checked')).getText();
    }

    async supplierSelectLastOption() {
        await this.supplierSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async supplierSelectOption(option) {
        await this.supplierSelect.sendKeys(option);
    }

    getSupplierSelect(): ElementFinder {
        return this.supplierSelect;
    }

    async getSupplierSelectedOption() {
        return this.supplierSelect.element(by.css('option:checked')).getText();
    }

    async productSubCategorySelectLastOption() {
        await this.productSubCategorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productSubCategorySelectOption(option) {
        await this.productSubCategorySelect.sendKeys(option);
    }

    getProductSubCategorySelect(): ElementFinder {
        return this.productSubCategorySelect;
    }

    async getProductSubCategorySelectedOption() {
        return this.productSubCategorySelect.element(by.css('option:checked')).getText();
    }

    async sizeUnitMeasureCodeSelectLastOption() {
        await this.sizeUnitMeasureCodeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async sizeUnitMeasureCodeSelectOption(option) {
        await this.sizeUnitMeasureCodeSelect.sendKeys(option);
    }

    getSizeUnitMeasureCodeSelect(): ElementFinder {
        return this.sizeUnitMeasureCodeSelect;
    }

    async getSizeUnitMeasureCodeSelectedOption() {
        return this.sizeUnitMeasureCodeSelect.element(by.css('option:checked')).getText();
    }

    async weightUnitMeasureCodeSelectLastOption() {
        await this.weightUnitMeasureCodeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async weightUnitMeasureCodeSelectOption(option) {
        await this.weightUnitMeasureCodeSelect.sendKeys(option);
    }

    getWeightUnitMeasureCodeSelect(): ElementFinder {
        return this.weightUnitMeasureCodeSelect;
    }

    async getWeightUnitMeasureCodeSelectedOption() {
        return this.weightUnitMeasureCodeSelect.element(by.css('option:checked')).getText();
    }

    async productModelSelectLastOption() {
        await this.productModelSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productModelSelectOption(option) {
        await this.productModelSelect.sendKeys(option);
    }

    getProductModelSelect(): ElementFinder {
        return this.productModelSelect;
    }

    async getProductModelSelectedOption() {
        return this.productModelSelect.element(by.css('option:checked')).getText();
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

export class ProductsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-products-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-products'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
