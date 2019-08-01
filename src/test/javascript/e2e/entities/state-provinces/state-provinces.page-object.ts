import { element, by, ElementFinder } from 'protractor';

export class StateProvincesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-state-provinces div table .btn-danger'));
    title = element.all(by.css('jhi-state-provinces div h2#page-heading span')).first();

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

export class StateProvincesUpdatePage {
    pageTitle = element(by.id('jhi-state-provinces-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    stateProvinceCodeInput = element(by.id('field_stateProvinceCode'));
    stateProvinceNameInput = element(by.id('field_stateProvinceName'));
    salesTerritoryInput = element(by.id('field_salesTerritory'));
    borderInput = element(by.id('field_border'));
    latestRecordedPopulationInput = element(by.id('field_latestRecordedPopulation'));
    validFromInput = element(by.id('field_validFrom'));
    validToInput = element(by.id('field_validTo'));
    countrySelect = element(by.id('field_country'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setStateProvinceCodeInput(stateProvinceCode) {
        await this.stateProvinceCodeInput.sendKeys(stateProvinceCode);
    }

    async getStateProvinceCodeInput() {
        return this.stateProvinceCodeInput.getAttribute('value');
    }

    async setStateProvinceNameInput(stateProvinceName) {
        await this.stateProvinceNameInput.sendKeys(stateProvinceName);
    }

    async getStateProvinceNameInput() {
        return this.stateProvinceNameInput.getAttribute('value');
    }

    async setSalesTerritoryInput(salesTerritory) {
        await this.salesTerritoryInput.sendKeys(salesTerritory);
    }

    async getSalesTerritoryInput() {
        return this.salesTerritoryInput.getAttribute('value');
    }

    async setBorderInput(border) {
        await this.borderInput.sendKeys(border);
    }

    async getBorderInput() {
        return this.borderInput.getAttribute('value');
    }

    async setLatestRecordedPopulationInput(latestRecordedPopulation) {
        await this.latestRecordedPopulationInput.sendKeys(latestRecordedPopulation);
    }

    async getLatestRecordedPopulationInput() {
        return this.latestRecordedPopulationInput.getAttribute('value');
    }

    async setValidFromInput(validFrom) {
        await this.validFromInput.sendKeys(validFrom);
    }

    async getValidFromInput() {
        return this.validFromInput.getAttribute('value');
    }

    async setValidToInput(validTo) {
        await this.validToInput.sendKeys(validTo);
    }

    async getValidToInput() {
        return this.validToInput.getAttribute('value');
    }

    async countrySelectLastOption() {
        await this.countrySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async countrySelectOption(option) {
        await this.countrySelect.sendKeys(option);
    }

    getCountrySelect(): ElementFinder {
        return this.countrySelect;
    }

    async getCountrySelectedOption() {
        return this.countrySelect.element(by.css('option:checked')).getText();
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

export class StateProvincesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-stateProvinces-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-stateProvinces'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
