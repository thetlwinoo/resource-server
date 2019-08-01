import { element, by, ElementFinder } from 'protractor';

export class ProductPhotoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-photo div table .btn-danger'));
    title = element.all(by.css('jhi-product-photo div h2#page-heading span')).first();

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

export class ProductPhotoUpdatePage {
    pageTitle = element(by.id('jhi-product-photo-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    thumbnailPhotoInput = element(by.id('field_thumbnailPhoto'));
    originalPhotoInput = element(by.id('field_originalPhoto'));
    bannerTallPhotoInput = element(by.id('field_bannerTallPhoto'));
    bannerWidePhotoInput = element(by.id('field_bannerWidePhoto'));
    circlePhotoInput = element(by.id('field_circlePhoto'));
    sharpenedPhotoInput = element(by.id('field_sharpenedPhoto'));
    squarePhotoInput = element(by.id('field_squarePhoto'));
    watermarkPhotoInput = element(by.id('field_watermarkPhoto'));
    priorityInput = element(by.id('field_priority'));
    defaultIndInput = element(by.id('field_defaultInd'));
    deleteTokenInput = element(by.id('field_deleteToken'));
    productSelect = element(by.id('field_product'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setThumbnailPhotoInput(thumbnailPhoto) {
        await this.thumbnailPhotoInput.sendKeys(thumbnailPhoto);
    }

    async getThumbnailPhotoInput() {
        return this.thumbnailPhotoInput.getAttribute('value');
    }

    async setOriginalPhotoInput(originalPhoto) {
        await this.originalPhotoInput.sendKeys(originalPhoto);
    }

    async getOriginalPhotoInput() {
        return this.originalPhotoInput.getAttribute('value');
    }

    async setBannerTallPhotoInput(bannerTallPhoto) {
        await this.bannerTallPhotoInput.sendKeys(bannerTallPhoto);
    }

    async getBannerTallPhotoInput() {
        return this.bannerTallPhotoInput.getAttribute('value');
    }

    async setBannerWidePhotoInput(bannerWidePhoto) {
        await this.bannerWidePhotoInput.sendKeys(bannerWidePhoto);
    }

    async getBannerWidePhotoInput() {
        return this.bannerWidePhotoInput.getAttribute('value');
    }

    async setCirclePhotoInput(circlePhoto) {
        await this.circlePhotoInput.sendKeys(circlePhoto);
    }

    async getCirclePhotoInput() {
        return this.circlePhotoInput.getAttribute('value');
    }

    async setSharpenedPhotoInput(sharpenedPhoto) {
        await this.sharpenedPhotoInput.sendKeys(sharpenedPhoto);
    }

    async getSharpenedPhotoInput() {
        return this.sharpenedPhotoInput.getAttribute('value');
    }

    async setSquarePhotoInput(squarePhoto) {
        await this.squarePhotoInput.sendKeys(squarePhoto);
    }

    async getSquarePhotoInput() {
        return this.squarePhotoInput.getAttribute('value');
    }

    async setWatermarkPhotoInput(watermarkPhoto) {
        await this.watermarkPhotoInput.sendKeys(watermarkPhoto);
    }

    async getWatermarkPhotoInput() {
        return this.watermarkPhotoInput.getAttribute('value');
    }

    async setPriorityInput(priority) {
        await this.priorityInput.sendKeys(priority);
    }

    async getPriorityInput() {
        return this.priorityInput.getAttribute('value');
    }

    getDefaultIndInput() {
        return this.defaultIndInput;
    }
    async setDeleteTokenInput(deleteToken) {
        await this.deleteTokenInput.sendKeys(deleteToken);
    }

    async getDeleteTokenInput() {
        return this.deleteTokenInput.getAttribute('value');
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

export class ProductPhotoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productPhoto-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productPhoto'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
