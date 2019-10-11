import { element, by, ElementFinder } from 'protractor';

export class LastestMerchantUploadedDocumentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-lastest-merchant-uploaded-document div table .btn-danger'));
    title = element.all(by.css('jhi-lastest-merchant-uploaded-document div h2#page-heading span')).first();

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

export class LastestMerchantUploadedDocumentUpdatePage {
    pageTitle = element(by.id('jhi-lastest-merchant-uploaded-document-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    productCreateTemplateInput = element(by.id('file_productCreateTemplate'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setProductCreateTemplateInput(productCreateTemplate) {
        await this.productCreateTemplateInput.sendKeys(productCreateTemplate);
    }

    async getProductCreateTemplateInput() {
        return this.productCreateTemplateInput.getAttribute('value');
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

export class LastestMerchantUploadedDocumentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-lastestMerchantUploadedDocument-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-lastestMerchantUploadedDocument'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
