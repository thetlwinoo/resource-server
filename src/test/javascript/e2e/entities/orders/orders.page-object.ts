import { element, by, ElementFinder } from 'protractor';

export class OrdersComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-orders div table .btn-danger'));
    title = element.all(by.css('jhi-orders div h2#page-heading span')).first();

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

export class OrdersUpdatePage {
    pageTitle = element(by.id('jhi-orders-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    orderDateInput = element(by.id('field_orderDate'));
    dueDateInput = element(by.id('field_dueDate'));
    shipDateInput = element(by.id('field_shipDate'));
    paymentStatusInput = element(by.id('field_paymentStatus'));
    orderFlagInput = element(by.id('field_orderFlag'));
    orderNumberInput = element(by.id('field_orderNumber'));
    subTotalInput = element(by.id('field_subTotal'));
    taxAmountInput = element(by.id('field_taxAmount'));
    frieightInput = element(by.id('field_frieight'));
    totalDueInput = element(by.id('field_totalDue'));
    commentsInput = element(by.id('field_comments'));
    deliveryInstructionsInput = element(by.id('field_deliveryInstructions'));
    internalCommentsInput = element(by.id('field_internalComments'));
    pickingCompletedWhenInput = element(by.id('field_pickingCompletedWhen'));
    reviewSelect = element(by.id('field_review'));
    customerSelect = element(by.id('field_customer'));
    shipToAddressSelect = element(by.id('field_shipToAddress'));
    billToAddressSelect = element(by.id('field_billToAddress'));
    shipMethodSelect = element(by.id('field_shipMethod'));
    currencyRateSelect = element(by.id('field_currencyRate'));
    specialDealsSelect = element(by.id('field_specialDeals'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setOrderDateInput(orderDate) {
        await this.orderDateInput.sendKeys(orderDate);
    }

    async getOrderDateInput() {
        return this.orderDateInput.getAttribute('value');
    }

    async setDueDateInput(dueDate) {
        await this.dueDateInput.sendKeys(dueDate);
    }

    async getDueDateInput() {
        return this.dueDateInput.getAttribute('value');
    }

    async setShipDateInput(shipDate) {
        await this.shipDateInput.sendKeys(shipDate);
    }

    async getShipDateInput() {
        return this.shipDateInput.getAttribute('value');
    }

    async setPaymentStatusInput(paymentStatus) {
        await this.paymentStatusInput.sendKeys(paymentStatus);
    }

    async getPaymentStatusInput() {
        return this.paymentStatusInput.getAttribute('value');
    }

    async setOrderFlagInput(orderFlag) {
        await this.orderFlagInput.sendKeys(orderFlag);
    }

    async getOrderFlagInput() {
        return this.orderFlagInput.getAttribute('value');
    }

    async setOrderNumberInput(orderNumber) {
        await this.orderNumberInput.sendKeys(orderNumber);
    }

    async getOrderNumberInput() {
        return this.orderNumberInput.getAttribute('value');
    }

    async setSubTotalInput(subTotal) {
        await this.subTotalInput.sendKeys(subTotal);
    }

    async getSubTotalInput() {
        return this.subTotalInput.getAttribute('value');
    }

    async setTaxAmountInput(taxAmount) {
        await this.taxAmountInput.sendKeys(taxAmount);
    }

    async getTaxAmountInput() {
        return this.taxAmountInput.getAttribute('value');
    }

    async setFrieightInput(frieight) {
        await this.frieightInput.sendKeys(frieight);
    }

    async getFrieightInput() {
        return this.frieightInput.getAttribute('value');
    }

    async setTotalDueInput(totalDue) {
        await this.totalDueInput.sendKeys(totalDue);
    }

    async getTotalDueInput() {
        return this.totalDueInput.getAttribute('value');
    }

    async setCommentsInput(comments) {
        await this.commentsInput.sendKeys(comments);
    }

    async getCommentsInput() {
        return this.commentsInput.getAttribute('value');
    }

    async setDeliveryInstructionsInput(deliveryInstructions) {
        await this.deliveryInstructionsInput.sendKeys(deliveryInstructions);
    }

    async getDeliveryInstructionsInput() {
        return this.deliveryInstructionsInput.getAttribute('value');
    }

    async setInternalCommentsInput(internalComments) {
        await this.internalCommentsInput.sendKeys(internalComments);
    }

    async getInternalCommentsInput() {
        return this.internalCommentsInput.getAttribute('value');
    }

    async setPickingCompletedWhenInput(pickingCompletedWhen) {
        await this.pickingCompletedWhenInput.sendKeys(pickingCompletedWhen);
    }

    async getPickingCompletedWhenInput() {
        return this.pickingCompletedWhenInput.getAttribute('value');
    }

    async reviewSelectLastOption() {
        await this.reviewSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async reviewSelectOption(option) {
        await this.reviewSelect.sendKeys(option);
    }

    getReviewSelect(): ElementFinder {
        return this.reviewSelect;
    }

    async getReviewSelectedOption() {
        return this.reviewSelect.element(by.css('option:checked')).getText();
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

    async shipToAddressSelectLastOption() {
        await this.shipToAddressSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async shipToAddressSelectOption(option) {
        await this.shipToAddressSelect.sendKeys(option);
    }

    getShipToAddressSelect(): ElementFinder {
        return this.shipToAddressSelect;
    }

    async getShipToAddressSelectedOption() {
        return this.shipToAddressSelect.element(by.css('option:checked')).getText();
    }

    async billToAddressSelectLastOption() {
        await this.billToAddressSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async billToAddressSelectOption(option) {
        await this.billToAddressSelect.sendKeys(option);
    }

    getBillToAddressSelect(): ElementFinder {
        return this.billToAddressSelect;
    }

    async getBillToAddressSelectedOption() {
        return this.billToAddressSelect.element(by.css('option:checked')).getText();
    }

    async shipMethodSelectLastOption() {
        await this.shipMethodSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async shipMethodSelectOption(option) {
        await this.shipMethodSelect.sendKeys(option);
    }

    getShipMethodSelect(): ElementFinder {
        return this.shipMethodSelect;
    }

    async getShipMethodSelectedOption() {
        return this.shipMethodSelect.element(by.css('option:checked')).getText();
    }

    async currencyRateSelectLastOption() {
        await this.currencyRateSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async currencyRateSelectOption(option) {
        await this.currencyRateSelect.sendKeys(option);
    }

    getCurrencyRateSelect(): ElementFinder {
        return this.currencyRateSelect;
    }

    async getCurrencyRateSelectedOption() {
        return this.currencyRateSelect.element(by.css('option:checked')).getText();
    }

    async specialDealsSelectLastOption() {
        await this.specialDealsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async specialDealsSelectOption(option) {
        await this.specialDealsSelect.sendKeys(option);
    }

    getSpecialDealsSelect(): ElementFinder {
        return this.specialDealsSelect;
    }

    async getSpecialDealsSelectedOption() {
        return this.specialDealsSelect.element(by.css('option:checked')).getText();
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

export class OrdersDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-orders-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-orders'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
