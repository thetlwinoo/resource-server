/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LocationsComponentsPage, LocationsDeleteDialog, LocationsUpdatePage } from './locations.page-object';

const expect = chai.expect;

describe('Locations e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let locationsUpdatePage: LocationsUpdatePage;
    let locationsComponentsPage: LocationsComponentsPage;
    let locationsDeleteDialog: LocationsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Locations', async () => {
        await navBarPage.goToEntity('locations');
        locationsComponentsPage = new LocationsComponentsPage();
        await browser.wait(ec.visibilityOf(locationsComponentsPage.title), 5000);
        expect(await locationsComponentsPage.getTitle()).to.eq('resourceApp.locations.home.title');
    });

    it('should load create Locations page', async () => {
        await locationsComponentsPage.clickOnCreateButton();
        locationsUpdatePage = new LocationsUpdatePage();
        expect(await locationsUpdatePage.getPageTitle()).to.eq('resourceApp.locations.home.createOrEditLabel');
        await locationsUpdatePage.cancel();
    });

    it('should create and save Locations', async () => {
        const nbButtonsBeforeCreate = await locationsComponentsPage.countDeleteButtons();

        await locationsComponentsPage.clickOnCreateButton();
        await promise.all([
            locationsUpdatePage.setLocationNameInput('locationName'),
            locationsUpdatePage.setCostRateInput('5'),
            locationsUpdatePage.setAvailabilityInput('5')
        ]);
        expect(await locationsUpdatePage.getLocationNameInput()).to.eq('locationName');
        expect(await locationsUpdatePage.getCostRateInput()).to.eq('5');
        expect(await locationsUpdatePage.getAvailabilityInput()).to.eq('5');
        await locationsUpdatePage.save();
        expect(await locationsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await locationsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Locations', async () => {
        const nbButtonsBeforeDelete = await locationsComponentsPage.countDeleteButtons();
        await locationsComponentsPage.clickOnLastDeleteButton();

        locationsDeleteDialog = new LocationsDeleteDialog();
        expect(await locationsDeleteDialog.getDialogTitle()).to.eq('resourceApp.locations.delete.question');
        await locationsDeleteDialog.clickOnConfirmButton();

        expect(await locationsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
