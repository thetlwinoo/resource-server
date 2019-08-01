/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ShoppingCartsComponentsPage, ShoppingCartsDeleteDialog, ShoppingCartsUpdatePage } from './shopping-carts.page-object';

const expect = chai.expect;

describe('ShoppingCarts e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let shoppingCartsUpdatePage: ShoppingCartsUpdatePage;
    let shoppingCartsComponentsPage: ShoppingCartsComponentsPage;
    let shoppingCartsDeleteDialog: ShoppingCartsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ShoppingCarts', async () => {
        await navBarPage.goToEntity('shopping-carts');
        shoppingCartsComponentsPage = new ShoppingCartsComponentsPage();
        await browser.wait(ec.visibilityOf(shoppingCartsComponentsPage.title), 5000);
        expect(await shoppingCartsComponentsPage.getTitle()).to.eq('resourceApp.shoppingCarts.home.title');
    });

    it('should load create ShoppingCarts page', async () => {
        await shoppingCartsComponentsPage.clickOnCreateButton();
        shoppingCartsUpdatePage = new ShoppingCartsUpdatePage();
        expect(await shoppingCartsUpdatePage.getPageTitle()).to.eq('resourceApp.shoppingCarts.home.createOrEditLabel');
        await shoppingCartsUpdatePage.cancel();
    });

    it('should create and save ShoppingCarts', async () => {
        const nbButtonsBeforeCreate = await shoppingCartsComponentsPage.countDeleteButtons();

        await shoppingCartsComponentsPage.clickOnCreateButton();
        await promise.all([
            shoppingCartsUpdatePage.setTotalPriceInput('5'),
            shoppingCartsUpdatePage.setTotalCargoPriceInput('5'),
            shoppingCartsUpdatePage.specialDealsSelectLastOption(),
            shoppingCartsUpdatePage.cartUserSelectLastOption(),
            shoppingCartsUpdatePage.customerSelectLastOption()
        ]);
        expect(await shoppingCartsUpdatePage.getTotalPriceInput()).to.eq('5');
        expect(await shoppingCartsUpdatePage.getTotalCargoPriceInput()).to.eq('5');
        await shoppingCartsUpdatePage.save();
        expect(await shoppingCartsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await shoppingCartsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ShoppingCarts', async () => {
        const nbButtonsBeforeDelete = await shoppingCartsComponentsPage.countDeleteButtons();
        await shoppingCartsComponentsPage.clickOnLastDeleteButton();

        shoppingCartsDeleteDialog = new ShoppingCartsDeleteDialog();
        expect(await shoppingCartsDeleteDialog.getDialogTitle()).to.eq('resourceApp.shoppingCarts.delete.question');
        await shoppingCartsDeleteDialog.clickOnConfirmButton();

        expect(await shoppingCartsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
