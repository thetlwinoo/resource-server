/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    SupplierTransactionsComponentsPage,
    SupplierTransactionsDeleteDialog,
    SupplierTransactionsUpdatePage
} from './supplier-transactions.page-object';

const expect = chai.expect;

describe('SupplierTransactions e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let supplierTransactionsUpdatePage: SupplierTransactionsUpdatePage;
    let supplierTransactionsComponentsPage: SupplierTransactionsComponentsPage;
    let supplierTransactionsDeleteDialog: SupplierTransactionsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SupplierTransactions', async () => {
        await navBarPage.goToEntity('supplier-transactions');
        supplierTransactionsComponentsPage = new SupplierTransactionsComponentsPage();
        await browser.wait(ec.visibilityOf(supplierTransactionsComponentsPage.title), 5000);
        expect(await supplierTransactionsComponentsPage.getTitle()).to.eq('resourceApp.supplierTransactions.home.title');
    });

    it('should load create SupplierTransactions page', async () => {
        await supplierTransactionsComponentsPage.clickOnCreateButton();
        supplierTransactionsUpdatePage = new SupplierTransactionsUpdatePage();
        expect(await supplierTransactionsUpdatePage.getPageTitle()).to.eq('resourceApp.supplierTransactions.home.createOrEditLabel');
        await supplierTransactionsUpdatePage.cancel();
    });

    it('should create and save SupplierTransactions', async () => {
        const nbButtonsBeforeCreate = await supplierTransactionsComponentsPage.countDeleteButtons();

        await supplierTransactionsComponentsPage.clickOnCreateButton();
        await promise.all([
            supplierTransactionsUpdatePage.setSupplierInvoiceNumberInput('supplierInvoiceNumber'),
            supplierTransactionsUpdatePage.setTransactionDateInput('2000-12-31'),
            supplierTransactionsUpdatePage.setAmountExcludingTaxInput('5'),
            supplierTransactionsUpdatePage.setTaxAmountInput('5'),
            supplierTransactionsUpdatePage.setTransactionAmountInput('5'),
            supplierTransactionsUpdatePage.setOutstandingBalanceInput('5'),
            supplierTransactionsUpdatePage.setFinalizationDateInput('2000-12-31'),
            supplierTransactionsUpdatePage.supplierSelectLastOption(),
            supplierTransactionsUpdatePage.transactionTypeSelectLastOption(),
            supplierTransactionsUpdatePage.purchaseOrderSelectLastOption(),
            supplierTransactionsUpdatePage.paymentMethodSelectLastOption()
        ]);
        expect(await supplierTransactionsUpdatePage.getSupplierInvoiceNumberInput()).to.eq('supplierInvoiceNumber');
        expect(await supplierTransactionsUpdatePage.getTransactionDateInput()).to.eq('2000-12-31');
        expect(await supplierTransactionsUpdatePage.getAmountExcludingTaxInput()).to.eq('5');
        expect(await supplierTransactionsUpdatePage.getTaxAmountInput()).to.eq('5');
        expect(await supplierTransactionsUpdatePage.getTransactionAmountInput()).to.eq('5');
        expect(await supplierTransactionsUpdatePage.getOutstandingBalanceInput()).to.eq('5');
        expect(await supplierTransactionsUpdatePage.getFinalizationDateInput()).to.eq('2000-12-31');
        const selectedIsFinalized = supplierTransactionsUpdatePage.getIsFinalizedInput();
        if (await selectedIsFinalized.isSelected()) {
            await supplierTransactionsUpdatePage.getIsFinalizedInput().click();
            expect(await supplierTransactionsUpdatePage.getIsFinalizedInput().isSelected()).to.be.false;
        } else {
            await supplierTransactionsUpdatePage.getIsFinalizedInput().click();
            expect(await supplierTransactionsUpdatePage.getIsFinalizedInput().isSelected()).to.be.true;
        }
        await supplierTransactionsUpdatePage.save();
        expect(await supplierTransactionsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await supplierTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SupplierTransactions', async () => {
        const nbButtonsBeforeDelete = await supplierTransactionsComponentsPage.countDeleteButtons();
        await supplierTransactionsComponentsPage.clickOnLastDeleteButton();

        supplierTransactionsDeleteDialog = new SupplierTransactionsDeleteDialog();
        expect(await supplierTransactionsDeleteDialog.getDialogTitle()).to.eq('resourceApp.supplierTransactions.delete.question');
        await supplierTransactionsDeleteDialog.clickOnConfirmButton();

        expect(await supplierTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
