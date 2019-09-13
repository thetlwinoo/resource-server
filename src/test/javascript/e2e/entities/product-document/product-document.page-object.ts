import { element, by, ElementFinder } from 'protractor';

export class ProductDocumentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-document div table .btn-danger'));
    title = element.all(by.css('jhi-product-document div h2#page-heading span')).first();

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

export class ProductDocumentUpdatePage {
    pageTitle = element(by.id('jhi-product-document-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    documentNodeInput = element(by.id('file_documentNode'));
    videoUrlInput = element(by.id('field_videoUrl'));
    highlightsInput = element(by.id('field_highlights'));
    productSelect = element(by.id('field_product'));
    cultureSelect = element(by.id('field_culture'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDocumentNodeInput(documentNode) {
        await this.documentNodeInput.sendKeys(documentNode);
    }

    async getDocumentNodeInput() {
        return this.documentNodeInput.getAttribute('value');
    }

    async setVideoUrlInput(videoUrl) {
        await this.videoUrlInput.sendKeys(videoUrl);
    }

    async getVideoUrlInput() {
        return this.videoUrlInput.getAttribute('value');
    }

    async setHighlightsInput(highlights) {
        await this.highlightsInput.sendKeys(highlights);
    }

    async getHighlightsInput() {
        return this.highlightsInput.getAttribute('value');
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

    async cultureSelectLastOption() {
        await this.cultureSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async cultureSelectOption(option) {
        await this.cultureSelect.sendKeys(option);
    }

    getCultureSelect(): ElementFinder {
        return this.cultureSelect;
    }

    async getCultureSelectedOption() {
        return this.cultureSelect.element(by.css('option:checked')).getText();
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

export class ProductDocumentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productDocument-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productDocument'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
