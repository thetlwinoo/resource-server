/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ProductDescriptionComponentsPage,
    ProductDescriptionDeleteDialog,
    ProductDescriptionUpdatePage
} from './product-description.page-object';

const expect = chai.expect;

describe('ProductDescription e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productDescriptionUpdatePage: ProductDescriptionUpdatePage;
    let productDescriptionComponentsPage: ProductDescriptionComponentsPage;
    let productDescriptionDeleteDialog: ProductDescriptionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductDescriptions', async () => {
        await navBarPage.goToEntity('product-description');
        productDescriptionComponentsPage = new ProductDescriptionComponentsPage();
        await browser.wait(ec.visibilityOf(productDescriptionComponentsPage.title), 5000);
        expect(await productDescriptionComponentsPage.getTitle()).to.eq('resourceApp.productDescription.home.title');
    });

    it('should load create ProductDescription page', async () => {
        await productDescriptionComponentsPage.clickOnCreateButton();
        productDescriptionUpdatePage = new ProductDescriptionUpdatePage();
        expect(await productDescriptionUpdatePage.getPageTitle()).to.eq('resourceApp.productDescription.home.createOrEditLabel');
        await productDescriptionUpdatePage.cancel();
    });

    it('should create and save ProductDescriptions', async () => {
        const nbButtonsBeforeCreate = await productDescriptionComponentsPage.countDeleteButtons();

        await productDescriptionComponentsPage.clickOnCreateButton();
        await promise.all([
            productDescriptionUpdatePage.setDescriptionInput('description'),
            productDescriptionUpdatePage.productModelSelectLastOption()
        ]);
        expect(await productDescriptionUpdatePage.getDescriptionInput()).to.eq('description');
        await productDescriptionUpdatePage.save();
        expect(await productDescriptionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productDescriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductDescription', async () => {
        const nbButtonsBeforeDelete = await productDescriptionComponentsPage.countDeleteButtons();
        await productDescriptionComponentsPage.clickOnLastDeleteButton();

        productDescriptionDeleteDialog = new ProductDescriptionDeleteDialog();
        expect(await productDescriptionDeleteDialog.getDialogTitle()).to.eq('resourceApp.productDescription.delete.question');
        await productDescriptionDeleteDialog.clickOnConfirmButton();

        expect(await productDescriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
