/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductDocumentComponentsPage, ProductDocumentDeleteDialog, ProductDocumentUpdatePage } from './product-document.page-object';

const expect = chai.expect;

describe('ProductDocument e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productDocumentUpdatePage: ProductDocumentUpdatePage;
    let productDocumentComponentsPage: ProductDocumentComponentsPage;
    let productDocumentDeleteDialog: ProductDocumentDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductDocuments', async () => {
        await navBarPage.goToEntity('product-document');
        productDocumentComponentsPage = new ProductDocumentComponentsPage();
        await browser.wait(ec.visibilityOf(productDocumentComponentsPage.title), 5000);
        expect(await productDocumentComponentsPage.getTitle()).to.eq('resourceApp.productDocument.home.title');
    });

    it('should load create ProductDocument page', async () => {
        await productDocumentComponentsPage.clickOnCreateButton();
        productDocumentUpdatePage = new ProductDocumentUpdatePage();
        expect(await productDocumentUpdatePage.getPageTitle()).to.eq('resourceApp.productDocument.home.createOrEditLabel');
        await productDocumentUpdatePage.cancel();
    });

    it('should create and save ProductDocuments', async () => {
        const nbButtonsBeforeCreate = await productDocumentComponentsPage.countDeleteButtons();

        await productDocumentComponentsPage.clickOnCreateButton();
        await promise.all([
            productDocumentUpdatePage.setDocumentNodeInput('documentNode'),
            productDocumentUpdatePage.productSelectLastOption(),
            productDocumentUpdatePage.cultureSelectLastOption()
        ]);
        expect(await productDocumentUpdatePage.getDocumentNodeInput()).to.eq('documentNode');
        await productDocumentUpdatePage.save();
        expect(await productDocumentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductDocument', async () => {
        const nbButtonsBeforeDelete = await productDocumentComponentsPage.countDeleteButtons();
        await productDocumentComponentsPage.clickOnLastDeleteButton();

        productDocumentDeleteDialog = new ProductDocumentDeleteDialog();
        expect(await productDocumentDeleteDialog.getDialogTitle()).to.eq('resourceApp.productDocument.delete.question');
        await productDocumentDeleteDialog.clickOnConfirmButton();

        expect(await productDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
