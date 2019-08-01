/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SpecialDealsComponentsPage, SpecialDealsDeleteDialog, SpecialDealsUpdatePage } from './special-deals.page-object';

const expect = chai.expect;

describe('SpecialDeals e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let specialDealsUpdatePage: SpecialDealsUpdatePage;
    let specialDealsComponentsPage: SpecialDealsComponentsPage;
    let specialDealsDeleteDialog: SpecialDealsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SpecialDeals', async () => {
        await navBarPage.goToEntity('special-deals');
        specialDealsComponentsPage = new SpecialDealsComponentsPage();
        await browser.wait(ec.visibilityOf(specialDealsComponentsPage.title), 5000);
        expect(await specialDealsComponentsPage.getTitle()).to.eq('resourceApp.specialDeals.home.title');
    });

    it('should load create SpecialDeals page', async () => {
        await specialDealsComponentsPage.clickOnCreateButton();
        specialDealsUpdatePage = new SpecialDealsUpdatePage();
        expect(await specialDealsUpdatePage.getPageTitle()).to.eq('resourceApp.specialDeals.home.createOrEditLabel');
        await specialDealsUpdatePage.cancel();
    });

    it('should create and save SpecialDeals', async () => {
        const nbButtonsBeforeCreate = await specialDealsComponentsPage.countDeleteButtons();

        await specialDealsComponentsPage.clickOnCreateButton();
        await promise.all([
            specialDealsUpdatePage.setDealDescriptionInput('dealDescription'),
            specialDealsUpdatePage.setStartDateInput('2000-12-31'),
            specialDealsUpdatePage.setEndDateInput('2000-12-31'),
            specialDealsUpdatePage.setDiscountAmountInput('5'),
            specialDealsUpdatePage.setDiscountPercentageInput('5'),
            specialDealsUpdatePage.setDiscountCodeInput('discountCode'),
            specialDealsUpdatePage.setUnitPriceInput('5'),
            specialDealsUpdatePage.buyingGroupSelectLastOption(),
            specialDealsUpdatePage.customerCategorySelectLastOption(),
            specialDealsUpdatePage.customerSelectLastOption(),
            specialDealsUpdatePage.stockGroupSelectLastOption(),
            specialDealsUpdatePage.productSelectLastOption()
        ]);
        expect(await specialDealsUpdatePage.getDealDescriptionInput()).to.eq('dealDescription');
        expect(await specialDealsUpdatePage.getStartDateInput()).to.eq('2000-12-31');
        expect(await specialDealsUpdatePage.getEndDateInput()).to.eq('2000-12-31');
        expect(await specialDealsUpdatePage.getDiscountAmountInput()).to.eq('5');
        expect(await specialDealsUpdatePage.getDiscountPercentageInput()).to.eq('5');
        expect(await specialDealsUpdatePage.getDiscountCodeInput()).to.eq('discountCode');
        expect(await specialDealsUpdatePage.getUnitPriceInput()).to.eq('5');
        await specialDealsUpdatePage.save();
        expect(await specialDealsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await specialDealsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SpecialDeals', async () => {
        const nbButtonsBeforeDelete = await specialDealsComponentsPage.countDeleteButtons();
        await specialDealsComponentsPage.clickOnLastDeleteButton();

        specialDealsDeleteDialog = new SpecialDealsDeleteDialog();
        expect(await specialDealsDeleteDialog.getDialogTitle()).to.eq('resourceApp.specialDeals.delete.question');
        await specialDealsDeleteDialog.clickOnConfirmButton();

        expect(await specialDealsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
