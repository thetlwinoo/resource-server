/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductBrandComponentsPage, ProductBrandDeleteDialog, ProductBrandUpdatePage } from './product-brand.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('ProductBrand e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productBrandUpdatePage: ProductBrandUpdatePage;
    let productBrandComponentsPage: ProductBrandComponentsPage;
    let productBrandDeleteDialog: ProductBrandDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductBrands', async () => {
        await navBarPage.goToEntity('product-brand');
        productBrandComponentsPage = new ProductBrandComponentsPage();
        await browser.wait(ec.visibilityOf(productBrandComponentsPage.title), 5000);
        expect(await productBrandComponentsPage.getTitle()).to.eq('resourceApp.productBrand.home.title');
    });

    it('should load create ProductBrand page', async () => {
        await productBrandComponentsPage.clickOnCreateButton();
        productBrandUpdatePage = new ProductBrandUpdatePage();
        expect(await productBrandUpdatePage.getPageTitle()).to.eq('resourceApp.productBrand.home.createOrEditLabel');
        await productBrandUpdatePage.cancel();
    });

    it('should create and save ProductBrands', async () => {
        const nbButtonsBeforeCreate = await productBrandComponentsPage.countDeleteButtons();

        await productBrandComponentsPage.clickOnCreateButton();
        await promise.all([
            productBrandUpdatePage.setProductBrandNameInput('productBrandName'),
            productBrandUpdatePage.setPhotoInput(absolutePath),
            productBrandUpdatePage.merchantSelectLastOption()
        ]);
        expect(await productBrandUpdatePage.getProductBrandNameInput()).to.eq('productBrandName');
        expect(await productBrandUpdatePage.getPhotoInput()).to.endsWith(fileNameToUpload);
        await productBrandUpdatePage.save();
        expect(await productBrandUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productBrandComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductBrand', async () => {
        const nbButtonsBeforeDelete = await productBrandComponentsPage.countDeleteButtons();
        await productBrandComponentsPage.clickOnLastDeleteButton();

        productBrandDeleteDialog = new ProductBrandDeleteDialog();
        expect(await productBrandDeleteDialog.getDialogTitle()).to.eq('resourceApp.productBrand.delete.question');
        await productBrandDeleteDialog.clickOnConfirmButton();

        expect(await productBrandComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
