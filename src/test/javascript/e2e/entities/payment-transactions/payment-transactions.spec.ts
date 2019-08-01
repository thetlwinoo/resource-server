/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    PaymentTransactionsComponentsPage,
    PaymentTransactionsDeleteDialog,
    PaymentTransactionsUpdatePage
} from './payment-transactions.page-object';

const expect = chai.expect;

describe('PaymentTransactions e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let paymentTransactionsUpdatePage: PaymentTransactionsUpdatePage;
    let paymentTransactionsComponentsPage: PaymentTransactionsComponentsPage;
    let paymentTransactionsDeleteDialog: PaymentTransactionsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PaymentTransactions', async () => {
        await navBarPage.goToEntity('payment-transactions');
        paymentTransactionsComponentsPage = new PaymentTransactionsComponentsPage();
        await browser.wait(ec.visibilityOf(paymentTransactionsComponentsPage.title), 5000);
        expect(await paymentTransactionsComponentsPage.getTitle()).to.eq('resourceApp.paymentTransactions.home.title');
    });

    it('should load create PaymentTransactions page', async () => {
        await paymentTransactionsComponentsPage.clickOnCreateButton();
        paymentTransactionsUpdatePage = new PaymentTransactionsUpdatePage();
        expect(await paymentTransactionsUpdatePage.getPageTitle()).to.eq('resourceApp.paymentTransactions.home.createOrEditLabel');
        await paymentTransactionsUpdatePage.cancel();
    });

    it('should create and save PaymentTransactions', async () => {
        const nbButtonsBeforeCreate = await paymentTransactionsComponentsPage.countDeleteButtons();

        await paymentTransactionsComponentsPage.clickOnCreateButton();
        await promise.all([
            paymentTransactionsUpdatePage.setReturnedCompletedPaymentDataInput('returnedCompletedPaymentData'),
            paymentTransactionsUpdatePage.paymentOnOrderSelectLastOption()
        ]);
        expect(await paymentTransactionsUpdatePage.getReturnedCompletedPaymentDataInput()).to.eq('returnedCompletedPaymentData');
        await paymentTransactionsUpdatePage.save();
        expect(await paymentTransactionsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await paymentTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PaymentTransactions', async () => {
        const nbButtonsBeforeDelete = await paymentTransactionsComponentsPage.countDeleteButtons();
        await paymentTransactionsComponentsPage.clickOnLastDeleteButton();

        paymentTransactionsDeleteDialog = new PaymentTransactionsDeleteDialog();
        expect(await paymentTransactionsDeleteDialog.getDialogTitle()).to.eq('resourceApp.paymentTransactions.delete.question');
        await paymentTransactionsDeleteDialog.clickOnConfirmButton();

        expect(await paymentTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
