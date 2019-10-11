/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    UploadActionTypesComponentsPage,
    UploadActionTypesDeleteDialog,
    UploadActionTypesUpdatePage
} from './upload-action-types.page-object';

const expect = chai.expect;

describe('UploadActionTypes e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let uploadActionTypesUpdatePage: UploadActionTypesUpdatePage;
    let uploadActionTypesComponentsPage: UploadActionTypesComponentsPage;
    let uploadActionTypesDeleteDialog: UploadActionTypesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UploadActionTypes', async () => {
        await navBarPage.goToEntity('upload-action-types');
        uploadActionTypesComponentsPage = new UploadActionTypesComponentsPage();
        await browser.wait(ec.visibilityOf(uploadActionTypesComponentsPage.title), 5000);
        expect(await uploadActionTypesComponentsPage.getTitle()).to.eq('resourceApp.uploadActionTypes.home.title');
    });

    it('should load create UploadActionTypes page', async () => {
        await uploadActionTypesComponentsPage.clickOnCreateButton();
        uploadActionTypesUpdatePage = new UploadActionTypesUpdatePage();
        expect(await uploadActionTypesUpdatePage.getPageTitle()).to.eq('resourceApp.uploadActionTypes.home.createOrEditLabel');
        await uploadActionTypesUpdatePage.cancel();
    });

    it('should create and save UploadActionTypes', async () => {
        const nbButtonsBeforeCreate = await uploadActionTypesComponentsPage.countDeleteButtons();

        await uploadActionTypesComponentsPage.clickOnCreateButton();
        await promise.all([uploadActionTypesUpdatePage.setActionTypeNameInput('actionTypeName')]);
        expect(await uploadActionTypesUpdatePage.getActionTypeNameInput()).to.eq('actionTypeName');
        await uploadActionTypesUpdatePage.save();
        expect(await uploadActionTypesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await uploadActionTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UploadActionTypes', async () => {
        const nbButtonsBeforeDelete = await uploadActionTypesComponentsPage.countDeleteButtons();
        await uploadActionTypesComponentsPage.clickOnLastDeleteButton();

        uploadActionTypesDeleteDialog = new UploadActionTypesDeleteDialog();
        expect(await uploadActionTypesDeleteDialog.getDialogTitle()).to.eq('resourceApp.uploadActionTypes.delete.question');
        await uploadActionTypesDeleteDialog.clickOnConfirmButton();

        expect(await uploadActionTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
