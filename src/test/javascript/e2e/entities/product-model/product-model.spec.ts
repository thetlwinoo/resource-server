/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductModelComponentsPage, ProductModelDeleteDialog, ProductModelUpdatePage } from './product-model.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('ProductModel e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productModelUpdatePage: ProductModelUpdatePage;
    let productModelComponentsPage: ProductModelComponentsPage;
    let productModelDeleteDialog: ProductModelDeleteDialog;
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

    it('should load ProductModels', async () => {
        await navBarPage.goToEntity('product-model');
        productModelComponentsPage = new ProductModelComponentsPage();
        await browser.wait(ec.visibilityOf(productModelComponentsPage.title), 5000);
        expect(await productModelComponentsPage.getTitle()).to.eq('resourceApp.productModel.home.title');
    });

    it('should load create ProductModel page', async () => {
        await productModelComponentsPage.clickOnCreateButton();
        productModelUpdatePage = new ProductModelUpdatePage();
        expect(await productModelUpdatePage.getPageTitle()).to.eq('resourceApp.productModel.home.createOrEditLabel');
        await productModelUpdatePage.cancel();
    });

    it('should create and save ProductModels', async () => {
        const nbButtonsBeforeCreate = await productModelComponentsPage.countDeleteButtons();

        await productModelComponentsPage.clickOnCreateButton();
        await promise.all([
            productModelUpdatePage.setProductModelNameInput('productModelName'),
            productModelUpdatePage.setCalalogDescriptionInput('calalogDescription'),
            productModelUpdatePage.setInstructionsInput('instructions'),
            productModelUpdatePage.setPhotoInput(absolutePath),
            productModelUpdatePage.merchantSelectLastOption()
        ]);
        expect(await productModelUpdatePage.getProductModelNameInput()).to.eq('productModelName');
        expect(await productModelUpdatePage.getCalalogDescriptionInput()).to.eq('calalogDescription');
        expect(await productModelUpdatePage.getInstructionsInput()).to.eq('instructions');
        expect(await productModelUpdatePage.getPhotoInput()).to.endsWith(fileNameToUpload);
        await productModelUpdatePage.save();
        expect(await productModelUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productModelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductModel', async () => {
        const nbButtonsBeforeDelete = await productModelComponentsPage.countDeleteButtons();
        await productModelComponentsPage.clickOnLastDeleteButton();

        productModelDeleteDialog = new ProductModelDeleteDialog();
        expect(await productModelDeleteDialog.getDialogTitle()).to.eq('resourceApp.productModel.delete.question');
        await productModelDeleteDialog.clickOnConfirmButton();

        expect(await productModelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
