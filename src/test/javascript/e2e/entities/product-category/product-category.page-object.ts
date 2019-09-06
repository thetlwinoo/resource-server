import { element, by, ElementFinder } from 'protractor';

export class ProductCategoryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-category div table .btn-danger'));
    title = element.all(by.css('jhi-product-category div h2#page-heading span')).first();

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

export class ProductCategoryUpdatePage {
    pageTitle = element(by.id('jhi-product-category-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    productCategoryNameInput = element(by.id('field_productCategoryName'));
    photoInput = element(by.id('file_photo'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setProductCategoryNameInput(productCategoryName) {
        await this.productCategoryNameInput.sendKeys(productCategoryName);
    }

    async getProductCategoryNameInput() {
        return this.productCategoryNameInput.getAttribute('value');
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

export class ProductCategoryDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productCategory-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productCategory'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
