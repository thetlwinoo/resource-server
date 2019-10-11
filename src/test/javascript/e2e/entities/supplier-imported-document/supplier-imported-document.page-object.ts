import { element, by, ElementFinder } from 'protractor';

export class SupplierImportedDocumentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-supplier-imported-document div table .btn-danger'));
    title = element.all(by.css('jhi-supplier-imported-document div h2#page-heading span')).first();

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

export class SupplierImportedDocumentUpdatePage {
    pageTitle = element(by.id('jhi-supplier-imported-document-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    importedTemplateInput = element(by.id('file_importedTemplate'));
    importedFailedTemplateInput = element(by.id('file_importedFailedTemplate'));
    uploadTransactionSelect = element(by.id('field_uploadTransaction'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setImportedTemplateInput(importedTemplate) {
        await this.importedTemplateInput.sendKeys(importedTemplate);
    }

    async getImportedTemplateInput() {
        return this.importedTemplateInput.getAttribute('value');
    }

    async setImportedFailedTemplateInput(importedFailedTemplate) {
        await this.importedFailedTemplateInput.sendKeys(importedFailedTemplate);
    }

    async getImportedFailedTemplateInput() {
        return this.importedFailedTemplateInput.getAttribute('value');
    }

    async uploadTransactionSelectLastOption() {
        await this.uploadTransactionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async uploadTransactionSelectOption(option) {
        await this.uploadTransactionSelect.sendKeys(option);
    }

    getUploadTransactionSelect(): ElementFinder {
        return this.uploadTransactionSelect;
    }

    async getUploadTransactionSelectedOption() {
        return this.uploadTransactionSelect.element(by.css('option:checked')).getText();
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

export class SupplierImportedDocumentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-supplierImportedDocument-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-supplierImportedDocument'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
