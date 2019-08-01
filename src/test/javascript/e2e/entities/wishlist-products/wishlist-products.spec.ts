/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WishlistProductsComponentsPage, WishlistProductsDeleteDialog, WishlistProductsUpdatePage } from './wishlist-products.page-object';

const expect = chai.expect;

describe('WishlistProducts e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let wishlistProductsUpdatePage: WishlistProductsUpdatePage;
    let wishlistProductsComponentsPage: WishlistProductsComponentsPage;
    let wishlistProductsDeleteDialog: WishlistProductsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load WishlistProducts', async () => {
        await navBarPage.goToEntity('wishlist-products');
        wishlistProductsComponentsPage = new WishlistProductsComponentsPage();
        await browser.wait(ec.visibilityOf(wishlistProductsComponentsPage.title), 5000);
        expect(await wishlistProductsComponentsPage.getTitle()).to.eq('resourceApp.wishlistProducts.home.title');
    });

    it('should load create WishlistProducts page', async () => {
        await wishlistProductsComponentsPage.clickOnCreateButton();
        wishlistProductsUpdatePage = new WishlistProductsUpdatePage();
        expect(await wishlistProductsUpdatePage.getPageTitle()).to.eq('resourceApp.wishlistProducts.home.createOrEditLabel');
        await wishlistProductsUpdatePage.cancel();
    });

    it('should create and save WishlistProducts', async () => {
        const nbButtonsBeforeCreate = await wishlistProductsComponentsPage.countDeleteButtons();

        await wishlistProductsComponentsPage.clickOnCreateButton();
        await promise.all([wishlistProductsUpdatePage.productSelectLastOption(), wishlistProductsUpdatePage.wishlistSelectLastOption()]);
        await wishlistProductsUpdatePage.save();
        expect(await wishlistProductsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await wishlistProductsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last WishlistProducts', async () => {
        const nbButtonsBeforeDelete = await wishlistProductsComponentsPage.countDeleteButtons();
        await wishlistProductsComponentsPage.clickOnLastDeleteButton();

        wishlistProductsDeleteDialog = new WishlistProductsDeleteDialog();
        expect(await wishlistProductsDeleteDialog.getDialogTitle()).to.eq('resourceApp.wishlistProducts.delete.question');
        await wishlistProductsDeleteDialog.clickOnConfirmButton();

        expect(await wishlistProductsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
