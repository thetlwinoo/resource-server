import { element, by, ElementFinder } from 'protractor';

export class ProductBrandComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-brand div table .btn-danger'));
    title = element.all(by.css('jhi-product-brand div h2#page-heading span')).first();

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

export class ProductBrandUpdatePage {
    pageTitle = element(by.id('jhi-product-brand-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    productBrandNameInput = element(by.id('field_productBrandName'));
    photoInput = element(by.id('file_photo'));
    merchantSelect = element(by.id('field_merchant'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setProductBrandNameInput(productBrandName) {
        await this.productBrandNameInput.sendKeys(productBrandName);
    }

    async getProductBrandNameInput() {
        return this.productBrandNameInput.getAttribute('value');
    }

    async setPhotoInput(photo) {
        await this.photoInput.sendKeys(photo);
    }

    async getPhotoInput() {
        return this.photoInput.getAttribute('value');
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

export class ProductBrandDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productBrand-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productBrand'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
