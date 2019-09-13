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
    thumbnailUrlInput = element(by.id('field_thumbnailUrl'));
    sellStartDateInput = element(by.id('field_sellStartDate'));
    sellEndDateInput = element(by.id('field_sellEndDate'));
    warrantyPeriodInput = element(by.id('field_warrantyPeriod'));
    warrantyPolicyInput = element(by.id('field_warrantyPolicy'));
    sellCountInput = element(by.id('field_sellCount'));
    whatInTheBoxInput = element(by.id('field_whatInTheBox'));
    supplierSelect = element(by.id('field_supplier'));
    merchantSelect = element(by.id('field_merchant'));
    unitPackageSelect = element(by.id('field_unitPackage'));
    outerPackageSelect = element(by.id('field_outerPackage'));
    productModelSelect = element(by.id('field_productModel'));
    productCategorySelect = element(by.id('field_productCategory'));
    productBrandSelect = element(by.id('field_productBrand'));
    warrantyTypeSelect = element(by.id('field_warrantyType'));

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

    async setThumbnailUrlInput(thumbnailUrl) {
        await this.thumbnailUrlInput.sendKeys(thumbnailUrl);
    }

    async getThumbnailUrlInput() {
        return this.thumbnailUrlInput.getAttribute('value');
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

    async setSellCountInput(sellCount) {
        await this.sellCountInput.sendKeys(sellCount);
    }

    async getSellCountInput() {
        return this.sellCountInput.getAttribute('value');
    }

    async setWhatInTheBoxInput(whatInTheBox) {
        await this.whatInTheBoxInput.sendKeys(whatInTheBox);
    }

    async getWhatInTheBoxInput() {
        return this.whatInTheBoxInput.getAttribute('value');
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

    async productCategorySelectLastOption() {
        await this.productCategorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productCategorySelectOption(option) {
        await this.productCategorySelect.sendKeys(option);
    }

    getProductCategorySelect(): ElementFinder {
        return this.productCategorySelect;
    }

    async getProductCategorySelectedOption() {
        return this.productCategorySelect.element(by.css('option:checked')).getText();
    }

    async productBrandSelectLastOption() {
        await this.productBrandSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productBrandSelectOption(option) {
        await this.productBrandSelect.sendKeys(option);
    }

    getProductBrandSelect(): ElementFinder {
        return this.productBrandSelect;
    }

    async getProductBrandSelectedOption() {
        return this.productBrandSelect.element(by.css('option:checked')).getText();
    }

    async warrantyTypeSelectLastOption() {
        await this.warrantyTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async warrantyTypeSelectOption(option) {
        await this.warrantyTypeSelect.sendKeys(option);
    }

    getWarrantyTypeSelect(): ElementFinder {
        return this.warrantyTypeSelect;
    }

    async getWarrantyTypeSelectedOption() {
        return this.warrantyTypeSelect.element(by.css('option:checked')).getText();
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
