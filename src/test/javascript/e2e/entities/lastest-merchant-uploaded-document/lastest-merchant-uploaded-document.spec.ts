/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    LastestMerchantUploadedDocumentComponentsPage,
    LastestMerchantUploadedDocumentDeleteDialog,
    LastestMerchantUploadedDocumentUpdatePage
} from './lastest-merchant-uploaded-document.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('LastestMerchantUploadedDocument e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let lastestMerchantUploadedDocumentUpdatePage: LastestMerchantUploadedDocumentUpdatePage;
    let lastestMerchantUploadedDocumentComponentsPage: LastestMerchantUploadedDocumentComponentsPage;
    let lastestMerchantUploadedDocumentDeleteDialog: LastestMerchantUploadedDocumentDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load LastestMerchantUploadedDocuments', async () => {
        await navBarPage.goToEntity('lastest-merchant-uploaded-document');
        lastestMerchantUploadedDocumentComponentsPage = new LastestMerchantUploadedDocumentComponentsPage();
        await browser.wait(ec.visibilityOf(lastestMerchantUploadedDocumentComponentsPage.title), 5000);
        expect(await lastestMerchantUploadedDocumentComponentsPage.getTitle()).to.eq(
            'resourceApp.lastestMerchantUploadedDocument.home.title'
        );
    });

    it('should load create LastestMerchantUploadedDocument page', async () => {
        await lastestMerchantUploadedDocumentComponentsPage.clickOnCreateButton();
        lastestMerchantUploadedDocumentUpdatePage = new LastestMerchantUploadedDocumentUpdatePage();
        expect(await lastestMerchantUploadedDocumentUpdatePage.getPageTitle()).to.eq(
            'resourceApp.lastestMerchantUploadedDocument.home.createOrEditLabel'
        );
        await lastestMerchantUploadedDocumentUpdatePage.cancel();
    });

    it('should create and save LastestMerchantUploadedDocuments', async () => {
        const nbButtonsBeforeCreate = await lastestMerchantUploadedDocumentComponentsPage.countDeleteButtons();

        await lastestMerchantUploadedDocumentComponentsPage.clickOnCreateButton();
        await promise.all([lastestMerchantUploadedDocumentUpdatePage.setProductCreateTemplateInput(absolutePath)]);
        expect(await lastestMerchantUploadedDocumentUpdatePage.getProductCreateTemplateInput()).to.endsWith(fileNameToUpload);
        await lastestMerchantUploadedDocumentUpdatePage.save();
        expect(await lastestMerchantUploadedDocumentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await lastestMerchantUploadedDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last LastestMerchantUploadedDocument', async () => {
        const nbButtonsBeforeDelete = await lastestMerchantUploadedDocumentComponentsPage.countDeleteButtons();
        await lastestMerchantUploadedDocumentComponentsPage.clickOnLastDeleteButton();

        lastestMerchantUploadedDocumentDeleteDialog = new LastestMerchantUploadedDocumentDeleteDialog();
        expect(await lastestMerchantUploadedDocumentDeleteDialog.getDialogTitle()).to.eq(
            'resourceApp.lastestMerchantUploadedDocument.delete.question'
        );
        await lastestMerchantUploadedDocumentDeleteDialog.clickOnConfirmButton();

        expect(await lastestMerchantUploadedDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
