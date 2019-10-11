/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductDocumentComponentsPage, ProductDocumentDeleteDialog, ProductDocumentUpdatePage } from './product-document.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('ProductDocument e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productDocumentUpdatePage: ProductDocumentUpdatePage;
    let productDocumentComponentsPage: ProductDocumentComponentsPage;
    let productDocumentDeleteDialog: ProductDocumentDeleteDialog;
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

    it('should load ProductDocuments', async () => {
        await navBarPage.goToEntity('product-document');
        productDocumentComponentsPage = new ProductDocumentComponentsPage();
        await browser.wait(ec.visibilityOf(productDocumentComponentsPage.title), 5000);
        expect(await productDocumentComponentsPage.getTitle()).to.eq('resourceApp.productDocument.home.title');
    });

    it('should load create ProductDocument page', async () => {
        await productDocumentComponentsPage.clickOnCreateButton();
        productDocumentUpdatePage = new ProductDocumentUpdatePage();
        expect(await productDocumentUpdatePage.getPageTitle()).to.eq('resourceApp.productDocument.home.createOrEditLabel');
        await productDocumentUpdatePage.cancel();
    });

    it('should create and save ProductDocuments', async () => {
        const nbButtonsBeforeCreate = await productDocumentComponentsPage.countDeleteButtons();

        await productDocumentComponentsPage.clickOnCreateButton();
        await promise.all([
            productDocumentUpdatePage.setVideoUrlInput('videoUrl'),
            productDocumentUpdatePage.setHighlightsInput('highlights'),
            productDocumentUpdatePage.setLongDescriptionInput(absolutePath),
            productDocumentUpdatePage.setShortDescriptionInput('shortDescription'),
            productDocumentUpdatePage.setDescriptionInput('description'),
            productDocumentUpdatePage.setCareInstructionsInput('careInstructions'),
            productDocumentUpdatePage.setProductTypeInput('productType'),
            productDocumentUpdatePage.setModelNameInput('modelName'),
            productDocumentUpdatePage.setModelNumberInput('modelNumber'),
            productDocumentUpdatePage.setFabricTypeInput('fabricType'),
            productDocumentUpdatePage.setSpecialFeaturesInput('specialFeatures'),
            productDocumentUpdatePage.setProductComplianceCertificateInput('productComplianceCertificate'),
            productDocumentUpdatePage.setCountryOfOriginInput('countryOfOrigin'),
            productDocumentUpdatePage.setUsageAndSideEffectsInput('usageAndSideEffects'),
            productDocumentUpdatePage.setSafetyWarnningInput('safetyWarnning'),
            productDocumentUpdatePage.setWarrantyPeriodInput('warrantyPeriod'),
            productDocumentUpdatePage.setWarrantyPolicyInput('warrantyPolicy'),
            productDocumentUpdatePage.warrantyTypeSelectLastOption(),
            productDocumentUpdatePage.cultureSelectLastOption()
        ]);
        expect(await productDocumentUpdatePage.getVideoUrlInput()).to.eq('videoUrl');
        expect(await productDocumentUpdatePage.getHighlightsInput()).to.eq('highlights');
        expect(await productDocumentUpdatePage.getLongDescriptionInput()).to.endsWith(fileNameToUpload);
        expect(await productDocumentUpdatePage.getShortDescriptionInput()).to.eq('shortDescription');
        expect(await productDocumentUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await productDocumentUpdatePage.getCareInstructionsInput()).to.eq('careInstructions');
        expect(await productDocumentUpdatePage.getProductTypeInput()).to.eq('productType');
        expect(await productDocumentUpdatePage.getModelNameInput()).to.eq('modelName');
        expect(await productDocumentUpdatePage.getModelNumberInput()).to.eq('modelNumber');
        expect(await productDocumentUpdatePage.getFabricTypeInput()).to.eq('fabricType');
        expect(await productDocumentUpdatePage.getSpecialFeaturesInput()).to.eq('specialFeatures');
        expect(await productDocumentUpdatePage.getProductComplianceCertificateInput()).to.eq('productComplianceCertificate');
        const selectedGenuineAndLegal = productDocumentUpdatePage.getGenuineAndLegalInput();
        if (await selectedGenuineAndLegal.isSelected()) {
            await productDocumentUpdatePage.getGenuineAndLegalInput().click();
            expect(await productDocumentUpdatePage.getGenuineAndLegalInput().isSelected()).to.be.false;
        } else {
            await productDocumentUpdatePage.getGenuineAndLegalInput().click();
            expect(await productDocumentUpdatePage.getGenuineAndLegalInput().isSelected()).to.be.true;
        }
        expect(await productDocumentUpdatePage.getCountryOfOriginInput()).to.eq('countryOfOrigin');
        expect(await productDocumentUpdatePage.getUsageAndSideEffectsInput()).to.eq('usageAndSideEffects');
        expect(await productDocumentUpdatePage.getSafetyWarnningInput()).to.eq('safetyWarnning');
        expect(await productDocumentUpdatePage.getWarrantyPeriodInput()).to.eq('warrantyPeriod');
        expect(await productDocumentUpdatePage.getWarrantyPolicyInput()).to.eq('warrantyPolicy');
        await productDocumentUpdatePage.save();
        expect(await productDocumentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductDocument', async () => {
        const nbButtonsBeforeDelete = await productDocumentComponentsPage.countDeleteButtons();
        await productDocumentComponentsPage.clickOnLastDeleteButton();

        productDocumentDeleteDialog = new ProductDocumentDeleteDialog();
        expect(await productDocumentDeleteDialog.getDialogTitle()).to.eq('resourceApp.productDocument.delete.question');
        await productDocumentDeleteDialog.clickOnConfirmButton();

        expect(await productDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
