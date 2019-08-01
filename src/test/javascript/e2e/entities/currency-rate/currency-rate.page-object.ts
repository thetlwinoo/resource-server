import { element, by, ElementFinder } from 'protractor';

export class CurrencyRateComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-currency-rate div table .btn-danger'));
    title = element.all(by.css('jhi-currency-rate div h2#page-heading span')).first();

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

export class CurrencyRateUpdatePage {
    pageTitle = element(by.id('jhi-currency-rate-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    currencyRateDateInput = element(by.id('field_currencyRateDate'));
    fromCurrencyCodeInput = element(by.id('field_fromCurrencyCode'));
    toCurrencyCodeInput = element(by.id('field_toCurrencyCode'));
    averageRateInput = element(by.id('field_averageRate'));
    endOfDayRateInput = element(by.id('field_endOfDayRate'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCurrencyRateDateInput(currencyRateDate) {
        await this.currencyRateDateInput.sendKeys(currencyRateDate);
    }

    async getCurrencyRateDateInput() {
        return this.currencyRateDateInput.getAttribute('value');
    }

    async setFromCurrencyCodeInput(fromCurrencyCode) {
        await this.fromCurrencyCodeInput.sendKeys(fromCurrencyCode);
    }

    async getFromCurrencyCodeInput() {
        return this.fromCurrencyCodeInput.getAttribute('value');
    }

    async setToCurrencyCodeInput(toCurrencyCode) {
        await this.toCurrencyCodeInput.sendKeys(toCurrencyCode);
    }

    async getToCurrencyCodeInput() {
        return this.toCurrencyCodeInput.getAttribute('value');
    }

    async setAverageRateInput(averageRate) {
        await this.averageRateInput.sendKeys(averageRate);
    }

    async getAverageRateInput() {
        return this.averageRateInput.getAttribute('value');
    }

    async setEndOfDayRateInput(endOfDayRate) {
        await this.endOfDayRateInput.sendKeys(endOfDayRate);
    }

    async getEndOfDayRateInput() {
        return this.endOfDayRateInput.getAttribute('value');
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

export class CurrencyRateDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-currencyRate-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-currencyRate'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
