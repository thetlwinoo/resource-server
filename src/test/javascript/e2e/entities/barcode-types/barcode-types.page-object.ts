import { element, by, ElementFinder } from 'protractor';

export class BarcodeTypesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-barcode-types div table .btn-danger'));
    title = element.all(by.css('jhi-barcode-types div h2#page-heading span')).first();

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

export class BarcodeTypesUpdatePage {
    pageTitle = element(by.id('jhi-barcode-types-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    barcodeTypeNameInput = element(by.id('field_barcodeTypeName'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setBarcodeTypeNameInput(barcodeTypeName) {
        await this.barcodeTypeNameInput.sendKeys(barcodeTypeName);
    }

    async getBarcodeTypeNameInput() {
        return this.barcodeTypeNameInput.getAttribute('value');
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

export class BarcodeTypesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-barcodeTypes-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-barcodeTypes'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
