/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ProductTransactionsComponentsPage,
    ProductTransactionsDeleteDialog,
    ProductTransactionsUpdatePage
} from './product-transactions.page-object';

const expect = chai.expect;

describe('ProductTransactions e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productTransactionsUpdatePage: ProductTransactionsUpdatePage;
    let productTransactionsComponentsPage: ProductTransactionsComponentsPage;
    let productTransactionsDeleteDialog: ProductTransactionsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductTransactions', async () => {
        await navBarPage.goToEntity('product-transactions');
        productTransactionsComponentsPage = new ProductTransactionsComponentsPage();
        await browser.wait(ec.visibilityOf(productTransactionsComponentsPage.title), 5000);
        expect(await productTransactionsComponentsPage.getTitle()).to.eq('resourceApp.productTransactions.home.title');
    });

    it('should load create ProductTransactions page', async () => {
        await productTransactionsComponentsPage.clickOnCreateButton();
        productTransactionsUpdatePage = new ProductTransactionsUpdatePage();
        expect(await productTransactionsUpdatePage.getPageTitle()).to.eq('resourceApp.productTransactions.home.createOrEditLabel');
        await productTransactionsUpdatePage.cancel();
    });

    it('should create and save ProductTransactions', async () => {
        const nbButtonsBeforeCreate = await productTransactionsComponentsPage.countDeleteButtons();

        await productTransactionsComponentsPage.clickOnCreateButton();
        await promise.all([
            productTransactionsUpdatePage.setTransactionOccuredWhenInput('2000-12-31'),
            productTransactionsUpdatePage.setQuantityInput('5'),
            productTransactionsUpdatePage.productSelectLastOption(),
            productTransactionsUpdatePage.customerSelectLastOption(),
            productTransactionsUpdatePage.invoiceSelectLastOption(),
            productTransactionsUpdatePage.supplierSelectLastOption(),
            productTransactionsUpdatePage.transactionTypeSelectLastOption(),
            productTransactionsUpdatePage.purchaseOrderSelectLastOption()
        ]);
        expect(await productTransactionsUpdatePage.getTransactionOccuredWhenInput()).to.eq('2000-12-31');
        expect(await productTransactionsUpdatePage.getQuantityInput()).to.eq('5');
        await productTransactionsUpdatePage.save();
        expect(await productTransactionsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductTransactions', async () => {
        const nbButtonsBeforeDelete = await productTransactionsComponentsPage.countDeleteButtons();
        await productTransactionsComponentsPage.clickOnLastDeleteButton();

        productTransactionsDeleteDialog = new ProductTransactionsDeleteDialog();
        expect(await productTransactionsDeleteDialog.getDialogTitle()).to.eq('resourceApp.productTransactions.delete.question');
        await productTransactionsDeleteDialog.clickOnConfirmButton();

        expect(await productTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
