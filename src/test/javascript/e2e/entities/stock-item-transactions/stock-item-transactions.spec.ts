/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    StockItemTransactionsComponentsPage,
    StockItemTransactionsDeleteDialog,
    StockItemTransactionsUpdatePage
} from './stock-item-transactions.page-object';

const expect = chai.expect;

describe('StockItemTransactions e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let stockItemTransactionsUpdatePage: StockItemTransactionsUpdatePage;
    let stockItemTransactionsComponentsPage: StockItemTransactionsComponentsPage;
    let stockItemTransactionsDeleteDialog: StockItemTransactionsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StockItemTransactions', async () => {
        await navBarPage.goToEntity('stock-item-transactions');
        stockItemTransactionsComponentsPage = new StockItemTransactionsComponentsPage();
        await browser.wait(ec.visibilityOf(stockItemTransactionsComponentsPage.title), 5000);
        expect(await stockItemTransactionsComponentsPage.getTitle()).to.eq('resourceApp.stockItemTransactions.home.title');
    });

    it('should load create StockItemTransactions page', async () => {
        await stockItemTransactionsComponentsPage.clickOnCreateButton();
        stockItemTransactionsUpdatePage = new StockItemTransactionsUpdatePage();
        expect(await stockItemTransactionsUpdatePage.getPageTitle()).to.eq('resourceApp.stockItemTransactions.home.createOrEditLabel');
        await stockItemTransactionsUpdatePage.cancel();
    });

    it('should create and save StockItemTransactions', async () => {
        const nbButtonsBeforeCreate = await stockItemTransactionsComponentsPage.countDeleteButtons();

        await stockItemTransactionsComponentsPage.clickOnCreateButton();
        await promise.all([
            stockItemTransactionsUpdatePage.setTransactionOccurredWhenInput('2000-12-31'),
            stockItemTransactionsUpdatePage.setQuantityInput('5'),
            stockItemTransactionsUpdatePage.customerSelectLastOption(),
            stockItemTransactionsUpdatePage.invoiceSelectLastOption(),
            stockItemTransactionsUpdatePage.purchaseOrderSelectLastOption(),
            stockItemTransactionsUpdatePage.productSelectLastOption(),
            stockItemTransactionsUpdatePage.supplierSelectLastOption(),
            stockItemTransactionsUpdatePage.transactionTypeSelectLastOption()
        ]);
        expect(await stockItemTransactionsUpdatePage.getTransactionOccurredWhenInput()).to.eq('2000-12-31');
        expect(await stockItemTransactionsUpdatePage.getQuantityInput()).to.eq('5');
        await stockItemTransactionsUpdatePage.save();
        expect(await stockItemTransactionsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await stockItemTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StockItemTransactions', async () => {
        const nbButtonsBeforeDelete = await stockItemTransactionsComponentsPage.countDeleteButtons();
        await stockItemTransactionsComponentsPage.clickOnLastDeleteButton();

        stockItemTransactionsDeleteDialog = new StockItemTransactionsDeleteDialog();
        expect(await stockItemTransactionsDeleteDialog.getDialogTitle()).to.eq('resourceApp.stockItemTransactions.delete.question');
        await stockItemTransactionsDeleteDialog.clickOnConfirmButton();

        expect(await stockItemTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
