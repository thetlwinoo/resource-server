/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductsComponentsPage, ProductsDeleteDialog, ProductsUpdatePage } from './products.page-object';

const expect = chai.expect;

describe('Products e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productsUpdatePage: ProductsUpdatePage;
    let productsComponentsPage: ProductsComponentsPage;
    let productsDeleteDialog: ProductsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Products', async () => {
        await navBarPage.goToEntity('products');
        productsComponentsPage = new ProductsComponentsPage();
        await browser.wait(ec.visibilityOf(productsComponentsPage.title), 5000);
        expect(await productsComponentsPage.getTitle()).to.eq('resourceApp.products.home.title');
    });

    it('should load create Products page', async () => {
        await productsComponentsPage.clickOnCreateButton();
        productsUpdatePage = new ProductsUpdatePage();
        expect(await productsUpdatePage.getPageTitle()).to.eq('resourceApp.products.home.createOrEditLabel');
        await productsUpdatePage.cancel();
    });

    it('should create and save Products', async () => {
        const nbButtonsBeforeCreate = await productsComponentsPage.countDeleteButtons();

        await productsComponentsPage.clickOnCreateButton();
        await promise.all([
            productsUpdatePage.setProductNameInput('productName'),
            productsUpdatePage.setProductNumberInput('productNumber'),
            productsUpdatePage.setSearchDetailsInput('searchDetails'),
            productsUpdatePage.setColorInput('color'),
            productsUpdatePage.setSafetyStockLevelInput('5'),
            productsUpdatePage.setReorderPointInput('5'),
            productsUpdatePage.setStandardCostInput('5'),
            productsUpdatePage.setUnitPriceInput('5'),
            productsUpdatePage.setRecommendedRetailPriceInput('5'),
            productsUpdatePage.setBrandInput('brand'),
            productsUpdatePage.setSpecifySizeInput('specifySize'),
            productsUpdatePage.setWeightInput('5'),
            productsUpdatePage.setDaysToManufactureInput('5'),
            productsUpdatePage.setProductLineInput('productLine'),
            productsUpdatePage.setClassTypeInput('classType'),
            productsUpdatePage.setStyleInput('style'),
            productsUpdatePage.setCustomFieldsInput('customFields'),
            productsUpdatePage.setTagsInput('tags'),
            productsUpdatePage.setPhotoInput('photo'),
            productsUpdatePage.setSellStartDateInput('2000-12-31'),
            productsUpdatePage.setSellEndDateInput('2000-12-31'),
            productsUpdatePage.setMarketingCommentsInput('marketingComments'),
            productsUpdatePage.setInternalCommentsInput('internalComments'),
            productsUpdatePage.setDiscontinuedDateInput('2000-12-31'),
            productsUpdatePage.setSellCountInput('5'),
            productsUpdatePage.unitPackageSelectLastOption(),
            productsUpdatePage.outerPackageSelectLastOption(),
            productsUpdatePage.supplierSelectLastOption(),
            productsUpdatePage.productSubCategorySelectLastOption(),
            productsUpdatePage.sizeUnitMeasureCodeSelectLastOption(),
            productsUpdatePage.weightUnitMeasureCodeSelectLastOption(),
            productsUpdatePage.productModelSelectLastOption()
        ]);
        expect(await productsUpdatePage.getProductNameInput()).to.eq('productName');
        expect(await productsUpdatePage.getProductNumberInput()).to.eq('productNumber');
        expect(await productsUpdatePage.getSearchDetailsInput()).to.eq('searchDetails');
        const selectedMakeFlag = productsUpdatePage.getMakeFlagInput();
        if (await selectedMakeFlag.isSelected()) {
            await productsUpdatePage.getMakeFlagInput().click();
            expect(await productsUpdatePage.getMakeFlagInput().isSelected()).to.be.false;
        } else {
            await productsUpdatePage.getMakeFlagInput().click();
            expect(await productsUpdatePage.getMakeFlagInput().isSelected()).to.be.true;
        }
        const selectedFinishedGoodsFlag = productsUpdatePage.getFinishedGoodsFlagInput();
        if (await selectedFinishedGoodsFlag.isSelected()) {
            await productsUpdatePage.getFinishedGoodsFlagInput().click();
            expect(await productsUpdatePage.getFinishedGoodsFlagInput().isSelected()).to.be.false;
        } else {
            await productsUpdatePage.getFinishedGoodsFlagInput().click();
            expect(await productsUpdatePage.getFinishedGoodsFlagInput().isSelected()).to.be.true;
        }
        expect(await productsUpdatePage.getColorInput()).to.eq('color');
        expect(await productsUpdatePage.getSafetyStockLevelInput()).to.eq('5');
        expect(await productsUpdatePage.getReorderPointInput()).to.eq('5');
        expect(await productsUpdatePage.getStandardCostInput()).to.eq('5');
        expect(await productsUpdatePage.getUnitPriceInput()).to.eq('5');
        expect(await productsUpdatePage.getRecommendedRetailPriceInput()).to.eq('5');
        expect(await productsUpdatePage.getBrandInput()).to.eq('brand');
        expect(await productsUpdatePage.getSpecifySizeInput()).to.eq('specifySize');
        expect(await productsUpdatePage.getWeightInput()).to.eq('5');
        expect(await productsUpdatePage.getDaysToManufactureInput()).to.eq('5');
        expect(await productsUpdatePage.getProductLineInput()).to.eq('productLine');
        expect(await productsUpdatePage.getClassTypeInput()).to.eq('classType');
        expect(await productsUpdatePage.getStyleInput()).to.eq('style');
        expect(await productsUpdatePage.getCustomFieldsInput()).to.eq('customFields');
        expect(await productsUpdatePage.getTagsInput()).to.eq('tags');
        expect(await productsUpdatePage.getPhotoInput()).to.eq('photo');
        expect(await productsUpdatePage.getSellStartDateInput()).to.eq('2000-12-31');
        expect(await productsUpdatePage.getSellEndDateInput()).to.eq('2000-12-31');
        expect(await productsUpdatePage.getMarketingCommentsInput()).to.eq('marketingComments');
        expect(await productsUpdatePage.getInternalCommentsInput()).to.eq('internalComments');
        expect(await productsUpdatePage.getDiscontinuedDateInput()).to.eq('2000-12-31');
        expect(await productsUpdatePage.getSellCountInput()).to.eq('5');
        await productsUpdatePage.save();
        expect(await productsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Products', async () => {
        const nbButtonsBeforeDelete = await productsComponentsPage.countDeleteButtons();
        await productsComponentsPage.clickOnLastDeleteButton();

        productsDeleteDialog = new ProductsDeleteDialog();
        expect(await productsDeleteDialog.getDialogTitle()).to.eq('resourceApp.products.delete.question');
        await productsDeleteDialog.clickOnConfirmButton();

        expect(await productsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
