import { element, by, ElementFinder } from 'protractor';

export class ProductSubCategoryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-sub-category div table .btn-danger'));
    title = element.all(by.css('jhi-product-sub-category div h2#page-heading span')).first();

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

export class ProductSubCategoryUpdatePage {
    pageTitle = element(by.id('jhi-product-sub-category-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    productSubCategoryNameInput = element(by.id('field_productSubCategoryName'));
    productCategorySelect = element(by.id('field_productCategory'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setProductSubCategoryNameInput(productSubCategoryName) {
        await this.productSubCategoryNameInput.sendKeys(productSubCategoryName);
    }

    async getProductSubCategoryNameInput() {
        return this.productSubCategoryNameInput.getAttribute('value');
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

export class ProductSubCategoryDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productSubCategory-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productSubCategory'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
