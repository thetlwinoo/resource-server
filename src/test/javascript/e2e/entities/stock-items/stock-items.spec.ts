/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StockItemsComponentsPage, StockItemsDeleteDialog, StockItemsUpdatePage } from './stock-items.page-object';

const expect = chai.expect;

describe('StockItems e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let stockItemsUpdatePage: StockItemsUpdatePage;
    let stockItemsComponentsPage: StockItemsComponentsPage;
    let stockItemsDeleteDialog: StockItemsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StockItems', async () => {
        await navBarPage.goToEntity('stock-items');
        stockItemsComponentsPage = new StockItemsComponentsPage();
        await browser.wait(ec.visibilityOf(stockItemsComponentsPage.title), 5000);
        expect(await stockItemsComponentsPage.getTitle()).to.eq('resourceApp.stockItems.home.title');
    });

    it('should load create StockItems page', async () => {
        await stockItemsComponentsPage.clickOnCreateButton();
        stockItemsUpdatePage = new StockItemsUpdatePage();
        expect(await stockItemsUpdatePage.getPageTitle()).to.eq('resourceApp.stockItems.home.createOrEditLabel');
        await stockItemsUpdatePage.cancel();
    });

    it('should create and save StockItems', async () => {
        const nbButtonsBeforeCreate = await stockItemsComponentsPage.countDeleteButtons();

        await stockItemsComponentsPage.clickOnCreateButton();
        await promise.all([
            stockItemsUpdatePage.setStockItemNameInput('stockItemName'),
            stockItemsUpdatePage.setSellerSKUInput('sellerSKU'),
            stockItemsUpdatePage.setGeneratedSKUInput('generatedSKU'),
            stockItemsUpdatePage.setBarcodeInput('barcode'),
            stockItemsUpdatePage.setUnitPriceInput('5'),
            stockItemsUpdatePage.setRecommendedRetailPriceInput('5'),
            stockItemsUpdatePage.setQuantityPerOuterInput('5'),
            stockItemsUpdatePage.setTypicalWeightPerUnitInput('5'),
            stockItemsUpdatePage.setTypicalLengthPerUnitInput('5'),
            stockItemsUpdatePage.setTypicalWidthPerUnitInput('5'),
            stockItemsUpdatePage.setTypicalHeightPerUnitInput('5'),
            stockItemsUpdatePage.setMarketingCommentsInput('marketingComments'),
            stockItemsUpdatePage.setInternalCommentsInput('internalComments'),
            stockItemsUpdatePage.setDiscontinuedDateInput('2000-12-31'),
            stockItemsUpdatePage.setSellCountInput('5'),
            stockItemsUpdatePage.setCustomFieldsInput('customFields'),
            stockItemsUpdatePage.setThumbnailUrlInput('thumbnailUrl'),
            stockItemsUpdatePage.reviewLineSelectLastOption(),
            stockItemsUpdatePage.productSelectLastOption(),
            stockItemsUpdatePage.lengthUnitMeasureCodeSelectLastOption(),
            stockItemsUpdatePage.weightUnitMeasureCodeSelectLastOption(),
            stockItemsUpdatePage.widthUnitMeasureCodeSelectLastOption(),
            stockItemsUpdatePage.heightUnitMeasureCodeSelectLastOption(),
            stockItemsUpdatePage.productAttributeSelectLastOption(),
            stockItemsUpdatePage.productOptionSelectLastOption()
        ]);
        expect(await stockItemsUpdatePage.getStockItemNameInput()).to.eq('stockItemName');
        expect(await stockItemsUpdatePage.getSellerSKUInput()).to.eq('sellerSKU');
        expect(await stockItemsUpdatePage.getGeneratedSKUInput()).to.eq('generatedSKU');
        expect(await stockItemsUpdatePage.getBarcodeInput()).to.eq('barcode');
        expect(await stockItemsUpdatePage.getUnitPriceInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getRecommendedRetailPriceInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getQuantityPerOuterInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getTypicalWeightPerUnitInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getTypicalLengthPerUnitInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getTypicalWidthPerUnitInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getTypicalHeightPerUnitInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getMarketingCommentsInput()).to.eq('marketingComments');
        expect(await stockItemsUpdatePage.getInternalCommentsInput()).to.eq('internalComments');
        expect(await stockItemsUpdatePage.getDiscontinuedDateInput()).to.eq('2000-12-31');
        expect(await stockItemsUpdatePage.getSellCountInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getCustomFieldsInput()).to.eq('customFields');
        expect(await stockItemsUpdatePage.getThumbnailUrlInput()).to.eq('thumbnailUrl');
        await stockItemsUpdatePage.save();
        expect(await stockItemsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await stockItemsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StockItems', async () => {
        const nbButtonsBeforeDelete = await stockItemsComponentsPage.countDeleteButtons();
        await stockItemsComponentsPage.clickOnLastDeleteButton();

        stockItemsDeleteDialog = new StockItemsDeleteDialog();
        expect(await stockItemsDeleteDialog.getDialogTitle()).to.eq('resourceApp.stockItems.delete.question');
        await stockItemsDeleteDialog.clickOnConfirmButton();

        expect(await stockItemsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
