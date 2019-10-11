/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    UploadTransactionsComponentsPage,
    UploadTransactionsDeleteDialog,
    UploadTransactionsUpdatePage
} from './upload-transactions.page-object';

const expect = chai.expect;

describe('UploadTransactions e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let uploadTransactionsUpdatePage: UploadTransactionsUpdatePage;
    let uploadTransactionsComponentsPage: UploadTransactionsComponentsPage;
    let uploadTransactionsDeleteDialog: UploadTransactionsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UploadTransactions', async () => {
        await navBarPage.goToEntity('upload-transactions');
        uploadTransactionsComponentsPage = new UploadTransactionsComponentsPage();
        await browser.wait(ec.visibilityOf(uploadTransactionsComponentsPage.title), 5000);
        expect(await uploadTransactionsComponentsPage.getTitle()).to.eq('resourceApp.uploadTransactions.home.title');
    });

    it('should load create UploadTransactions page', async () => {
        await uploadTransactionsComponentsPage.clickOnCreateButton();
        uploadTransactionsUpdatePage = new UploadTransactionsUpdatePage();
        expect(await uploadTransactionsUpdatePage.getPageTitle()).to.eq('resourceApp.uploadTransactions.home.createOrEditLabel');
        await uploadTransactionsUpdatePage.cancel();
    });

    it('should create and save UploadTransactions', async () => {
        const nbButtonsBeforeCreate = await uploadTransactionsComponentsPage.countDeleteButtons();

        await uploadTransactionsComponentsPage.clickOnCreateButton();
        await promise.all([
            uploadTransactionsUpdatePage.setFileNameInput('fileName'),
            uploadTransactionsUpdatePage.setTemplateUrlInput('templateUrl'),
            uploadTransactionsUpdatePage.setStatusInput('5'),
            uploadTransactionsUpdatePage.setGeneratedCodeInput('generatedCode'),
            uploadTransactionsUpdatePage.supplierSelectLastOption(),
            uploadTransactionsUpdatePage.actionTypeSelectLastOption()
        ]);
        expect(await uploadTransactionsUpdatePage.getFileNameInput()).to.eq('fileName');
        expect(await uploadTransactionsUpdatePage.getTemplateUrlInput()).to.eq('templateUrl');
        expect(await uploadTransactionsUpdatePage.getStatusInput()).to.eq('5');
        expect(await uploadTransactionsUpdatePage.getGeneratedCodeInput()).to.eq('generatedCode');
        await uploadTransactionsUpdatePage.save();
        expect(await uploadTransactionsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await uploadTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UploadTransactions', async () => {
        const nbButtonsBeforeDelete = await uploadTransactionsComponentsPage.countDeleteButtons();
        await uploadTransactionsComponentsPage.clickOnLastDeleteButton();

        uploadTransactionsDeleteDialog = new UploadTransactionsDeleteDialog();
        expect(await uploadTransactionsDeleteDialog.getDialogTitle()).to.eq('resourceApp.uploadTransactions.delete.question');
        await uploadTransactionsDeleteDialog.clickOnConfirmButton();

        expect(await uploadTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
