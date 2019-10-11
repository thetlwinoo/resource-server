import { element, by, ElementFinder } from 'protractor';

export class SuppliersComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-suppliers div table .btn-danger'));
    title = element.all(by.css('jhi-suppliers div h2#page-heading span')).first();

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

export class SuppliersUpdatePage {
    pageTitle = element(by.id('jhi-suppliers-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    supplierNameInput = element(by.id('field_supplierName'));
    supplierReferenceInput = element(by.id('field_supplierReference'));
    bankAccountNameInput = element(by.id('field_bankAccountName'));
    bankAccountBranchInput = element(by.id('field_bankAccountBranch'));
    bankAccountCodeInput = element(by.id('field_bankAccountCode'));
    bankAccountNumberInput = element(by.id('field_bankAccountNumber'));
    bankInternationalCodeInput = element(by.id('field_bankInternationalCode'));
    paymentDaysInput = element(by.id('field_paymentDays'));
    internalCommentsInput = element(by.id('field_internalComments'));
    phoneNumberInput = element(by.id('field_phoneNumber'));
    faxNumberInput = element(by.id('field_faxNumber'));
    websiteURLInput = element(by.id('field_websiteURL'));
    webServiceUrlInput = element(by.id('field_webServiceUrl'));
    creditRatingInput = element(by.id('field_creditRating'));
    activeFlagInput = element(by.id('field_activeFlag'));
    avatarInput = element(by.id('file_avatar'));
    validFromInput = element(by.id('field_validFrom'));
    validToInput = element(by.id('field_validTo'));
    primaryContactPersonSelect = element(by.id('field_primaryContactPerson'));
    alternateContactPersonSelect = element(by.id('field_alternateContactPerson'));
    supplierCategorySelect = element(by.id('field_supplierCategory'));
    deliveryMethodSelect = element(by.id('field_deliveryMethod'));
    deliveryCitySelect = element(by.id('field_deliveryCity'));
    postalCitySelect = element(by.id('field_postalCity'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setSupplierNameInput(supplierName) {
        await this.supplierNameInput.sendKeys(supplierName);
    }

    async getSupplierNameInput() {
        return this.supplierNameInput.getAttribute('value');
    }

    async setSupplierReferenceInput(supplierReference) {
        await this.supplierReferenceInput.sendKeys(supplierReference);
    }

    async getSupplierReferenceInput() {
        return this.supplierReferenceInput.getAttribute('value');
    }

    async setBankAccountNameInput(bankAccountName) {
        await this.bankAccountNameInput.sendKeys(bankAccountName);
    }

    async getBankAccountNameInput() {
        return this.bankAccountNameInput.getAttribute('value');
    }

    async setBankAccountBranchInput(bankAccountBranch) {
        await this.bankAccountBranchInput.sendKeys(bankAccountBranch);
    }

    async getBankAccountBranchInput() {
        return this.bankAccountBranchInput.getAttribute('value');
    }

    async setBankAccountCodeInput(bankAccountCode) {
        await this.bankAccountCodeInput.sendKeys(bankAccountCode);
    }

    async getBankAccountCodeInput() {
        return this.bankAccountCodeInput.getAttribute('value');
    }

    async setBankAccountNumberInput(bankAccountNumber) {
        await this.bankAccountNumberInput.sendKeys(bankAccountNumber);
    }

    async getBankAccountNumberInput() {
        return this.bankAccountNumberInput.getAttribute('value');
    }

    async setBankInternationalCodeInput(bankInternationalCode) {
        await this.bankInternationalCodeInput.sendKeys(bankInternationalCode);
    }

    async getBankInternationalCodeInput() {
        return this.bankInternationalCodeInput.getAttribute('value');
    }

    async setPaymentDaysInput(paymentDays) {
        await this.paymentDaysInput.sendKeys(paymentDays);
    }

    async getPaymentDaysInput() {
        return this.paymentDaysInput.getAttribute('value');
    }

    async setInternalCommentsInput(internalComments) {
        await this.internalCommentsInput.sendKeys(internalComments);
    }

    async getInternalCommentsInput() {
        return this.internalCommentsInput.getAttribute('value');
    }

    async setPhoneNumberInput(phoneNumber) {
        await this.phoneNumberInput.sendKeys(phoneNumber);
    }

    async getPhoneNumberInput() {
        return this.phoneNumberInput.getAttribute('value');
    }

    async setFaxNumberInput(faxNumber) {
        await this.faxNumberInput.sendKeys(faxNumber);
    }

    async getFaxNumberInput() {
        return this.faxNumberInput.getAttribute('value');
    }

    async setWebsiteURLInput(websiteURL) {
        await this.websiteURLInput.sendKeys(websiteURL);
    }

    async getWebsiteURLInput() {
        return this.websiteURLInput.getAttribute('value');
    }

    async setWebServiceUrlInput(webServiceUrl) {
        await this.webServiceUrlInput.sendKeys(webServiceUrl);
    }

    async getWebServiceUrlInput() {
        return this.webServiceUrlInput.getAttribute('value');
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
    async setAvatarInput(avatar) {
        await this.avatarInput.sendKeys(avatar);
    }

    async getAvatarInput() {
        return this.avatarInput.getAttribute('value');
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

    async primaryContactPersonSelectLastOption() {
        await this.primaryContactPersonSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async primaryContactPersonSelectOption(option) {
        await this.primaryContactPersonSelect.sendKeys(option);
    }

    getPrimaryContactPersonSelect(): ElementFinder {
        return this.primaryContactPersonSelect;
    }

    async getPrimaryContactPersonSelectedOption() {
        return this.primaryContactPersonSelect.element(by.css('option:checked')).getText();
    }

    async alternateContactPersonSelectLastOption() {
        await this.alternateContactPersonSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async alternateContactPersonSelectOption(option) {
        await this.alternateContactPersonSelect.sendKeys(option);
    }

    getAlternateContactPersonSelect(): ElementFinder {
        return this.alternateContactPersonSelect;
    }

    async getAlternateContactPersonSelectedOption() {
        return this.alternateContactPersonSelect.element(by.css('option:checked')).getText();
    }

    async supplierCategorySelectLastOption() {
        await this.supplierCategorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async supplierCategorySelectOption(option) {
        await this.supplierCategorySelect.sendKeys(option);
    }

    getSupplierCategorySelect(): ElementFinder {
        return this.supplierCategorySelect;
    }

    async getSupplierCategorySelectedOption() {
        return this.supplierCategorySelect.element(by.css('option:checked')).getText();
    }

    async deliveryMethodSelectLastOption() {
        await this.deliveryMethodSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async deliveryMethodSelectOption(option) {
        await this.deliveryMethodSelect.sendKeys(option);
    }

    getDeliveryMethodSelect(): ElementFinder {
        return this.deliveryMethodSelect;
    }

    async getDeliveryMethodSelectedOption() {
        return this.deliveryMethodSelect.element(by.css('option:checked')).getText();
    }

    async deliveryCitySelectLastOption() {
        await this.deliveryCitySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async deliveryCitySelectOption(option) {
        await this.deliveryCitySelect.sendKeys(option);
    }

    getDeliveryCitySelect(): ElementFinder {
        return this.deliveryCitySelect;
    }

    async getDeliveryCitySelectedOption() {
        return this.deliveryCitySelect.element(by.css('option:checked')).getText();
    }

    async postalCitySelectLastOption() {
        await this.postalCitySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async postalCitySelectOption(option) {
        await this.postalCitySelect.sendKeys(option);
    }

    getPostalCitySelect(): ElementFinder {
        return this.postalCitySelect;
    }

    async getPostalCitySelectedOption() {
        return this.postalCitySelect.element(by.css('option:checked')).getText();
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

export class SuppliersDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-suppliers-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-suppliers'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
