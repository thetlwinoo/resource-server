/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BarcodeTypesComponentsPage, BarcodeTypesDeleteDialog, BarcodeTypesUpdatePage } from './barcode-types.page-object';

const expect = chai.expect;

describe('BarcodeTypes e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let barcodeTypesUpdatePage: BarcodeTypesUpdatePage;
    let barcodeTypesComponentsPage: BarcodeTypesComponentsPage;
    let barcodeTypesDeleteDialog: BarcodeTypesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BarcodeTypes', async () => {
        await navBarPage.goToEntity('barcode-types');
        barcodeTypesComponentsPage = new BarcodeTypesComponentsPage();
        await browser.wait(ec.visibilityOf(barcodeTypesComponentsPage.title), 5000);
        expect(await barcodeTypesComponentsPage.getTitle()).to.eq('resourceApp.barcodeTypes.home.title');
    });

    it('should load create BarcodeTypes page', async () => {
        await barcodeTypesComponentsPage.clickOnCreateButton();
        barcodeTypesUpdatePage = new BarcodeTypesUpdatePage();
        expect(await barcodeTypesUpdatePage.getPageTitle()).to.eq('resourceApp.barcodeTypes.home.createOrEditLabel');
        await barcodeTypesUpdatePage.cancel();
    });

    it('should create and save BarcodeTypes', async () => {
        const nbButtonsBeforeCreate = await barcodeTypesComponentsPage.countDeleteButtons();

        await barcodeTypesComponentsPage.clickOnCreateButton();
        await promise.all([barcodeTypesUpdatePage.setBarcodeTypeNameInput('barcodeTypeName')]);
        expect(await barcodeTypesUpdatePage.getBarcodeTypeNameInput()).to.eq('barcodeTypeName');
        await barcodeTypesUpdatePage.save();
        expect(await barcodeTypesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await barcodeTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last BarcodeTypes', async () => {
        const nbButtonsBeforeDelete = await barcodeTypesComponentsPage.countDeleteButtons();
        await barcodeTypesComponentsPage.clickOnLastDeleteButton();

        barcodeTypesDeleteDialog = new BarcodeTypesDeleteDialog();
        expect(await barcodeTypesDeleteDialog.getDialogTitle()).to.eq('resourceApp.barcodeTypes.delete.question');
        await barcodeTypesDeleteDialog.clickOnConfirmButton();

        expect(await barcodeTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
