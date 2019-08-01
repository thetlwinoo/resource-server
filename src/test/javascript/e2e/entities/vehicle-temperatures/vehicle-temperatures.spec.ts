/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    VehicleTemperaturesComponentsPage,
    VehicleTemperaturesDeleteDialog,
    VehicleTemperaturesUpdatePage
} from './vehicle-temperatures.page-object';

const expect = chai.expect;

describe('VehicleTemperatures e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let vehicleTemperaturesUpdatePage: VehicleTemperaturesUpdatePage;
    let vehicleTemperaturesComponentsPage: VehicleTemperaturesComponentsPage;
    let vehicleTemperaturesDeleteDialog: VehicleTemperaturesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load VehicleTemperatures', async () => {
        await navBarPage.goToEntity('vehicle-temperatures');
        vehicleTemperaturesComponentsPage = new VehicleTemperaturesComponentsPage();
        await browser.wait(ec.visibilityOf(vehicleTemperaturesComponentsPage.title), 5000);
        expect(await vehicleTemperaturesComponentsPage.getTitle()).to.eq('resourceApp.vehicleTemperatures.home.title');
    });

    it('should load create VehicleTemperatures page', async () => {
        await vehicleTemperaturesComponentsPage.clickOnCreateButton();
        vehicleTemperaturesUpdatePage = new VehicleTemperaturesUpdatePage();
        expect(await vehicleTemperaturesUpdatePage.getPageTitle()).to.eq('resourceApp.vehicleTemperatures.home.createOrEditLabel');
        await vehicleTemperaturesUpdatePage.cancel();
    });

    it('should create and save VehicleTemperatures', async () => {
        const nbButtonsBeforeCreate = await vehicleTemperaturesComponentsPage.countDeleteButtons();

        await vehicleTemperaturesComponentsPage.clickOnCreateButton();
        await promise.all([
            vehicleTemperaturesUpdatePage.setVehicleRegistrationInput('5'),
            vehicleTemperaturesUpdatePage.setChillerSensorNumberInput('chillerSensorNumber'),
            vehicleTemperaturesUpdatePage.setRecordedWhenInput('5'),
            vehicleTemperaturesUpdatePage.setTemperatureInput('5'),
            vehicleTemperaturesUpdatePage.setFullSensorDataInput('fullSensorData'),
            vehicleTemperaturesUpdatePage.setCompressedSensorDataInput('compressedSensorData')
        ]);
        expect(await vehicleTemperaturesUpdatePage.getVehicleRegistrationInput()).to.eq('5');
        expect(await vehicleTemperaturesUpdatePage.getChillerSensorNumberInput()).to.eq('chillerSensorNumber');
        expect(await vehicleTemperaturesUpdatePage.getRecordedWhenInput()).to.eq('5');
        expect(await vehicleTemperaturesUpdatePage.getTemperatureInput()).to.eq('5');
        const selectedIsCompressed = vehicleTemperaturesUpdatePage.getIsCompressedInput();
        if (await selectedIsCompressed.isSelected()) {
            await vehicleTemperaturesUpdatePage.getIsCompressedInput().click();
            expect(await vehicleTemperaturesUpdatePage.getIsCompressedInput().isSelected()).to.be.false;
        } else {
            await vehicleTemperaturesUpdatePage.getIsCompressedInput().click();
            expect(await vehicleTemperaturesUpdatePage.getIsCompressedInput().isSelected()).to.be.true;
        }
        expect(await vehicleTemperaturesUpdatePage.getFullSensorDataInput()).to.eq('fullSensorData');
        expect(await vehicleTemperaturesUpdatePage.getCompressedSensorDataInput()).to.eq('compressedSensorData');
        await vehicleTemperaturesUpdatePage.save();
        expect(await vehicleTemperaturesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await vehicleTemperaturesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last VehicleTemperatures', async () => {
        const nbButtonsBeforeDelete = await vehicleTemperaturesComponentsPage.countDeleteButtons();
        await vehicleTemperaturesComponentsPage.clickOnLastDeleteButton();

        vehicleTemperaturesDeleteDialog = new VehicleTemperaturesDeleteDialog();
        expect(await vehicleTemperaturesDeleteDialog.getDialogTitle()).to.eq('resourceApp.vehicleTemperatures.delete.question');
        await vehicleTemperaturesDeleteDialog.clickOnConfirmButton();

        expect(await vehicleTemperaturesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
