import { element, by, ElementFinder } from 'protractor';

export class SpecialDealsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-special-deals div table .btn-danger'));
    title = element.all(by.css('jhi-special-deals div h2#page-heading span')).first();

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

export class SpecialDealsUpdatePage {
    pageTitle = element(by.id('jhi-special-deals-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dealDescriptionInput = element(by.id('field_dealDescription'));
    startDateInput = element(by.id('field_startDate'));
    endDateInput = element(by.id('field_endDate'));
    discountAmountInput = element(by.id('field_discountAmount'));
    discountPercentageInput = element(by.id('field_discountPercentage'));
    discountCodeInput = element(by.id('field_discountCode'));
    unitPriceInput = element(by.id('field_unitPrice'));
    buyingGroupSelect = element(by.id('field_buyingGroup'));
    customerCategorySelect = element(by.id('field_customerCategory'));
    customerSelect = element(by.id('field_customer'));
    productCategorySelect = element(by.id('field_productCategory'));
    stockItemSelect = element(by.id('field_stockItem'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDealDescriptionInput(dealDescription) {
        await this.dealDescriptionInput.sendKeys(dealDescription);
    }

    async getDealDescriptionInput() {
        return this.dealDescriptionInput.getAttribute('value');
    }

    async setStartDateInput(startDate) {
        await this.startDateInput.sendKeys(startDate);
    }

    async getStartDateInput() {
        return this.startDateInput.getAttribute('value');
    }

    async setEndDateInput(endDate) {
        await this.endDateInput.sendKeys(endDate);
    }

    async getEndDateInput() {
        return this.endDateInput.getAttribute('value');
    }

    async setDiscountAmountInput(discountAmount) {
        await this.discountAmountInput.sendKeys(discountAmount);
    }

    async getDiscountAmountInput() {
        return this.discountAmountInput.getAttribute('value');
    }

    async setDiscountPercentageInput(discountPercentage) {
        await this.discountPercentageInput.sendKeys(discountPercentage);
    }

    async getDiscountPercentageInput() {
        return this.discountPercentageInput.getAttribute('value');
    }

    async setDiscountCodeInput(discountCode) {
        await this.discountCodeInput.sendKeys(discountCode);
    }

    async getDiscountCodeInput() {
        return this.discountCodeInput.getAttribute('value');
    }

    async setUnitPriceInput(unitPrice) {
        await this.unitPriceInput.sendKeys(unitPrice);
    }

    async getUnitPriceInput() {
        return this.unitPriceInput.getAttribute('value');
    }

    async buyingGroupSelectLastOption() {
        await this.buyingGroupSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async buyingGroupSelectOption(option) {
        await this.buyingGroupSelect.sendKeys(option);
    }

    getBuyingGroupSelect(): ElementFinder {
        return this.buyingGroupSelect;
    }

    async getBuyingGroupSelectedOption() {
        return this.buyingGroupSelect.element(by.css('option:checked')).getText();
    }

    async customerCategorySelectLastOption() {
        await this.customerCategorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async customerCategorySelectOption(option) {
        await this.customerCategorySelect.sendKeys(option);
    }

    getCustomerCategorySelect(): ElementFinder {
        return this.customerCategorySelect;
    }

    async getCustomerCategorySelectedOption() {
        return this.customerCategorySelect.element(by.css('option:checked')).getText();
    }

    async customerSelectLastOption() {
        await this.customerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async customerSelectOption(option) {
        await this.customerSelect.sendKeys(option);
    }

    getCustomerSelect(): ElementFinder {
        return this.customerSelect;
    }

    async getCustomerSelectedOption() {
        return this.customerSelect.element(by.css('option:checked')).getText();
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

    async stockItemSelectLastOption() {
        await this.stockItemSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async stockItemSelectOption(option) {
        await this.stockItemSelect.sendKeys(option);
    }

    getStockItemSelect(): ElementFinder {
        return this.stockItemSelect;
    }

    async getStockItemSelectedOption() {
        return this.stockItemSelect.element(by.css('option:checked')).getText();
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

export class SpecialDealsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-specialDeals-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-specialDeals'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
