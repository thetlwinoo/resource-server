import { element, by, ElementFinder } from 'protractor';

export class ProductInventoryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-inventory div table .btn-danger'));
    title = element.all(by.css('jhi-product-inventory div h2#page-heading span')).first();

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

export class ProductInventoryUpdatePage {
    pageTitle = element(by.id('jhi-product-inventory-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    shelfInput = element(by.id('field_shelf'));
    binInput = element(by.id('field_bin'));
    quantityInput = element(by.id('field_quantity'));
    productSelect = element(by.id('field_product'));
    locationSelect = element(by.id('field_location'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setShelfInput(shelf) {
        await this.shelfInput.sendKeys(shelf);
    }

    async getShelfInput() {
        return this.shelfInput.getAttribute('value');
    }

    async setBinInput(bin) {
        await this.binInput.sendKeys(bin);
    }

    async getBinInput() {
        return this.binInput.getAttribute('value');
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

    async locationSelectLastOption() {
        await this.locationSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async locationSelectOption(option) {
        await this.locationSelect.sendKeys(option);
    }

    getLocationSelect(): ElementFinder {
        return this.locationSelect;
    }

    async getLocationSelectedOption() {
        return this.locationSelect.element(by.css('option:checked')).getText();
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

export class ProductInventoryDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productInventory-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productInventory'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
