import { element, by, ElementFinder } from 'protractor';

export class ProductModelComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-model div table .btn-danger'));
    title = element.all(by.css('jhi-product-model div h2#page-heading span')).first();

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

export class ProductModelUpdatePage {
    pageTitle = element(by.id('jhi-product-model-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    productModelNameInput = element(by.id('field_productModelName'));
    calalogDescriptionInput = element(by.id('field_calalogDescription'));
    instructionsInput = element(by.id('field_instructions'));
    photoInput = element(by.id('file_photo'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setProductModelNameInput(productModelName) {
        await this.productModelNameInput.sendKeys(productModelName);
    }

    async getProductModelNameInput() {
        return this.productModelNameInput.getAttribute('value');
    }

    async setCalalogDescriptionInput(calalogDescription) {
        await this.calalogDescriptionInput.sendKeys(calalogDescription);
    }

    async getCalalogDescriptionInput() {
        return this.calalogDescriptionInput.getAttribute('value');
    }

    async setInstructionsInput(instructions) {
        await this.instructionsInput.sendKeys(instructions);
    }

    async getInstructionsInput() {
        return this.instructionsInput.getAttribute('value');
    }

    async setPhotoInput(photo) {
        await this.photoInput.sendKeys(photo);
    }

    async getPhotoInput() {
        return this.photoInput.getAttribute('value');
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

export class ProductModelDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productModel-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productModel'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
