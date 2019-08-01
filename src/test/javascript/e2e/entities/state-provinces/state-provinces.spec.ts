/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StateProvincesComponentsPage, StateProvincesDeleteDialog, StateProvincesUpdatePage } from './state-provinces.page-object';

const expect = chai.expect;

describe('StateProvinces e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let stateProvincesUpdatePage: StateProvincesUpdatePage;
    let stateProvincesComponentsPage: StateProvincesComponentsPage;
    let stateProvincesDeleteDialog: StateProvincesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StateProvinces', async () => {
        await navBarPage.goToEntity('state-provinces');
        stateProvincesComponentsPage = new StateProvincesComponentsPage();
        await browser.wait(ec.visibilityOf(stateProvincesComponentsPage.title), 5000);
        expect(await stateProvincesComponentsPage.getTitle()).to.eq('resourceApp.stateProvinces.home.title');
    });

    it('should load create StateProvinces page', async () => {
        await stateProvincesComponentsPage.clickOnCreateButton();
        stateProvincesUpdatePage = new StateProvincesUpdatePage();
        expect(await stateProvincesUpdatePage.getPageTitle()).to.eq('resourceApp.stateProvinces.home.createOrEditLabel');
        await stateProvincesUpdatePage.cancel();
    });

    it('should create and save StateProvinces', async () => {
        const nbButtonsBeforeCreate = await stateProvincesComponentsPage.countDeleteButtons();

        await stateProvincesComponentsPage.clickOnCreateButton();
        await promise.all([
            stateProvincesUpdatePage.setStateProvinceCodeInput('stateProvinceCode'),
            stateProvincesUpdatePage.setStateProvinceNameInput('stateProvinceName'),
            stateProvincesUpdatePage.setSalesTerritoryInput('salesTerritory'),
            stateProvincesUpdatePage.setBorderInput('border'),
            stateProvincesUpdatePage.setLatestRecordedPopulationInput('5'),
            stateProvincesUpdatePage.setValidFromInput('2000-12-31'),
            stateProvincesUpdatePage.setValidToInput('2000-12-31'),
            stateProvincesUpdatePage.countrySelectLastOption()
        ]);
        expect(await stateProvincesUpdatePage.getStateProvinceCodeInput()).to.eq('stateProvinceCode');
        expect(await stateProvincesUpdatePage.getStateProvinceNameInput()).to.eq('stateProvinceName');
        expect(await stateProvincesUpdatePage.getSalesTerritoryInput()).to.eq('salesTerritory');
        expect(await stateProvincesUpdatePage.getBorderInput()).to.eq('border');
        expect(await stateProvincesUpdatePage.getLatestRecordedPopulationInput()).to.eq('5');
        expect(await stateProvincesUpdatePage.getValidFromInput()).to.eq('2000-12-31');
        expect(await stateProvincesUpdatePage.getValidToInput()).to.eq('2000-12-31');
        await stateProvincesUpdatePage.save();
        expect(await stateProvincesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await stateProvincesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StateProvinces', async () => {
        const nbButtonsBeforeDelete = await stateProvincesComponentsPage.countDeleteButtons();
        await stateProvincesComponentsPage.clickOnLastDeleteButton();

        stateProvincesDeleteDialog = new StateProvincesDeleteDialog();
        expect(await stateProvincesDeleteDialog.getDialogTitle()).to.eq('resourceApp.stateProvinces.delete.question');
        await stateProvincesDeleteDialog.clickOnConfirmButton();

        expect(await stateProvincesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
