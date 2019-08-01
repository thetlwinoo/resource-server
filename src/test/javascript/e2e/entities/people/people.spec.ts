/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PeopleComponentsPage, PeopleDeleteDialog, PeopleUpdatePage } from './people.page-object';

const expect = chai.expect;

describe('People e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let peopleUpdatePage: PeopleUpdatePage;
    let peopleComponentsPage: PeopleComponentsPage;
    let peopleDeleteDialog: PeopleDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load People', async () => {
        await navBarPage.goToEntity('people');
        peopleComponentsPage = new PeopleComponentsPage();
        await browser.wait(ec.visibilityOf(peopleComponentsPage.title), 5000);
        expect(await peopleComponentsPage.getTitle()).to.eq('resourceApp.people.home.title');
    });

    it('should load create People page', async () => {
        await peopleComponentsPage.clickOnCreateButton();
        peopleUpdatePage = new PeopleUpdatePage();
        expect(await peopleUpdatePage.getPageTitle()).to.eq('resourceApp.people.home.createOrEditLabel');
        await peopleUpdatePage.cancel();
    });

    it('should create and save People', async () => {
        const nbButtonsBeforeCreate = await peopleComponentsPage.countDeleteButtons();

        await peopleComponentsPage.clickOnCreateButton();
        await promise.all([
            peopleUpdatePage.setFullNameInput('fullName'),
            peopleUpdatePage.setPreferredNameInput('preferredName'),
            peopleUpdatePage.setSearchNameInput('searchName'),
            peopleUpdatePage.setLogonNameInput('logonName'),
            peopleUpdatePage.setEmailPromotionInput('5'),
            peopleUpdatePage.setUserPreferencesInput('userPreferences'),
            peopleUpdatePage.setPhoneNumberInput('phoneNumber'),
            peopleUpdatePage.setEmailAddressInput('emailAddress'),
            peopleUpdatePage.setPhotoInput('photo'),
            peopleUpdatePage.setCustomFieldsInput('customFields'),
            peopleUpdatePage.setOtherLanguagesInput('otherLanguages'),
            peopleUpdatePage.setValidFromInput('2000-12-31'),
            peopleUpdatePage.setValidToInput('2000-12-31')
        ]);
        expect(await peopleUpdatePage.getFullNameInput()).to.eq('fullName');
        expect(await peopleUpdatePage.getPreferredNameInput()).to.eq('preferredName');
        expect(await peopleUpdatePage.getSearchNameInput()).to.eq('searchName');
        const selectedIsPermittedToLogon = peopleUpdatePage.getIsPermittedToLogonInput();
        if (await selectedIsPermittedToLogon.isSelected()) {
            await peopleUpdatePage.getIsPermittedToLogonInput().click();
            expect(await peopleUpdatePage.getIsPermittedToLogonInput().isSelected()).to.be.false;
        } else {
            await peopleUpdatePage.getIsPermittedToLogonInput().click();
            expect(await peopleUpdatePage.getIsPermittedToLogonInput().isSelected()).to.be.true;
        }
        expect(await peopleUpdatePage.getLogonNameInput()).to.eq('logonName');
        const selectedIsExternalLogonProvider = peopleUpdatePage.getIsExternalLogonProviderInput();
        if (await selectedIsExternalLogonProvider.isSelected()) {
            await peopleUpdatePage.getIsExternalLogonProviderInput().click();
            expect(await peopleUpdatePage.getIsExternalLogonProviderInput().isSelected()).to.be.false;
        } else {
            await peopleUpdatePage.getIsExternalLogonProviderInput().click();
            expect(await peopleUpdatePage.getIsExternalLogonProviderInput().isSelected()).to.be.true;
        }
        const selectedIsSystemUser = peopleUpdatePage.getIsSystemUserInput();
        if (await selectedIsSystemUser.isSelected()) {
            await peopleUpdatePage.getIsSystemUserInput().click();
            expect(await peopleUpdatePage.getIsSystemUserInput().isSelected()).to.be.false;
        } else {
            await peopleUpdatePage.getIsSystemUserInput().click();
            expect(await peopleUpdatePage.getIsSystemUserInput().isSelected()).to.be.true;
        }
        const selectedIsEmployee = peopleUpdatePage.getIsEmployeeInput();
        if (await selectedIsEmployee.isSelected()) {
            await peopleUpdatePage.getIsEmployeeInput().click();
            expect(await peopleUpdatePage.getIsEmployeeInput().isSelected()).to.be.false;
        } else {
            await peopleUpdatePage.getIsEmployeeInput().click();
            expect(await peopleUpdatePage.getIsEmployeeInput().isSelected()).to.be.true;
        }
        const selectedIsSalesPerson = peopleUpdatePage.getIsSalesPersonInput();
        if (await selectedIsSalesPerson.isSelected()) {
            await peopleUpdatePage.getIsSalesPersonInput().click();
            expect(await peopleUpdatePage.getIsSalesPersonInput().isSelected()).to.be.false;
        } else {
            await peopleUpdatePage.getIsSalesPersonInput().click();
            expect(await peopleUpdatePage.getIsSalesPersonInput().isSelected()).to.be.true;
        }
        const selectedIsGuestUser = peopleUpdatePage.getIsGuestUserInput();
        if (await selectedIsGuestUser.isSelected()) {
            await peopleUpdatePage.getIsGuestUserInput().click();
            expect(await peopleUpdatePage.getIsGuestUserInput().isSelected()).to.be.false;
        } else {
            await peopleUpdatePage.getIsGuestUserInput().click();
            expect(await peopleUpdatePage.getIsGuestUserInput().isSelected()).to.be.true;
        }
        expect(await peopleUpdatePage.getEmailPromotionInput()).to.eq('5');
        expect(await peopleUpdatePage.getUserPreferencesInput()).to.eq('userPreferences');
        expect(await peopleUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber');
        expect(await peopleUpdatePage.getEmailAddressInput()).to.eq('emailAddress');
        expect(await peopleUpdatePage.getPhotoInput()).to.eq('photo');
        expect(await peopleUpdatePage.getCustomFieldsInput()).to.eq('customFields');
        expect(await peopleUpdatePage.getOtherLanguagesInput()).to.eq('otherLanguages');
        expect(await peopleUpdatePage.getValidFromInput()).to.eq('2000-12-31');
        expect(await peopleUpdatePage.getValidToInput()).to.eq('2000-12-31');
        await peopleUpdatePage.save();
        expect(await peopleUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await peopleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last People', async () => {
        const nbButtonsBeforeDelete = await peopleComponentsPage.countDeleteButtons();
        await peopleComponentsPage.clickOnLastDeleteButton();

        peopleDeleteDialog = new PeopleDeleteDialog();
        expect(await peopleDeleteDialog.getDialogTitle()).to.eq('resourceApp.people.delete.question');
        await peopleDeleteDialog.clickOnConfirmButton();

        expect(await peopleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
