/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AddressesComponentsPage, AddressesDeleteDialog, AddressesUpdatePage } from './addresses.page-object';

const expect = chai.expect;

describe('Addresses e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let addressesUpdatePage: AddressesUpdatePage;
    let addressesComponentsPage: AddressesComponentsPage;
    let addressesDeleteDialog: AddressesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Addresses', async () => {
        await navBarPage.goToEntity('addresses');
        addressesComponentsPage = new AddressesComponentsPage();
        await browser.wait(ec.visibilityOf(addressesComponentsPage.title), 5000);
        expect(await addressesComponentsPage.getTitle()).to.eq('resourceApp.addresses.home.title');
    });

    it('should load create Addresses page', async () => {
        await addressesComponentsPage.clickOnCreateButton();
        addressesUpdatePage = new AddressesUpdatePage();
        expect(await addressesUpdatePage.getPageTitle()).to.eq('resourceApp.addresses.home.createOrEditLabel');
        await addressesUpdatePage.cancel();
    });

    it('should create and save Addresses', async () => {
        const nbButtonsBeforeCreate = await addressesComponentsPage.countDeleteButtons();

        await addressesComponentsPage.clickOnCreateButton();
        await promise.all([
            addressesUpdatePage.setContactPersonInput('contactPerson'),
            addressesUpdatePage.setContactNumberInput('contactNumber'),
            addressesUpdatePage.setContactEmailAddressInput('contactEmailAddress'),
            addressesUpdatePage.setAddressLine1Input('addressLine1'),
            addressesUpdatePage.setAddressLine2Input('addressLine2'),
            addressesUpdatePage.setCityInput('city'),
            addressesUpdatePage.setPostalCodeInput('postalCode'),
            addressesUpdatePage.stateProvinceSelectLastOption(),
            addressesUpdatePage.addressTypeSelectLastOption(),
            addressesUpdatePage.personSelectLastOption()
        ]);
        expect(await addressesUpdatePage.getContactPersonInput()).to.eq('contactPerson');
        expect(await addressesUpdatePage.getContactNumberInput()).to.eq('contactNumber');
        expect(await addressesUpdatePage.getContactEmailAddressInput()).to.eq('contactEmailAddress');
        expect(await addressesUpdatePage.getAddressLine1Input()).to.eq('addressLine1');
        expect(await addressesUpdatePage.getAddressLine2Input()).to.eq('addressLine2');
        expect(await addressesUpdatePage.getCityInput()).to.eq('city');
        expect(await addressesUpdatePage.getPostalCodeInput()).to.eq('postalCode');
        const selectedDefaultInd = addressesUpdatePage.getDefaultIndInput();
        if (await selectedDefaultInd.isSelected()) {
            await addressesUpdatePage.getDefaultIndInput().click();
            expect(await addressesUpdatePage.getDefaultIndInput().isSelected()).to.be.false;
        } else {
            await addressesUpdatePage.getDefaultIndInput().click();
            expect(await addressesUpdatePage.getDefaultIndInput().isSelected()).to.be.true;
        }
        const selectedActiveInd = addressesUpdatePage.getActiveIndInput();
        if (await selectedActiveInd.isSelected()) {
            await addressesUpdatePage.getActiveIndInput().click();
            expect(await addressesUpdatePage.getActiveIndInput().isSelected()).to.be.false;
        } else {
            await addressesUpdatePage.getActiveIndInput().click();
            expect(await addressesUpdatePage.getActiveIndInput().isSelected()).to.be.true;
        }
        await addressesUpdatePage.save();
        expect(await addressesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await addressesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Addresses', async () => {
        const nbButtonsBeforeDelete = await addressesComponentsPage.countDeleteButtons();
        await addressesComponentsPage.clickOnLastDeleteButton();

        addressesDeleteDialog = new AddressesDeleteDialog();
        expect(await addressesDeleteDialog.getDialogTitle()).to.eq('resourceApp.addresses.delete.question');
        await addressesDeleteDialog.clickOnConfirmButton();

        expect(await addressesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
