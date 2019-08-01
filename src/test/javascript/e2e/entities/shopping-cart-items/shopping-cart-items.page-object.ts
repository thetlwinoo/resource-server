import { element, by, ElementFinder } from 'protractor';

export class ShoppingCartItemsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-shopping-cart-items div table .btn-danger'));
    title = element.all(by.css('jhi-shopping-cart-items div h2#page-heading span')).first();

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

export class ShoppingCartItemsUpdatePage {
    pageTitle = element(by.id('jhi-shopping-cart-items-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    quantityInput = element(by.id('field_quantity'));
    productSelect = element(by.id('field_product'));
    cartSelect = element(by.id('field_cart'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
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

    async cartSelectLastOption() {
        await this.cartSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async cartSelectOption(option) {
        await this.cartSelect.sendKeys(option);
    }

    getCartSelect(): ElementFinder {
        return this.cartSelect;
    }

    async getCartSelectedOption() {
        return this.cartSelect.element(by.css('option:checked')).getText();
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

export class ShoppingCartItemsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-shoppingCartItems-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-shoppingCartItems'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
