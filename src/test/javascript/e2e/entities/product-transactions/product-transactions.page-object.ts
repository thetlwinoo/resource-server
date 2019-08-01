import { element, by, ElementFinder } from 'protractor';

export class ProductTransactionsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-transactions div table .btn-danger'));
    title = element.all(by.css('jhi-product-transactions div h2#page-heading span')).first();

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

export class ProductTransactionsUpdatePage {
    pageTitle = element(by.id('jhi-product-transactions-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    transactionOccuredWhenInput = element(by.id('field_transactionOccuredWhen'));
    quantityInput = element(by.id('field_quantity'));
    productSelect = element(by.id('field_product'));
    customerSelect = element(by.id('field_customer'));
    invoiceSelect = element(by.id('field_invoice'));
    supplierSelect = element(by.id('field_supplier'));
    transactionTypeSelect = element(by.id('field_transactionType'));
    purchaseOrderSelect = element(by.id('field_purchaseOrder'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setTransactionOccuredWhenInput(transactionOccuredWhen) {
        await this.transactionOccuredWhenInput.sendKeys(transactionOccuredWhen);
    }

    async getTransactionOccuredWhenInput() {
        return this.transactionOccuredWhenInput.getAttribute('value');
    }

    async setQuantityInput(quantity) {
        await this.quantityInput.sendKeys(quantity);
    }

    async getQuantityInput() {
        return this.quantityInput.getAttribute('value');
    }

    async productSelectLastOption() {
        await this.productSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productSelectOption(option) {
        await this.productSelect.sendKeys(option);
    }

    getProductSelect(): ElementFinder {
        return this.productSelect;
    }

    async getProductSelectedOption() {
        return this.productSelect.element(by.css('option:checked')).getText();
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

    async invoiceSelectLastOption() {
        await this.invoiceSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async invoiceSelectOption(option) {
        await this.invoiceSelect.sendKeys(option);
    }

    getInvoiceSelect(): ElementFinder {
        return this.invoiceSelect;
    }

    async getInvoiceSelectedOption() {
        return this.invoiceSelect.element(by.css('option:checked')).getText();
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

export class ProductTransactionsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productTransactions-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productTransactions'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
