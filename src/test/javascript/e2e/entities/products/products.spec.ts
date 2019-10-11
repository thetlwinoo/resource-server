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
            productsUpdatePage.setHandleInput('handle'),
            productsUpdatePage.setProductNumberInput('productNumber'),
            productsUpdatePage.setSearchDetailsInput('searchDetails'),
            productsUpdatePage.setSellCountInput('5'),
            productsUpdatePage.documentSelectLastOption(),
            productsUpdatePage.supplierSelectLastOption(),
            productsUpdatePage.productCategorySelectLastOption(),
            productsUpdatePage.productBrandSelectLastOption()
        ]);
        expect(await productsUpdatePage.getProductNameInput()).to.eq('productName');
        expect(await productsUpdatePage.getHandleInput()).to.eq('handle');
        expect(await productsUpdatePage.getProductNumberInput()).to.eq('productNumber');
        expect(await productsUpdatePage.getSearchDetailsInput()).to.eq('searchDetails');
        expect(await productsUpdatePage.getSellCountInput()).to.eq('5');
        const selectedActiveInd = productsUpdatePage.getActiveIndInput();
        if (await selectedActiveInd.isSelected()) {
            await productsUpdatePage.getActiveIndInput().click();
            expect(await productsUpdatePage.getActiveIndInput().isSelected()).to.be.false;
        } else {
            await productsUpdatePage.getActiveIndInput().click();
            expect(await productsUpdatePage.getActiveIndInput().isSelected()).to.be.true;
        }
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
