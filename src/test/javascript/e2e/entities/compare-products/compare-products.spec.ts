/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CompareProductsComponentsPage, CompareProductsDeleteDialog, CompareProductsUpdatePage } from './compare-products.page-object';

const expect = chai.expect;

describe('CompareProducts e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let compareProductsUpdatePage: CompareProductsUpdatePage;
    let compareProductsComponentsPage: CompareProductsComponentsPage;
    let compareProductsDeleteDialog: CompareProductsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load CompareProducts', async () => {
        await navBarPage.goToEntity('compare-products');
        compareProductsComponentsPage = new CompareProductsComponentsPage();
        await browser.wait(ec.visibilityOf(compareProductsComponentsPage.title), 5000);
        expect(await compareProductsComponentsPage.getTitle()).to.eq('resourceApp.compareProducts.home.title');
    });

    it('should load create CompareProducts page', async () => {
        await compareProductsComponentsPage.clickOnCreateButton();
        compareProductsUpdatePage = new CompareProductsUpdatePage();
        expect(await compareProductsUpdatePage.getPageTitle()).to.eq('resourceApp.compareProducts.home.createOrEditLabel');
        await compareProductsUpdatePage.cancel();
    });

    it('should create and save CompareProducts', async () => {
        const nbButtonsBeforeCreate = await compareProductsComponentsPage.countDeleteButtons();

        await compareProductsComponentsPage.clickOnCreateButton();
        await promise.all([compareProductsUpdatePage.productSelectLastOption(), compareProductsUpdatePage.compareSelectLastOption()]);
        await compareProductsUpdatePage.save();
        expect(await compareProductsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await compareProductsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last CompareProducts', async () => {
        const nbButtonsBeforeDelete = await compareProductsComponentsPage.countDeleteButtons();
        await compareProductsComponentsPage.clickOnLastDeleteButton();

        compareProductsDeleteDialog = new CompareProductsDeleteDialog();
        expect(await compareProductsDeleteDialog.getDialogTitle()).to.eq('resourceApp.compareProducts.delete.question');
        await compareProductsDeleteDialog.clickOnConfirmButton();

        expect(await compareProductsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
