/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoicesComponentsPage, InvoicesDeleteDialog, InvoicesUpdatePage } from './invoices.page-object';

const expect = chai.expect;

describe('Invoices e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let invoicesUpdatePage: InvoicesUpdatePage;
    let invoicesComponentsPage: InvoicesComponentsPage;
    let invoicesDeleteDialog: InvoicesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Invoices', async () => {
        await navBarPage.goToEntity('invoices');
        invoicesComponentsPage = new InvoicesComponentsPage();
        await browser.wait(ec.visibilityOf(invoicesComponentsPage.title), 5000);
        expect(await invoicesComponentsPage.getTitle()).to.eq('resourceApp.invoices.home.title');
    });

    it('should load create Invoices page', async () => {
        await invoicesComponentsPage.clickOnCreateButton();
        invoicesUpdatePage = new InvoicesUpdatePage();
        expect(await invoicesUpdatePage.getPageTitle()).to.eq('resourceApp.invoices.home.createOrEditLabel');
        await invoicesUpdatePage.cancel();
    });

    it('should create and save Invoices', async () => {
        const nbButtonsBeforeCreate = await invoicesComponentsPage.countDeleteButtons();

        await invoicesComponentsPage.clickOnCreateButton();
        await promise.all([
            invoicesUpdatePage.setInvoiceDateInput('2000-12-31'),
            invoicesUpdatePage.setCustomerPurchaseOrderNumberInput('customerPurchaseOrderNumber'),
            invoicesUpdatePage.setCreditNoteReasonInput('creditNoteReason'),
            invoicesUpdatePage.setCommentsInput('comments'),
            invoicesUpdatePage.setDeliveryInstructionsInput('deliveryInstructions'),
            invoicesUpdatePage.setInternalCommentsInput('internalComments'),
            invoicesUpdatePage.setTotalDryItemsInput('5'),
            invoicesUpdatePage.setTotalChillerItemsInput('5'),
            invoicesUpdatePage.setDeliveryRunInput('deliveryRun'),
            invoicesUpdatePage.setRunPositionInput('runPosition'),
            invoicesUpdatePage.setReturnedDeliveryDataInput('returnedDeliveryData'),
            invoicesUpdatePage.setConfirmedDeliveryTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            invoicesUpdatePage.setConfirmedReceivedByInput('confirmedReceivedBy'),
            invoicesUpdatePage.contactPersonSelectLastOption(),
            invoicesUpdatePage.salespersonPersonSelectLastOption(),
            invoicesUpdatePage.packedByPersonSelectLastOption(),
            invoicesUpdatePage.accountsPersonSelectLastOption(),
            invoicesUpdatePage.customerSelectLastOption(),
            invoicesUpdatePage.billToCustomerSelectLastOption(),
            invoicesUpdatePage.deliveryMethodSelectLastOption(),
            invoicesUpdatePage.orderSelectLastOption()
        ]);
        expect(await invoicesUpdatePage.getInvoiceDateInput()).to.eq('2000-12-31');
        expect(await invoicesUpdatePage.getCustomerPurchaseOrderNumberInput()).to.eq('customerPurchaseOrderNumber');
        const selectedIsCreditNote = invoicesUpdatePage.getIsCreditNoteInput();
        if (await selectedIsCreditNote.isSelected()) {
            await invoicesUpdatePage.getIsCreditNoteInput().click();
            expect(await invoicesUpdatePage.getIsCreditNoteInput().isSelected()).to.be.false;
        } else {
            await invoicesUpdatePage.getIsCreditNoteInput().click();
            expect(await invoicesUpdatePage.getIsCreditNoteInput().isSelected()).to.be.true;
        }
        expect(await invoicesUpdatePage.getCreditNoteReasonInput()).to.eq('creditNoteReason');
        expect(await invoicesUpdatePage.getCommentsInput()).to.eq('comments');
        expect(await invoicesUpdatePage.getDeliveryInstructionsInput()).to.eq('deliveryInstructions');
        expect(await invoicesUpdatePage.getInternalCommentsInput()).to.eq('internalComments');
        expect(await invoicesUpdatePage.getTotalDryItemsInput()).to.eq('5');
        expect(await invoicesUpdatePage.getTotalChillerItemsInput()).to.eq('5');
        expect(await invoicesUpdatePage.getDeliveryRunInput()).to.eq('deliveryRun');
        expect(await invoicesUpdatePage.getRunPositionInput()).to.eq('runPosition');
        expect(await invoicesUpdatePage.getReturnedDeliveryDataInput()).to.eq('returnedDeliveryData');
        expect(await invoicesUpdatePage.getConfirmedDeliveryTimeInput()).to.contain('2001-01-01T02:30');
        expect(await invoicesUpdatePage.getConfirmedReceivedByInput()).to.eq('confirmedReceivedBy');
        await invoicesUpdatePage.save();
        expect(await invoicesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await invoicesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Invoices', async () => {
        const nbButtonsBeforeDelete = await invoicesComponentsPage.countDeleteButtons();
        await invoicesComponentsPage.clickOnLastDeleteButton();

        invoicesDeleteDialog = new InvoicesDeleteDialog();
        expect(await invoicesDeleteDialog.getDialogTitle()).to.eq('resourceApp.invoices.delete.question');
        await invoicesDeleteDialog.clickOnConfirmButton();

        expect(await invoicesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
