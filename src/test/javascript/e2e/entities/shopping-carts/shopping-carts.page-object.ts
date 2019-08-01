import { element, by, ElementFinder } from 'protractor';

export class ShoppingCartsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-shopping-carts div table .btn-danger'));
    title = element.all(by.css('jhi-shopping-carts div h2#page-heading span')).first();

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

export class ShoppingCartsUpdatePage {
    pageTitle = element(by.id('jhi-shopping-carts-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    totalPriceInput = element(by.id('field_totalPrice'));
    totalCargoPriceInput = element(by.id('field_totalCargoPrice'));
    specialDealsSelect = element(by.id('field_specialDeals'));
    cartUserSelect = element(by.id('field_cartUser'));
    customerSelect = element(by.id('field_customer'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setTotalPriceInput(totalPrice) {
        await this.totalPriceInput.sendKeys(totalPrice);
    }

    async getTotalPriceInput() {
        return this.totalPriceInput.getAttribute('value');
    }

    async setTotalCargoPriceInput(totalCargoPrice) {
        await this.totalCargoPriceInput.sendKeys(totalCargoPrice);
    }

    async getTotalCargoPriceInput() {
        return this.totalCargoPriceInput.getAttribute('value');
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

    async cartUserSelectLastOption() {
        await this.cartUserSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async cartUserSelectOption(option) {
        await this.cartUserSelect.sendKeys(option);
    }

    getCartUserSelect(): ElementFinder {
        return this.cartUserSelect;
    }

    async getCartUserSelectedOption() {
        return this.cartUserSelect.element(by.css('option:checked')).getText();
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

export class ShoppingCartsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-shoppingCarts-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-shoppingCarts'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
