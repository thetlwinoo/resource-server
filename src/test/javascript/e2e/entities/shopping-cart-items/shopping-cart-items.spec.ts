/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ShoppingCartItemsComponentsPage,
    ShoppingCartItemsDeleteDialog,
    ShoppingCartItemsUpdatePage
} from './shopping-cart-items.page-object';

const expect = chai.expect;

describe('ShoppingCartItems e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let shoppingCartItemsUpdatePage: ShoppingCartItemsUpdatePage;
    let shoppingCartItemsComponentsPage: ShoppingCartItemsComponentsPage;
    let shoppingCartItemsDeleteDialog: ShoppingCartItemsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ShoppingCartItems', async () => {
        await navBarPage.goToEntity('shopping-cart-items');
        shoppingCartItemsComponentsPage = new ShoppingCartItemsComponentsPage();
        await browser.wait(ec.visibilityOf(shoppingCartItemsComponentsPage.title), 5000);
        expect(await shoppingCartItemsComponentsPage.getTitle()).to.eq('resourceApp.shoppingCartItems.home.title');
    });

    it('should load create ShoppingCartItems page', async () => {
        await shoppingCartItemsComponentsPage.clickOnCreateButton();
        shoppingCartItemsUpdatePage = new ShoppingCartItemsUpdatePage();
        expect(await shoppingCartItemsUpdatePage.getPageTitle()).to.eq('resourceApp.shoppingCartItems.home.createOrEditLabel');
        await shoppingCartItemsUpdatePage.cancel();
    });

    it('should create and save ShoppingCartItems', async () => {
        const nbButtonsBeforeCreate = await shoppingCartItemsComponentsPage.countDeleteButtons();

        await shoppingCartItemsComponentsPage.clickOnCreateButton();
        await promise.all([
            shoppingCartItemsUpdatePage.setQuantityInput('5'),
            shoppingCartItemsUpdatePage.productSelectLastOption(),
            shoppingCartItemsUpdatePage.cartSelectLastOption()
        ]);
        expect(await shoppingCartItemsUpdatePage.getQuantityInput()).to.eq('5');
        await shoppingCartItemsUpdatePage.save();
        expect(await shoppingCartItemsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await shoppingCartItemsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ShoppingCartItems', async () => {
        const nbButtonsBeforeDelete = await shoppingCartItemsComponentsPage.countDeleteButtons();
        await shoppingCartItemsComponentsPage.clickOnLastDeleteButton();

        shoppingCartItemsDeleteDialog = new ShoppingCartItemsDeleteDialog();
        expect(await shoppingCartItemsDeleteDialog.getDialogTitle()).to.eq('resourceApp.shoppingCartItems.delete.question');
        await shoppingCartItemsDeleteDialog.clickOnConfirmButton();

        expect(await shoppingCartItemsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
