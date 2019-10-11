/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    SupplierImportedDocumentComponentsPage,
    SupplierImportedDocumentDeleteDialog,
    SupplierImportedDocumentUpdatePage
} from './supplier-imported-document.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('SupplierImportedDocument e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let supplierImportedDocumentUpdatePage: SupplierImportedDocumentUpdatePage;
    let supplierImportedDocumentComponentsPage: SupplierImportedDocumentComponentsPage;
    let supplierImportedDocumentDeleteDialog: SupplierImportedDocumentDeleteDialog;
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

    it('should load SupplierImportedDocuments', async () => {
        await navBarPage.goToEntity('supplier-imported-document');
        supplierImportedDocumentComponentsPage = new SupplierImportedDocumentComponentsPage();
        await browser.wait(ec.visibilityOf(supplierImportedDocumentComponentsPage.title), 5000);
        expect(await supplierImportedDocumentComponentsPage.getTitle()).to.eq('resourceApp.supplierImportedDocument.home.title');
    });

    it('should load create SupplierImportedDocument page', async () => {
        await supplierImportedDocumentComponentsPage.clickOnCreateButton();
        supplierImportedDocumentUpdatePage = new SupplierImportedDocumentUpdatePage();
        expect(await supplierImportedDocumentUpdatePage.getPageTitle()).to.eq(
            'resourceApp.supplierImportedDocument.home.createOrEditLabel'
        );
        await supplierImportedDocumentUpdatePage.cancel();
    });

    it('should create and save SupplierImportedDocuments', async () => {
        const nbButtonsBeforeCreate = await supplierImportedDocumentComponentsPage.countDeleteButtons();

        await supplierImportedDocumentComponentsPage.clickOnCreateButton();
        await promise.all([
            supplierImportedDocumentUpdatePage.setImportedTemplateInput(absolutePath),
            supplierImportedDocumentUpdatePage.setImportedFailedTemplateInput(absolutePath),
            supplierImportedDocumentUpdatePage.uploadTransactionSelectLastOption()
        ]);
        expect(await supplierImportedDocumentUpdatePage.getImportedTemplateInput()).to.endsWith(fileNameToUpload);
        expect(await supplierImportedDocumentUpdatePage.getImportedFailedTemplateInput()).to.endsWith(fileNameToUpload);
        await supplierImportedDocumentUpdatePage.save();
        expect(await supplierImportedDocumentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await supplierImportedDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SupplierImportedDocument', async () => {
        const nbButtonsBeforeDelete = await supplierImportedDocumentComponentsPage.countDeleteButtons();
        await supplierImportedDocumentComponentsPage.clickOnLastDeleteButton();

        supplierImportedDocumentDeleteDialog = new SupplierImportedDocumentDeleteDialog();
        expect(await supplierImportedDocumentDeleteDialog.getDialogTitle()).to.eq('resourceApp.supplierImportedDocument.delete.question');
        await supplierImportedDocumentDeleteDialog.clickOnConfirmButton();

        expect(await supplierImportedDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
