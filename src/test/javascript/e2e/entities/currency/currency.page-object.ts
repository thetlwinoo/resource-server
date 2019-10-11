import { element, by, ElementFinder } from 'protractor';

export class CurrencyComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-currency div table .btn-danger'));
    title = element.all(by.css('jhi-currency div h2#page-heading span')).first();

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

export class CurrencyUpdatePage {
    pageTitle = element(by.id('jhi-currency-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    currencyCodeInput = element(by.id('field_currencyCode'));
    currencyNameInput = element(by.id('field_currencyName'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCurrencyCodeInput(currencyCode) {
        await this.currencyCodeInput.sendKeys(currencyCode);
    }

    async getCurrencyCodeInput() {
        return this.currencyCodeInput.getAttribute('value');
    }

    async setCurrencyNameInput(currencyName) {
        await this.currencyNameInput.sendKeys(currencyName);
    }

    async getCurrencyNameInput() {
        return this.currencyNameInput.getAttribute('value');
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

export class CurrencyDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-currency-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-currency'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
