/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductReviewComponentsPage, ProductReviewDeleteDialog, ProductReviewUpdatePage } from './product-review.page-object';

const expect = chai.expect;

describe('ProductReview e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productReviewUpdatePage: ProductReviewUpdatePage;
    let productReviewComponentsPage: ProductReviewComponentsPage;
    let productReviewDeleteDialog: ProductReviewDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductReviews', async () => {
        await navBarPage.goToEntity('product-review');
        productReviewComponentsPage = new ProductReviewComponentsPage();
        await browser.wait(ec.visibilityOf(productReviewComponentsPage.title), 5000);
        expect(await productReviewComponentsPage.getTitle()).to.eq('resourceApp.productReview.home.title');
    });

    it('should load create ProductReview page', async () => {
        await productReviewComponentsPage.clickOnCreateButton();
        productReviewUpdatePage = new ProductReviewUpdatePage();
        expect(await productReviewUpdatePage.getPageTitle()).to.eq('resourceApp.productReview.home.createOrEditLabel');
        await productReviewUpdatePage.cancel();
    });

    it('should create and save ProductReviews', async () => {
        const nbButtonsBeforeCreate = await productReviewComponentsPage.countDeleteButtons();

        await productReviewComponentsPage.clickOnCreateButton();
        await promise.all([
            productReviewUpdatePage.setReviewerNameInput('reviewerName'),
            productReviewUpdatePage.setReviewDateInput('2000-12-31'),
            productReviewUpdatePage.setEmailAddressInput('emailAddress'),
            productReviewUpdatePage.setRatingInput('5'),
            productReviewUpdatePage.setCommentsInput('comments'),
            productReviewUpdatePage.productSelectLastOption()
        ]);
        expect(await productReviewUpdatePage.getReviewerNameInput()).to.eq('reviewerName');
        expect(await productReviewUpdatePage.getReviewDateInput()).to.eq('2000-12-31');
        expect(await productReviewUpdatePage.getEmailAddressInput()).to.eq('emailAddress');
        expect(await productReviewUpdatePage.getRatingInput()).to.eq('5');
        expect(await productReviewUpdatePage.getCommentsInput()).to.eq('comments');
        await productReviewUpdatePage.save();
        expect(await productReviewUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productReviewComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductReview', async () => {
        const nbButtonsBeforeDelete = await productReviewComponentsPage.countDeleteButtons();
        await productReviewComponentsPage.clickOnLastDeleteButton();

        productReviewDeleteDialog = new ProductReviewDeleteDialog();
        expect(await productReviewDeleteDialog.getDialogTitle()).to.eq('resourceApp.productReview.delete.question');
        await productReviewDeleteDialog.clickOnConfirmButton();

        expect(await productReviewComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
