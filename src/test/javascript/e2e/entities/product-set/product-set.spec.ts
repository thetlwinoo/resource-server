/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductSetComponentsPage, ProductSetDeleteDialog, ProductSetUpdatePage } from './product-set.page-object';

const expect = chai.expect;

describe('ProductSet e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productSetUpdatePage: ProductSetUpdatePage;
    let productSetComponentsPage: ProductSetComponentsPage;
    let productSetDeleteDialog: ProductSetDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductSets', async () => {
        await navBarPage.goToEntity('product-set');
        productSetComponentsPage = new ProductSetComponentsPage();
        await browser.wait(ec.visibilityOf(productSetComponentsPage.title), 5000);
        expect(await productSetComponentsPage.getTitle()).to.eq('resourceApp.productSet.home.title');
    });

    it('should load create ProductSet page', async () => {
        await productSetComponentsPage.clickOnCreateButton();
        productSetUpdatePage = new ProductSetUpdatePage();
        expect(await productSetUpdatePage.getPageTitle()).to.eq('resourceApp.productSet.home.createOrEditLabel');
        await productSetUpdatePage.cancel();
    });

    it('should create and save ProductSets', async () => {
        const nbButtonsBeforeCreate = await productSetComponentsPage.countDeleteButtons();

        await productSetComponentsPage.clickOnCreateButton();
        await promise.all([productSetUpdatePage.setProductSetNameInput('productSetName'), productSetUpdatePage.setNoOfPersonInput('5')]);
        expect(await productSetUpdatePage.getProductSetNameInput()).to.eq('productSetName');
        expect(await productSetUpdatePage.getNoOfPersonInput()).to.eq('5');
        const selectedIsExclusive = productSetUpdatePage.getIsExclusiveInput();
        if (await selectedIsExclusive.isSelected()) {
            await productSetUpdatePage.getIsExclusiveInput().click();
            expect(await productSetUpdatePage.getIsExclusiveInput().isSelected()).to.be.false;
        } else {
            await productSetUpdatePage.getIsExclusiveInput().click();
            expect(await productSetUpdatePage.getIsExclusiveInput().isSelected()).to.be.true;
        }
        await productSetUpdatePage.save();
        expect(await productSetUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productSetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductSet', async () => {
        const nbButtonsBeforeDelete = await productSetComponentsPage.countDeleteButtons();
        await productSetComponentsPage.clickOnLastDeleteButton();

        productSetDeleteDialog = new ProductSetDeleteDialog();
        expect(await productSetDeleteDialog.getDialogTitle()).to.eq('resourceApp.productSet.delete.question');
        await productSetDeleteDialog.clickOnConfirmButton();

        expect(await productSetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
