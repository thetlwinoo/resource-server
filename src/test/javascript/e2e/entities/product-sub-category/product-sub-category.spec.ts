/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ProductSubCategoryComponentsPage,
    ProductSubCategoryDeleteDialog,
    ProductSubCategoryUpdatePage
} from './product-sub-category.page-object';

const expect = chai.expect;

describe('ProductSubCategory e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productSubCategoryUpdatePage: ProductSubCategoryUpdatePage;
    let productSubCategoryComponentsPage: ProductSubCategoryComponentsPage;
    let productSubCategoryDeleteDialog: ProductSubCategoryDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductSubCategories', async () => {
        await navBarPage.goToEntity('product-sub-category');
        productSubCategoryComponentsPage = new ProductSubCategoryComponentsPage();
        await browser.wait(ec.visibilityOf(productSubCategoryComponentsPage.title), 5000);
        expect(await productSubCategoryComponentsPage.getTitle()).to.eq('resourceApp.productSubCategory.home.title');
    });

    it('should load create ProductSubCategory page', async () => {
        await productSubCategoryComponentsPage.clickOnCreateButton();
        productSubCategoryUpdatePage = new ProductSubCategoryUpdatePage();
        expect(await productSubCategoryUpdatePage.getPageTitle()).to.eq('resourceApp.productSubCategory.home.createOrEditLabel');
        await productSubCategoryUpdatePage.cancel();
    });

    it('should create and save ProductSubCategories', async () => {
        const nbButtonsBeforeCreate = await productSubCategoryComponentsPage.countDeleteButtons();

        await productSubCategoryComponentsPage.clickOnCreateButton();
        await promise.all([
            productSubCategoryUpdatePage.setProductSubCategoryNameInput('productSubCategoryName'),
            productSubCategoryUpdatePage.productCategorySelectLastOption()
        ]);
        expect(await productSubCategoryUpdatePage.getProductSubCategoryNameInput()).to.eq('productSubCategoryName');
        await productSubCategoryUpdatePage.save();
        expect(await productSubCategoryUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productSubCategoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductSubCategory', async () => {
        const nbButtonsBeforeDelete = await productSubCategoryComponentsPage.countDeleteButtons();
        await productSubCategoryComponentsPage.clickOnLastDeleteButton();

        productSubCategoryDeleteDialog = new ProductSubCategoryDeleteDialog();
        expect(await productSubCategoryDeleteDialog.getDialogTitle()).to.eq('resourceApp.productSubCategory.delete.question');
        await productSubCategoryDeleteDialog.clickOnConfirmButton();

        expect(await productSubCategoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
