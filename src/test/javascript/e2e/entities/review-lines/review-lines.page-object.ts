import { element, by, ElementFinder } from 'protractor';

export class ReviewLinesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-review-lines div table .btn-danger'));
    title = element.all(by.css('jhi-review-lines div h2#page-heading span')).first();

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

export class ReviewLinesUpdatePage {
    pageTitle = element(by.id('jhi-review-lines-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    productRatingInput = element(by.id('field_productRating'));
    productReviewInput = element(by.id('field_productReview'));
    sellerRatingInput = element(by.id('field_sellerRating'));
    sellerReviewInput = element(by.id('field_sellerReview'));
    deliveryRatingInput = element(by.id('field_deliveryRating'));
    deliveryReviewInput = element(by.id('field_deliveryReview'));
    photoInput = element(by.id('file_photo'));
    productSelect = element(by.id('field_product'));
    reviewSelect = element(by.id('field_review'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setProductRatingInput(productRating) {
        await this.productRatingInput.sendKeys(productRating);
    }

    async getProductRatingInput() {
        return this.productRatingInput.getAttribute('value');
    }

    async setProductReviewInput(productReview) {
        await this.productReviewInput.sendKeys(productReview);
    }

    async getProductReviewInput() {
        return this.productReviewInput.getAttribute('value');
    }

    async setSellerRatingInput(sellerRating) {
        await this.sellerRatingInput.sendKeys(sellerRating);
    }

    async getSellerRatingInput() {
        return this.sellerRatingInput.getAttribute('value');
    }

    async setSellerReviewInput(sellerReview) {
        await this.sellerReviewInput.sendKeys(sellerReview);
    }

    async getSellerReviewInput() {
        return this.sellerReviewInput.getAttribute('value');
    }

    async setDeliveryRatingInput(deliveryRating) {
        await this.deliveryRatingInput.sendKeys(deliveryRating);
    }

    async getDeliveryRatingInput() {
        return this.deliveryRatingInput.getAttribute('value');
    }

    async setDeliveryReviewInput(deliveryReview) {
        await this.deliveryReviewInput.sendKeys(deliveryReview);
    }

    async getDeliveryReviewInput() {
        return this.deliveryReviewInput.getAttribute('value');
    }

    async setPhotoInput(photo) {
        await this.photoInput.sendKeys(photo);
    }

    async getPhotoInput() {
        return this.photoInput.getAttribute('value');
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

    async reviewSelectLastOption() {
        await this.reviewSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async reviewSelectOption(option) {
        await this.reviewSelect.sendKeys(option);
    }

    getReviewSelect(): ElementFinder {
        return this.reviewSelect;
    }

    async getReviewSelectedOption() {
        return this.reviewSelect.element(by.css('option:checked')).getText();
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

export class ReviewLinesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-reviewLines-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-reviewLines'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
