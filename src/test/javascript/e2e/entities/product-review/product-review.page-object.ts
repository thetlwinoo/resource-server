import { element, by, ElementFinder } from 'protractor';

export class ProductReviewComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-review div table .btn-danger'));
    title = element.all(by.css('jhi-product-review div h2#page-heading span')).first();

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

export class ProductReviewUpdatePage {
    pageTitle = element(by.id('jhi-product-review-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    reviewerNameInput = element(by.id('field_reviewerName'));
    reviewDateInput = element(by.id('field_reviewDate'));
    emailAddressInput = element(by.id('field_emailAddress'));
    ratingInput = element(by.id('field_rating'));
    commentsInput = element(by.id('field_comments'));
    productSelect = element(by.id('field_product'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setReviewerNameInput(reviewerName) {
        await this.reviewerNameInput.sendKeys(reviewerName);
    }

    async getReviewerNameInput() {
        return this.reviewerNameInput.getAttribute('value');
    }

    async setReviewDateInput(reviewDate) {
        await this.reviewDateInput.sendKeys(reviewDate);
    }

    async getReviewDateInput() {
        return this.reviewDateInput.getAttribute('value');
    }

    async setEmailAddressInput(emailAddress) {
        await this.emailAddressInput.sendKeys(emailAddress);
    }

    async getEmailAddressInput() {
        return this.emailAddressInput.getAttribute('value');
    }

    async setRatingInput(rating) {
        await this.ratingInput.sendKeys(rating);
    }

    async getRatingInput() {
        return this.ratingInput.getAttribute('value');
    }

    async setCommentsInput(comments) {
        await this.commentsInput.sendKeys(comments);
    }

    async getCommentsInput() {
        return this.commentsInput.getAttribute('value');
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

export class ProductReviewDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productReview-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productReview'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
