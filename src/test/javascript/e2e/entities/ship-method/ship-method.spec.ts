/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ShipMethodComponentsPage, ShipMethodDeleteDialog, ShipMethodUpdatePage } from './ship-method.page-object';

const expect = chai.expect;

describe('ShipMethod e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let shipMethodUpdatePage: ShipMethodUpdatePage;
    let shipMethodComponentsPage: ShipMethodComponentsPage;
    let shipMethodDeleteDialog: ShipMethodDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ShipMethods', async () => {
        await navBarPage.goToEntity('ship-method');
        shipMethodComponentsPage = new ShipMethodComponentsPage();
        await browser.wait(ec.visibilityOf(shipMethodComponentsPage.title), 5000);
        expect(await shipMethodComponentsPage.getTitle()).to.eq('resourceApp.shipMethod.home.title');
    });

    it('should load create ShipMethod page', async () => {
        await shipMethodComponentsPage.clickOnCreateButton();
        shipMethodUpdatePage = new ShipMethodUpdatePage();
        expect(await shipMethodUpdatePage.getPageTitle()).to.eq('resourceApp.shipMethod.home.createOrEditLabel');
        await shipMethodUpdatePage.cancel();
    });

    it('should create and save ShipMethods', async () => {
        const nbButtonsBeforeCreate = await shipMethodComponentsPage.countDeleteButtons();

        await shipMethodComponentsPage.clickOnCreateButton();
        await promise.all([shipMethodUpdatePage.setShipMethodNameInput('shipMethodName')]);
        expect(await shipMethodUpdatePage.getShipMethodNameInput()).to.eq('shipMethodName');
        await shipMethodUpdatePage.save();
        expect(await shipMethodUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await shipMethodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ShipMethod', async () => {
        const nbButtonsBeforeDelete = await shipMethodComponentsPage.countDeleteButtons();
        await shipMethodComponentsPage.clickOnLastDeleteButton();

        shipMethodDeleteDialog = new ShipMethodDeleteDialog();
        expect(await shipMethodDeleteDialog.getDialogTitle()).to.eq('resourceApp.shipMethod.delete.question');
        await shipMethodDeleteDialog.clickOnConfirmButton();

        expect(await shipMethodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
