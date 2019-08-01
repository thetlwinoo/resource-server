import { element, by, ElementFinder } from 'protractor';

export class SupplierTransactionsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-supplier-transactions div table .btn-danger'));
    title = element.all(by.css('jhi-supplier-transactions div h2#page-heading span')).first();

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

export class SupplierTransactionsUpdatePage {
    pageTitle = element(by.id('jhi-supplier-transactions-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    supplierInvoiceNumberInput = element(by.id('field_supplierInvoiceNumber'));
    transactionDateInput = element(by.id('field_transactionDate'));
    amountExcludingTaxInput = element(by.id('field_amountExcludingTax'));
    taxAmountInput = element(by.id('field_taxAmount'));
    transactionAmountInput = element(by.id('field_transactionAmount'));
    outstandingBalanceInput = element(by.id('field_outstandingBalance'));
    finalizationDateInput = element(by.id('field_finalizationDate'));
    isFinalizedInput = element(by.id('field_isFinalized'));
    supplierSelect = element(by.id('field_supplier'));
    transactionTypeSelect = element(by.id('field_transactionType'));
    purchaseOrderSelect = element(by.id('field_purchaseOrder'));
    paymentMethodSelect = element(by.id('field_paymentMethod'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setSupplierInvoiceNumberInput(supplierInvoiceNumber) {
        await this.supplierInvoiceNumberInput.sendKeys(supplierInvoiceNumber);
    }

    async getSupplierInvoiceNumberInput() {
        return this.supplierInvoiceNumberInput.getAttribute('value');
    }

    async setTransactionDateInput(transactionDate) {
        await this.transactionDateInput.sendKeys(transactionDate);
    }

    async getTransactionDateInput() {
        return this.transactionDateInput.getAttribute('value');
    }

    async setAmountExcludingTaxInput(amountExcludingTax) {
        await this.amountExcludingTaxInput.sendKeys(amountExcludingTax);
    }

    async getAmountExcludingTaxInput() {
        return this.amountExcludingTaxInput.getAttribute('value');
    }

    async setTaxAmountInput(taxAmount) {
        await this.taxAmountInput.sendKeys(taxAmount);
    }

    async getTaxAmountInput() {
        return this.taxAmountInput.getAttribute('value');
    }

    async setTransactionAmountInput(transactionAmount) {
        await this.transactionAmountInput.sendKeys(transactionAmount);
    }

    async getTransactionAmountInput() {
        return this.transactionAmountInput.getAttribute('value');
    }

    async setOutstandingBalanceInput(outstandingBalance) {
        await this.outstandingBalanceInput.sendKeys(outstandingBalance);
    }

    async getOutstandingBalanceInput() {
        return this.outstandingBalanceInput.getAttribute('value');
    }

    async setFinalizationDateInput(finalizationDate) {
        await this.finalizationDateInput.sendKeys(finalizationDate);
    }

    async getFinalizationDateInput() {
        return this.finalizationDateInput.getAttribute('value');
    }

    getIsFinalizedInput() {
        return this.isFinalizedInput;
    }

    async supplierSelectLastOption() {
        await this.supplierSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async supplierSelectOption(option) {
        await this.supplierSelect.sendKeys(option);
    }

    getSupplierSelect(): ElementFinder {
        return this.supplierSelect;
    }

    async getSupplierSelectedOption() {
        return this.supplierSelect.element(by.css('option:checked')).getText();
    }

    async transactionTypeSelectLastOption() {
        await this.transactionTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async transactionTypeSelectOption(option) {
        await this.transactionTypeSelect.sendKeys(option);
    }

    getTransactionTypeSelect(): ElementFinder {
        return this.transactionTypeSelect;
    }

    async getTransactionTypeSelectedOption() {
        return this.transactionTypeSelect.element(by.css('option:checked')).getText();
    }

    async purchaseOrderSelectLastOption() {
        await this.purchaseOrderSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async purchaseOrderSelectOption(option) {
        await this.purchaseOrderSelect.sendKeys(option);
    }

    getPurchaseOrderSelect(): ElementFinder {
        return this.purchaseOrderSelect;
    }

    async getPurchaseOrderSelectedOption() {
        return this.purchaseOrderSelect.element(by.css('option:checked')).getText();
    }

    async paymentMethodSelectLastOption() {
        await this.paymentMethodSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async paymentMethodSelectOption(option) {
        await this.paymentMethodSelect.sendKeys(option);
    }

    getPaymentMethodSelect(): ElementFinder {
        return this.paymentMethodSelect;
    }

    async getPaymentMethodSelectedOption() {
        return this.paymentMethodSelect.element(by.css('option:checked')).getText();
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

export class SupplierTransactionsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-supplierTransactions-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-supplierTransactions'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
