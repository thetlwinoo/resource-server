/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    CustomerCategoriesComponentsPage,
    CustomerCategoriesDeleteDialog,
    CustomerCategoriesUpdatePage
} from './customer-categories.page-object';

const expect = chai.expect;

describe('CustomerCategories e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let customerCategoriesUpdatePage: CustomerCategoriesUpdatePage;
    let customerCategoriesComponentsPage: CustomerCategoriesComponentsPage;
    let customerCategoriesDeleteDialog: CustomerCategoriesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load CustomerCategories', async () => {
        await navBarPage.goToEntity('customer-categories');
        customerCategoriesComponentsPage = new CustomerCategoriesComponentsPage();
        await browser.wait(ec.visibilityOf(customerCategoriesComponentsPage.title), 5000);
        expect(await customerCategoriesComponentsPage.getTitle()).to.eq('resourceApp.customerCategories.home.title');
    });

    it('should load create CustomerCategories page', async () => {
        await customerCategoriesComponentsPage.clickOnCreateButton();
        customerCategoriesUpdatePage = new CustomerCategoriesUpdatePage();
        expect(await customerCategoriesUpdatePage.getPageTitle()).to.eq('resourceApp.customerCategories.home.createOrEditLabel');
        await customerCategoriesUpdatePage.cancel();
    });

    it('should create and save CustomerCategories', async () => {
        const nbButtonsBeforeCreate = await customerCategoriesComponentsPage.countDeleteButtons();

        await customerCategoriesComponentsPage.clickOnCreateButton();
        await promise.all([
            customerCategoriesUpdatePage.setCustomerCategoryNameInput('customerCategoryName'),
            customerCategoriesUpdatePage.setValidFromInput('2000-12-31'),
            customerCategoriesUpdatePage.setValidToInput('2000-12-31')
        ]);
        expect(await customerCategoriesUpdatePage.getCustomerCategoryNameInput()).to.eq('customerCategoryName');
        expect(await customerCategoriesUpdatePage.getValidFromInput()).to.eq('2000-12-31');
        expect(await customerCategoriesUpdatePage.getValidToInput()).to.eq('2000-12-31');
        await customerCategoriesUpdatePage.save();
        expect(await customerCategoriesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await customerCategoriesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last CustomerCategories', async () => {
        const nbButtonsBeforeDelete = await customerCategoriesComponentsPage.countDeleteButtons();
        await customerCategoriesComponentsPage.clickOnLastDeleteButton();

        customerCategoriesDeleteDialog = new CustomerCategoriesDeleteDialog();
        expect(await customerCategoriesDeleteDialog.getDialogTitle()).to.eq('resourceApp.customerCategories.delete.question');
        await customerCategoriesDeleteDialog.clickOnConfirmButton();

        expect(await customerCategoriesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
