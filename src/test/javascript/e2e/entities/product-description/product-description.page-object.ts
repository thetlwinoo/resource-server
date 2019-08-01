import { element, by, ElementFinder } from 'protractor';

export class ProductDescriptionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-description div table .btn-danger'));
    title = element.all(by.css('jhi-product-description div h2#page-heading span')).first();

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

export class ProductDescriptionUpdatePage {
    pageTitle = element(by.id('jhi-product-description-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    productModelSelect = element(by.id('field_productModel'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
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

export class ProductDescriptionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productDescription-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productDescription'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
