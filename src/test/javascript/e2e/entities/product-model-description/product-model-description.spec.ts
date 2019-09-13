/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ProductModelDescriptionComponentsPage,
    ProductModelDescriptionDeleteDialog,
    ProductModelDescriptionUpdatePage
} from './product-model-description.page-object';

const expect = chai.expect;

describe('ProductModelDescription e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productModelDescriptionUpdatePage: ProductModelDescriptionUpdatePage;
    let productModelDescriptionComponentsPage: ProductModelDescriptionComponentsPage;
    let productModelDescriptionDeleteDialog: ProductModelDescriptionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductModelDescriptions', async () => {
        await navBarPage.goToEntity('product-model-description');
        productModelDescriptionComponentsPage = new ProductModelDescriptionComponentsPage();
        await browser.wait(ec.visibilityOf(productModelDescriptionComponentsPage.title), 5000);
        expect(await productModelDescriptionComponentsPage.getTitle()).to.eq('resourceApp.productModelDescription.home.title');
    });

    it('should load create ProductModelDescription page', async () => {
        await productModelDescriptionComponentsPage.clickOnCreateButton();
        productModelDescriptionUpdatePage = new ProductModelDescriptionUpdatePage();
        expect(await productModelDescriptionUpdatePage.getPageTitle()).to.eq('resourceApp.productModelDescription.home.createOrEditLabel');
        await productModelDescriptionUpdatePage.cancel();
    });

    it('should create and save ProductModelDescriptions', async () => {
        const nbButtonsBeforeCreate = await productModelDescriptionComponentsPage.countDeleteButtons();

        await productModelDescriptionComponentsPage.clickOnCreateButton();
        await promise.all([
            productModelDescriptionUpdatePage.setDescriptionInput('description'),
            productModelDescriptionUpdatePage.languageSelectLastOption(),
            productModelDescriptionUpdatePage.productModelSelectLastOption()
        ]);
        expect(await productModelDescriptionUpdatePage.getDescriptionInput()).to.eq('description');
        await productModelDescriptionUpdatePage.save();
        expect(await productModelDescriptionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productModelDescriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductModelDescription', async () => {
        const nbButtonsBeforeDelete = await productModelDescriptionComponentsPage.countDeleteButtons();
        await productModelDescriptionComponentsPage.clickOnLastDeleteButton();

        productModelDescriptionDeleteDialog = new ProductModelDescriptionDeleteDialog();
        expect(await productModelDescriptionDeleteDialog.getDialogTitle()).to.eq('resourceApp.productModelDescription.delete.question');
        await productModelDescriptionDeleteDialog.clickOnConfirmButton();

        expect(await productModelDescriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
