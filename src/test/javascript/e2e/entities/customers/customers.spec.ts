/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CustomersComponentsPage, CustomersDeleteDialog, CustomersUpdatePage } from './customers.page-object';

const expect = chai.expect;

describe('Customers e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let customersUpdatePage: CustomersUpdatePage;
    let customersComponentsPage: CustomersComponentsPage;
    let customersDeleteDialog: CustomersDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Customers', async () => {
        await navBarPage.goToEntity('customers');
        customersComponentsPage = new CustomersComponentsPage();
        await browser.wait(ec.visibilityOf(customersComponentsPage.title), 5000);
        expect(await customersComponentsPage.getTitle()).to.eq('resourceApp.customers.home.title');
    });

    it('should load create Customers page', async () => {
        await customersComponentsPage.clickOnCreateButton();
        customersUpdatePage = new CustomersUpdatePage();
        expect(await customersUpdatePage.getPageTitle()).to.eq('resourceApp.customers.home.createOrEditLabel');
        await customersUpdatePage.cancel();
    });

    it('should create and save Customers', async () => {
        const nbButtonsBeforeCreate = await customersComponentsPage.countDeleteButtons();

        await customersComponentsPage.clickOnCreateButton();
        await promise.all([customersUpdatePage.setAccountNumberInput('accountNumber'), customersUpdatePage.personSelectLastOption()]);
        expect(await customersUpdatePage.getAccountNumberInput()).to.eq('accountNumber');
        await customersUpdatePage.save();
        expect(await customersUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await customersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Customers', async () => {
        const nbButtonsBeforeDelete = await customersComponentsPage.countDeleteButtons();
        await customersComponentsPage.clickOnLastDeleteButton();

        customersDeleteDialog = new CustomersDeleteDialog();
        expect(await customersDeleteDialog.getDialogTitle()).to.eq('resourceApp.customers.delete.question');
        await customersDeleteDialog.clickOnConfirmButton();

        expect(await customersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
