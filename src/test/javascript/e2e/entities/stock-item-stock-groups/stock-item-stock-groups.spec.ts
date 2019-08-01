/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    StockItemStockGroupsComponentsPage,
    StockItemStockGroupsDeleteDialog,
    StockItemStockGroupsUpdatePage
} from './stock-item-stock-groups.page-object';

const expect = chai.expect;

describe('StockItemStockGroups e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let stockItemStockGroupsUpdatePage: StockItemStockGroupsUpdatePage;
    let stockItemStockGroupsComponentsPage: StockItemStockGroupsComponentsPage;
    let stockItemStockGroupsDeleteDialog: StockItemStockGroupsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StockItemStockGroups', async () => {
        await navBarPage.goToEntity('stock-item-stock-groups');
        stockItemStockGroupsComponentsPage = new StockItemStockGroupsComponentsPage();
        await browser.wait(ec.visibilityOf(stockItemStockGroupsComponentsPage.title), 5000);
        expect(await stockItemStockGroupsComponentsPage.getTitle()).to.eq('resourceApp.stockItemStockGroups.home.title');
    });

    it('should load create StockItemStockGroups page', async () => {
        await stockItemStockGroupsComponentsPage.clickOnCreateButton();
        stockItemStockGroupsUpdatePage = new StockItemStockGroupsUpdatePage();
        expect(await stockItemStockGroupsUpdatePage.getPageTitle()).to.eq('resourceApp.stockItemStockGroups.home.createOrEditLabel');
        await stockItemStockGroupsUpdatePage.cancel();
    });

    it('should create and save StockItemStockGroups', async () => {
        const nbButtonsBeforeCreate = await stockItemStockGroupsComponentsPage.countDeleteButtons();

        await stockItemStockGroupsComponentsPage.clickOnCreateButton();
        await promise.all([
            stockItemStockGroupsUpdatePage.stockGroupSelectLastOption(),
            stockItemStockGroupsUpdatePage.productSelectLastOption()
        ]);
        await stockItemStockGroupsUpdatePage.save();
        expect(await stockItemStockGroupsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await stockItemStockGroupsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StockItemStockGroups', async () => {
        const nbButtonsBeforeDelete = await stockItemStockGroupsComponentsPage.countDeleteButtons();
        await stockItemStockGroupsComponentsPage.clickOnLastDeleteButton();

        stockItemStockGroupsDeleteDialog = new StockItemStockGroupsDeleteDialog();
        expect(await stockItemStockGroupsDeleteDialog.getDialogTitle()).to.eq('resourceApp.stockItemStockGroups.delete.question');
        await stockItemStockGroupsDeleteDialog.clickOnConfirmButton();

        expect(await stockItemStockGroupsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
