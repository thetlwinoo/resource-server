/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ReviewsComponentsPage, ReviewsDeleteDialog, ReviewsUpdatePage } from './reviews.page-object';

const expect = chai.expect;

describe('Reviews e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let reviewsUpdatePage: ReviewsUpdatePage;
    let reviewsComponentsPage: ReviewsComponentsPage;
    let reviewsDeleteDialog: ReviewsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Reviews', async () => {
        await navBarPage.goToEntity('reviews');
        reviewsComponentsPage = new ReviewsComponentsPage();
        await browser.wait(ec.visibilityOf(reviewsComponentsPage.title), 5000);
        expect(await reviewsComponentsPage.getTitle()).to.eq('resourceApp.reviews.home.title');
    });

    it('should load create Reviews page', async () => {
        await reviewsComponentsPage.clickOnCreateButton();
        reviewsUpdatePage = new ReviewsUpdatePage();
        expect(await reviewsUpdatePage.getPageTitle()).to.eq('resourceApp.reviews.home.createOrEditLabel');
        await reviewsUpdatePage.cancel();
    });

    it('should create and save Reviews', async () => {
        const nbButtonsBeforeCreate = await reviewsComponentsPage.countDeleteButtons();

        await reviewsComponentsPage.clickOnCreateButton();
        await promise.all([
            reviewsUpdatePage.setReviewerNameInput('reviewerName'),
            reviewsUpdatePage.setEmailAddressInput('emailAddress'),
            reviewsUpdatePage.setReviewDateInput('2000-12-31'),
            reviewsUpdatePage.setOverAllSellerRatingInput('5'),
            reviewsUpdatePage.setOverAllSellerReviewInput('overAllSellerReview'),
            reviewsUpdatePage.setOverAllDeliveryRatingInput('5'),
            reviewsUpdatePage.setOverAllDeliveryReviewInput('overAllDeliveryReview')
        ]);
        expect(await reviewsUpdatePage.getReviewerNameInput()).to.eq('reviewerName');
        expect(await reviewsUpdatePage.getEmailAddressInput()).to.eq('emailAddress');
        expect(await reviewsUpdatePage.getReviewDateInput()).to.eq('2000-12-31');
        expect(await reviewsUpdatePage.getOverAllSellerRatingInput()).to.eq('5');
        expect(await reviewsUpdatePage.getOverAllSellerReviewInput()).to.eq('overAllSellerReview');
        expect(await reviewsUpdatePage.getOverAllDeliveryRatingInput()).to.eq('5');
        expect(await reviewsUpdatePage.getOverAllDeliveryReviewInput()).to.eq('overAllDeliveryReview');
        const selectedReviewAsAnonymous = reviewsUpdatePage.getReviewAsAnonymousInput();
        if (await selectedReviewAsAnonymous.isSelected()) {
            await reviewsUpdatePage.getReviewAsAnonymousInput().click();
            expect(await reviewsUpdatePage.getReviewAsAnonymousInput().isSelected()).to.be.false;
        } else {
            await reviewsUpdatePage.getReviewAsAnonymousInput().click();
            expect(await reviewsUpdatePage.getReviewAsAnonymousInput().isSelected()).to.be.true;
        }
        const selectedCompletedReview = reviewsUpdatePage.getCompletedReviewInput();
        if (await selectedCompletedReview.isSelected()) {
            await reviewsUpdatePage.getCompletedReviewInput().click();
            expect(await reviewsUpdatePage.getCompletedReviewInput().isSelected()).to.be.false;
        } else {
            await reviewsUpdatePage.getCompletedReviewInput().click();
            expect(await reviewsUpdatePage.getCompletedReviewInput().isSelected()).to.be.true;
        }
        await reviewsUpdatePage.save();
        expect(await reviewsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await reviewsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Reviews', async () => {
        const nbButtonsBeforeDelete = await reviewsComponentsPage.countDeleteButtons();
        await reviewsComponentsPage.clickOnLastDeleteButton();

        reviewsDeleteDialog = new ReviewsDeleteDialog();
        expect(await reviewsDeleteDialog.getDialogTitle()).to.eq('resourceApp.reviews.delete.question');
        await reviewsDeleteDialog.clickOnConfirmButton();

        expect(await reviewsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
