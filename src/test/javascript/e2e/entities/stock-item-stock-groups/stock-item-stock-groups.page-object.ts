import { element, by, ElementFinder } from 'protractor';

export class StockItemStockGroupsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-stock-item-stock-groups div table .btn-danger'));
    title = element.all(by.css('jhi-stock-item-stock-groups div h2#page-heading span')).first();

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

export class StockItemStockGroupsUpdatePage {
    pageTitle = element(by.id('jhi-stock-item-stock-groups-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    stockGroupSelect = element(by.id('field_stockGroup'));
    productSelect = element(by.id('field_product'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async stockGroupSelectLastOption() {
        await this.stockGroupSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async stockGroupSelectOption(option) {
        await this.stockGroupSelect.sendKeys(option);
    }

    getStockGroupSelect(): ElementFinder {
        return this.stockGroupSelect;
    }

    async getStockGroupSelectedOption() {
        return this.stockGroupSelect.element(by.css('option:checked')).getText();
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

export class StockItemStockGroupsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-stockItemStockGroups-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-stockItemStockGroups'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
