/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CountriesComponentsPage, CountriesDeleteDialog, CountriesUpdatePage } from './countries.page-object';

const expect = chai.expect;

describe('Countries e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let countriesUpdatePage: CountriesUpdatePage;
    let countriesComponentsPage: CountriesComponentsPage;
    let countriesDeleteDialog: CountriesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Countries', async () => {
        await navBarPage.goToEntity('countries');
        countriesComponentsPage = new CountriesComponentsPage();
        await browser.wait(ec.visibilityOf(countriesComponentsPage.title), 5000);
        expect(await countriesComponentsPage.getTitle()).to.eq('resourceApp.countries.home.title');
    });

    it('should load create Countries page', async () => {
        await countriesComponentsPage.clickOnCreateButton();
        countriesUpdatePage = new CountriesUpdatePage();
        expect(await countriesUpdatePage.getPageTitle()).to.eq('resourceApp.countries.home.createOrEditLabel');
        await countriesUpdatePage.cancel();
    });

    it('should create and save Countries', async () => {
        const nbButtonsBeforeCreate = await countriesComponentsPage.countDeleteButtons();

        await countriesComponentsPage.clickOnCreateButton();
        await promise.all([
            countriesUpdatePage.setCountryNameInput('countryName'),
            countriesUpdatePage.setFormalNameInput('formalName'),
            countriesUpdatePage.setIsoAplha3CodeInput('isoAplha3Code'),
            countriesUpdatePage.setIsoNumericCodeInput('5'),
            countriesUpdatePage.setCountryTypeInput('countryType'),
            countriesUpdatePage.setLatestRecordedPopulationInput('5'),
            countriesUpdatePage.setContinentInput('continent'),
            countriesUpdatePage.setRegionInput('region'),
            countriesUpdatePage.setSubregionInput('subregion'),
            countriesUpdatePage.setBorderInput('border'),
            countriesUpdatePage.setValidFromInput('2000-12-31'),
            countriesUpdatePage.setValidToInput('2000-12-31')
        ]);
        expect(await countriesUpdatePage.getCountryNameInput()).to.eq('countryName');
        expect(await countriesUpdatePage.getFormalNameInput()).to.eq('formalName');
        expect(await countriesUpdatePage.getIsoAplha3CodeInput()).to.eq('isoAplha3Code');
        expect(await countriesUpdatePage.getIsoNumericCodeInput()).to.eq('5');
        expect(await countriesUpdatePage.getCountryTypeInput()).to.eq('countryType');
        expect(await countriesUpdatePage.getLatestRecordedPopulationInput()).to.eq('5');
        expect(await countriesUpdatePage.getContinentInput()).to.eq('continent');
        expect(await countriesUpdatePage.getRegionInput()).to.eq('region');
        expect(await countriesUpdatePage.getSubregionInput()).to.eq('subregion');
        expect(await countriesUpdatePage.getBorderInput()).to.eq('border');
        expect(await countriesUpdatePage.getValidFromInput()).to.eq('2000-12-31');
        expect(await countriesUpdatePage.getValidToInput()).to.eq('2000-12-31');
        await countriesUpdatePage.save();
        expect(await countriesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await countriesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Countries', async () => {
        const nbButtonsBeforeDelete = await countriesComponentsPage.countDeleteButtons();
        await countriesComponentsPage.clickOnLastDeleteButton();

        countriesDeleteDialog = new CountriesDeleteDialog();
        expect(await countriesDeleteDialog.getDialogTitle()).to.eq('resourceApp.countries.delete.question');
        await countriesDeleteDialog.clickOnConfirmButton();

        expect(await countriesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
