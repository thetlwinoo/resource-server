import { element, by, ElementFinder } from 'protractor';

export class ProductModelDescriptionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-model-description div table .btn-danger'));
    title = element.all(by.css('jhi-product-model-description div h2#page-heading span')).first();

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

export class ProductModelDescriptionUpdatePage {
    pageTitle = element(by.id('jhi-product-model-description-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    languageSelect = element(by.id('field_language'));
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

    async setLanguageSelect(language) {
        await this.languageSelect.sendKeys(language);
    }

    async getLanguageSelect() {
        return this.languageSelect.element(by.css('option:checked')).getText();
    }

    async languageSelectLastOption() {
        await this.languageSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

export class ProductModelDescriptionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productModelDescription-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productModelDescription'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
