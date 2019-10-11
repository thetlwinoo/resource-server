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
    handleInput = element(by.id('field_handle'));
    productNumberInput = element(by.id('field_productNumber'));
    searchDetailsInput = element(by.id('field_searchDetails'));
    sellCountInput = element(by.id('field_sellCount'));
    activeIndInput = element(by.id('field_activeInd'));
    documentSelect = element(by.id('field_document'));
    supplierSelect = element(by.id('field_supplier'));
    productCategorySelect = element(by.id('field_productCategory'));
    productBrandSelect = element(by.id('field_productBrand'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setProductNameInput(productName) {
        await this.productNameInput.sendKeys(productName);
    }

    async getProductNameInput() {
        return this.productNameInput.getAttribute('value');
    }

    async setHandleInput(handle) {
        await this.handleInput.sendKeys(handle);
    }

    async getHandleInput() {
        return this.handleInput.getAttribute('value');
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

    async setSellCountInput(sellCount) {
        await this.sellCountInput.sendKeys(sellCount);
    }

    async getSellCountInput() {
        return this.sellCountInput.getAttribute('value');
    }

    getActiveIndInput() {
        return this.activeIndInput;
    }

    async documentSelectLastOption() {
        await this.documentSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async documentSelectOption(option) {
        await this.documentSelect.sendKeys(option);
    }

    getDocumentSelect(): ElementFinder {
        return this.documentSelect;
    }

    async getDocumentSelectedOption() {
        return this.documentSelect.element(by.css('option:checked')).getText();
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
