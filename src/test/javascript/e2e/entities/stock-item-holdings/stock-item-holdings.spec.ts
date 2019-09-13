/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    StockItemHoldingsComponentsPage,
    StockItemHoldingsDeleteDialog,
    StockItemHoldingsUpdatePage
} from './stock-item-holdings.page-object';

const expect = chai.expect;

describe('StockItemHoldings e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let stockItemHoldingsUpdatePage: StockItemHoldingsUpdatePage;
    let stockItemHoldingsComponentsPage: StockItemHoldingsComponentsPage;
    let stockItemHoldingsDeleteDialog: StockItemHoldingsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StockItemHoldings', async () => {
        await navBarPage.goToEntity('stock-item-holdings');
        stockItemHoldingsComponentsPage = new StockItemHoldingsComponentsPage();
        await browser.wait(ec.visibilityOf(stockItemHoldingsComponentsPage.title), 5000);
        expect(await stockItemHoldingsComponentsPage.getTitle()).to.eq('resourceApp.stockItemHoldings.home.title');
    });

    it('should load create StockItemHoldings page', async () => {
        await stockItemHoldingsComponentsPage.clickOnCreateButton();
        stockItemHoldingsUpdatePage = new StockItemHoldingsUpdatePage();
        expect(await stockItemHoldingsUpdatePage.getPageTitle()).to.eq('resourceApp.stockItemHoldings.home.createOrEditLabel');
        await stockItemHoldingsUpdatePage.cancel();
    });

    it('should create and save StockItemHoldings', async () => {
        const nbButtonsBeforeCreate = await stockItemHoldingsComponentsPage.countDeleteButtons();

        await stockItemHoldingsComponentsPage.clickOnCreateButton();
        await promise.all([
            stockItemHoldingsUpdatePage.setQuantityOnHandInput('5'),
            stockItemHoldingsUpdatePage.setBinLocationInput('binLocation'),
            stockItemHoldingsUpdatePage.setLastStocktakeQuantityInput('5'),
            stockItemHoldingsUpdatePage.setLastCostPriceInput('5'),
            stockItemHoldingsUpdatePage.setReorderLevelInput('5'),
            stockItemHoldingsUpdatePage.setTargerStockLevelInput('5'),
            stockItemHoldingsUpdatePage.stockItemSelectLastOption()
        ]);
        expect(await stockItemHoldingsUpdatePage.getQuantityOnHandInput()).to.eq('5');
        expect(await stockItemHoldingsUpdatePage.getBinLocationInput()).to.eq('binLocation');
        expect(await stockItemHoldingsUpdatePage.getLastStocktakeQuantityInput()).to.eq('5');
        expect(await stockItemHoldingsUpdatePage.getLastCostPriceInput()).to.eq('5');
        expect(await stockItemHoldingsUpdatePage.getReorderLevelInput()).to.eq('5');
        expect(await stockItemHoldingsUpdatePage.getTargerStockLevelInput()).to.eq('5');
        await stockItemHoldingsUpdatePage.save();
        expect(await stockItemHoldingsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await stockItemHoldingsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StockItemHoldings', async () => {
        const nbButtonsBeforeDelete = await stockItemHoldingsComponentsPage.countDeleteButtons();
        await stockItemHoldingsComponentsPage.clickOnLastDeleteButton();

        stockItemHoldingsDeleteDialog = new StockItemHoldingsDeleteDialog();
        expect(await stockItemHoldingsDeleteDialog.getDialogTitle()).to.eq('resourceApp.stockItemHoldings.delete.question');
        await stockItemHoldingsDeleteDialog.clickOnConfirmButton();

        expect(await stockItemHoldingsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
