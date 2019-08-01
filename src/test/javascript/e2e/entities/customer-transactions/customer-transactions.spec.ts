/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    CustomerTransactionsComponentsPage,
    CustomerTransactionsDeleteDialog,
    CustomerTransactionsUpdatePage
} from './customer-transactions.page-object';

const expect = chai.expect;

describe('CustomerTransactions e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let customerTransactionsUpdatePage: CustomerTransactionsUpdatePage;
    let customerTransactionsComponentsPage: CustomerTransactionsComponentsPage;
    let customerTransactionsDeleteDialog: CustomerTransactionsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load CustomerTransactions', async () => {
        await navBarPage.goToEntity('customer-transactions');
        customerTransactionsComponentsPage = new CustomerTransactionsComponentsPage();
        await browser.wait(ec.visibilityOf(customerTransactionsComponentsPage.title), 5000);
        expect(await customerTransactionsComponentsPage.getTitle()).to.eq('resourceApp.customerTransactions.home.title');
    });

    it('should load create CustomerTransactions page', async () => {
        await customerTransactionsComponentsPage.clickOnCreateButton();
        customerTransactionsUpdatePage = new CustomerTransactionsUpdatePage();
        expect(await customerTransactionsUpdatePage.getPageTitle()).to.eq('resourceApp.customerTransactions.home.createOrEditLabel');
        await customerTransactionsUpdatePage.cancel();
    });

    it('should create and save CustomerTransactions', async () => {
        const nbButtonsBeforeCreate = await customerTransactionsComponentsPage.countDeleteButtons();

        await customerTransactionsComponentsPage.clickOnCreateButton();
        await promise.all([
            customerTransactionsUpdatePage.setTransactionDateInput('2000-12-31'),
            customerTransactionsUpdatePage.setAmountExcludingTaxInput('5'),
            customerTransactionsUpdatePage.setTaxAmountInput('5'),
            customerTransactionsUpdatePage.setTransactionAmountInput('5'),
            customerTransactionsUpdatePage.setOutstandingBalanceInput('5'),
            customerTransactionsUpdatePage.setFinalizationDateInput('2000-12-31'),
            customerTransactionsUpdatePage.customerSelectLastOption(),
            customerTransactionsUpdatePage.paymentMethodSelectLastOption(),
            customerTransactionsUpdatePage.paymentTransactionSelectLastOption(),
            customerTransactionsUpdatePage.transactionTypeSelectLastOption(),
            customerTransactionsUpdatePage.invoiceSelectLastOption()
        ]);
        expect(await customerTransactionsUpdatePage.getTransactionDateInput()).to.eq('2000-12-31');
        expect(await customerTransactionsUpdatePage.getAmountExcludingTaxInput()).to.eq('5');
        expect(await customerTransactionsUpdatePage.getTaxAmountInput()).to.eq('5');
        expect(await customerTransactionsUpdatePage.getTransactionAmountInput()).to.eq('5');
        expect(await customerTransactionsUpdatePage.getOutstandingBalanceInput()).to.eq('5');
        expect(await customerTransactionsUpdatePage.getFinalizationDateInput()).to.eq('2000-12-31');
        const selectedIsFinalized = customerTransactionsUpdatePage.getIsFinalizedInput();
        if (await selectedIsFinalized.isSelected()) {
            await customerTransactionsUpdatePage.getIsFinalizedInput().click();
            expect(await customerTransactionsUpdatePage.getIsFinalizedInput().isSelected()).to.be.false;
        } else {
            await customerTransactionsUpdatePage.getIsFinalizedInput().click();
            expect(await customerTransactionsUpdatePage.getIsFinalizedInput().isSelected()).to.be.true;
        }
        await customerTransactionsUpdatePage.save();
        expect(await customerTransactionsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await customerTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last CustomerTransactions', async () => {
        const nbButtonsBeforeDelete = await customerTransactionsComponentsPage.countDeleteButtons();
        await customerTransactionsComponentsPage.clickOnLastDeleteButton();

        customerTransactionsDeleteDialog = new CustomerTransactionsDeleteDialog();
        expect(await customerTransactionsDeleteDialog.getDialogTitle()).to.eq('resourceApp.customerTransactions.delete.question');
        await customerTransactionsDeleteDialog.clickOnConfirmButton();

        expect(await customerTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
