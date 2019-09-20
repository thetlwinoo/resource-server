/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductsComponentsPage, ProductsDeleteDialog, ProductsUpdatePage } from './products.page-object';

const expect = chai.expect;

describe('Products e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productsUpdatePage: ProductsUpdatePage;
    let productsComponentsPage: ProductsComponentsPage;
    let productsDeleteDialog: ProductsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Products', async () => {
        await navBarPage.goToEntity('products');
        productsComponentsPage = new ProductsComponentsPage();
        await browser.wait(ec.visibilityOf(productsComponentsPage.title), 5000);
        expect(await productsComponentsPage.getTitle()).to.eq('resourceApp.products.home.title');
    });

    it('should load create Products page', async () => {
        await productsComponentsPage.clickOnCreateButton();
        productsUpdatePage = new ProductsUpdatePage();
        expect(await productsUpdatePage.getPageTitle()).to.eq('resourceApp.products.home.createOrEditLabel');
        await productsUpdatePage.cancel();
    });

    it('should create and save Products', async () => {
        const nbButtonsBeforeCreate = await productsComponentsPage.countDeleteButtons();

        await productsComponentsPage.clickOnCreateButton();
        await promise.all([
            productsUpdatePage.setProductNameInput('productName'),
            productsUpdatePage.setProductNumberInput('productNumber'),
            productsUpdatePage.setSearchDetailsInput('searchDetails'),
            productsUpdatePage.setThumbnailUrlInput('thumbnailUrl'),
            productsUpdatePage.setWarrantyPeriodInput('warrantyPeriod'),
            productsUpdatePage.setWarrantyPolicyInput('warrantyPolicy'),
            productsUpdatePage.setSellCountInput('5'),
            productsUpdatePage.setWhatInTheBoxInput('whatInTheBox'),
            productsUpdatePage.supplierSelectLastOption(),
            productsUpdatePage.merchantSelectLastOption(),
            productsUpdatePage.unitPackageSelectLastOption(),
            productsUpdatePage.outerPackageSelectLastOption(),
            productsUpdatePage.productModelSelectLastOption(),
            productsUpdatePage.productCategorySelectLastOption(),
            productsUpdatePage.productBrandSelectLastOption(),
            productsUpdatePage.warrantyTypeSelectLastOption()
        ]);
        expect(await productsUpdatePage.getProductNameInput()).to.eq('productName');
        expect(await productsUpdatePage.getProductNumberInput()).to.eq('productNumber');
        expect(await productsUpdatePage.getSearchDetailsInput()).to.eq('searchDetails');
        expect(await productsUpdatePage.getThumbnailUrlInput()).to.eq('thumbnailUrl');
        expect(await productsUpdatePage.getWarrantyPeriodInput()).to.eq('warrantyPeriod');
        expect(await productsUpdatePage.getWarrantyPolicyInput()).to.eq('warrantyPolicy');
        expect(await productsUpdatePage.getSellCountInput()).to.eq('5');
        expect(await productsUpdatePage.getWhatInTheBoxInput()).to.eq('whatInTheBox');
        await productsUpdatePage.save();
        expect(await productsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Products', async () => {
        const nbButtonsBeforeDelete = await productsComponentsPage.countDeleteButtons();
        await productsComponentsPage.clickOnLastDeleteButton();

        productsDeleteDialog = new ProductsDeleteDialog();
        expect(await productsDeleteDialog.getDialogTitle()).to.eq('resourceApp.products.delete.question');
        await productsDeleteDialog.clickOnConfirmButton();

        expect(await productsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
