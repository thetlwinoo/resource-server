import { element, by, ElementFinder } from 'protractor';

export class MerchantsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-merchants div table .btn-danger'));
    title = element.all(by.css('jhi-merchants div h2#page-heading span')).first();

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

export class MerchantsUpdatePage {
    pageTitle = element(by.id('jhi-merchants-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    accountNumberInput = element(by.id('field_accountNumber'));
    merchantNameInput = element(by.id('field_merchantName'));
    creditRatingInput = element(by.id('field_creditRating'));
    activeFlagInput = element(by.id('field_activeFlag'));
    webServiceUrlInput = element(by.id('field_webServiceUrl'));
    webSiteUrlInput = element(by.id('field_webSiteUrl'));
    avatarInput = element(by.id('file_avatar'));
    personSelect = element(by.id('field_person'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setAccountNumberInput(accountNumber) {
        await this.accountNumberInput.sendKeys(accountNumber);
    }

    async getAccountNumberInput() {
        return this.accountNumberInput.getAttribute('value');
    }

    async setMerchantNameInput(merchantName) {
        await this.merchantNameInput.sendKeys(merchantName);
    }

    async getMerchantNameInput() {
        return this.merchantNameInput.getAttribute('value');
    }

    async setCreditRatingInput(creditRating) {
        await this.creditRatingInput.sendKeys(creditRating);
    }

    async getCreditRatingInput() {
        return this.creditRatingInput.getAttribute('value');
    }

    getActiveFlagInput() {
        return this.activeFlagInput;
    }
    async setWebServiceUrlInput(webServiceUrl) {
        await this.webServiceUrlInput.sendKeys(webServiceUrl);
    }

    async getWebServiceUrlInput() {
        return this.webServiceUrlInput.getAttribute('value');
    }

    async setWebSiteUrlInput(webSiteUrl) {
        await this.webSiteUrlInput.sendKeys(webSiteUrl);
    }

    async getWebSiteUrlInput() {
        return this.webSiteUrlInput.getAttribute('value');
    }

    async setAvatarInput(avatar) {
        await this.avatarInput.sendKeys(avatar);
    }

    async getAvatarInput() {
        return this.avatarInput.getAttribute('value');
    }

    async personSelectLastOption() {
        await this.personSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async personSelectOption(option) {
        await this.personSelect.sendKeys(option);
    }

    getPersonSelect(): ElementFinder {
        return this.personSelect;
    }

    async getPersonSelectedOption() {
        return this.personSelect.element(by.css('option:checked')).getText();
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

export class MerchantsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-merchants-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-merchants'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
