/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductInventoryComponentsPage, ProductInventoryDeleteDialog, ProductInventoryUpdatePage } from './product-inventory.page-object';

const expect = chai.expect;

describe('ProductInventory e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productInventoryUpdatePage: ProductInventoryUpdatePage;
    let productInventoryComponentsPage: ProductInventoryComponentsPage;
    let productInventoryDeleteDialog: ProductInventoryDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductInventories', async () => {
        await navBarPage.goToEntity('product-inventory');
        productInventoryComponentsPage = new ProductInventoryComponentsPage();
        await browser.wait(ec.visibilityOf(productInventoryComponentsPage.title), 5000);
        expect(await productInventoryComponentsPage.getTitle()).to.eq('resourceApp.productInventory.home.title');
    });

    it('should load create ProductInventory page', async () => {
        await productInventoryComponentsPage.clickOnCreateButton();
        productInventoryUpdatePage = new ProductInventoryUpdatePage();
        expect(await productInventoryUpdatePage.getPageTitle()).to.eq('resourceApp.productInventory.home.createOrEditLabel');
        await productInventoryUpdatePage.cancel();
    });

    it('should create and save ProductInventories', async () => {
        const nbButtonsBeforeCreate = await productInventoryComponentsPage.countDeleteButtons();

        await productInventoryComponentsPage.clickOnCreateButton();
        await promise.all([
            productInventoryUpdatePage.setShelfInput('shelf'),
            productInventoryUpdatePage.setBinInput('5'),
            productInventoryUpdatePage.setQuantityInput('5'),
            productInventoryUpdatePage.productSelectLastOption(),
            productInventoryUpdatePage.locationSelectLastOption()
        ]);
        expect(await productInventoryUpdatePage.getShelfInput()).to.eq('shelf');
        expect(await productInventoryUpdatePage.getBinInput()).to.eq('5');
        expect(await productInventoryUpdatePage.getQuantityInput()).to.eq('5');
        await productInventoryUpdatePage.save();
        expect(await productInventoryUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productInventoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductInventory', async () => {
        const nbButtonsBeforeDelete = await productInventoryComponentsPage.countDeleteButtons();
        await productInventoryComponentsPage.clickOnLastDeleteButton();

        productInventoryDeleteDialog = new ProductInventoryDeleteDialog();
        expect(await productInventoryDeleteDialog.getDialogTitle()).to.eq('resourceApp.productInventory.delete.question');
        await productInventoryDeleteDialog.clickOnConfirmButton();

        expect(await productInventoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
