/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    BusinessEntityContactComponentsPage,
    BusinessEntityContactDeleteDialog,
    BusinessEntityContactUpdatePage
} from './business-entity-contact.page-object';

const expect = chai.expect;

describe('BusinessEntityContact e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let businessEntityContactUpdatePage: BusinessEntityContactUpdatePage;
    let businessEntityContactComponentsPage: BusinessEntityContactComponentsPage;
    let businessEntityContactDeleteDialog: BusinessEntityContactDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BusinessEntityContacts', async () => {
        await navBarPage.goToEntity('business-entity-contact');
        businessEntityContactComponentsPage = new BusinessEntityContactComponentsPage();
        await browser.wait(ec.visibilityOf(businessEntityContactComponentsPage.title), 5000);
        expect(await businessEntityContactComponentsPage.getTitle()).to.eq('resourceApp.businessEntityContact.home.title');
    });

    it('should load create BusinessEntityContact page', async () => {
        await businessEntityContactComponentsPage.clickOnCreateButton();
        businessEntityContactUpdatePage = new BusinessEntityContactUpdatePage();
        expect(await businessEntityContactUpdatePage.getPageTitle()).to.eq('resourceApp.businessEntityContact.home.createOrEditLabel');
        await businessEntityContactUpdatePage.cancel();
    });

    it('should create and save BusinessEntityContacts', async () => {
        const nbButtonsBeforeCreate = await businessEntityContactComponentsPage.countDeleteButtons();

        await businessEntityContactComponentsPage.clickOnCreateButton();
        await promise.all([
            businessEntityContactUpdatePage.personSelectLastOption(),
            businessEntityContactUpdatePage.contactTypeSelectLastOption()
        ]);
        await businessEntityContactUpdatePage.save();
        expect(await businessEntityContactUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await businessEntityContactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last BusinessEntityContact', async () => {
        const nbButtonsBeforeDelete = await businessEntityContactComponentsPage.countDeleteButtons();
        await businessEntityContactComponentsPage.clickOnLastDeleteButton();

        businessEntityContactDeleteDialog = new BusinessEntityContactDeleteDialog();
        expect(await businessEntityContactDeleteDialog.getDialogTitle()).to.eq('resourceApp.businessEntityContact.delete.question');
        await businessEntityContactDeleteDialog.clickOnConfirmButton();

        expect(await businessEntityContactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
