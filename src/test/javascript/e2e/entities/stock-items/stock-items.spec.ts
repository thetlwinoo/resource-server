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
            stockItemsUpdatePage.setVendorCodeInput('vendorCode'),
            stockItemsUpdatePage.setVendorSKUInput('vendorSKU'),
            stockItemsUpdatePage.setGeneratedSKUInput('generatedSKU'),
            stockItemsUpdatePage.setBarcodeInput('barcode'),
            stockItemsUpdatePage.setUnitPriceInput('5'),
            stockItemsUpdatePage.setRecommendedRetailPriceInput('5'),
            stockItemsUpdatePage.setQuantityOnHandInput('5'),
            stockItemsUpdatePage.setItemLengthInput('5'),
            stockItemsUpdatePage.setItemWidthInput('5'),
            stockItemsUpdatePage.setItemHeightInput('5'),
            stockItemsUpdatePage.setItemWeightInput('5'),
            stockItemsUpdatePage.setItemPackageLengthInput('5'),
            stockItemsUpdatePage.setItemPackageWidthInput('5'),
            stockItemsUpdatePage.setItemPackageHeightInput('5'),
            stockItemsUpdatePage.setItemPackageWeightInput('5'),
            stockItemsUpdatePage.setNoOfPiecesInput('5'),
            stockItemsUpdatePage.setNoOfItemsInput('5'),
            stockItemsUpdatePage.setManufactureInput('manufacture'),
            stockItemsUpdatePage.setMarketingCommentsInput('marketingComments'),
            stockItemsUpdatePage.setInternalCommentsInput('internalComments'),
            stockItemsUpdatePage.setSellStartDateInput('2000-12-31'),
            stockItemsUpdatePage.setSellEndDateInput('2000-12-31'),
            stockItemsUpdatePage.setSellCountInput('5'),
            stockItemsUpdatePage.setCustomFieldsInput('customFields'),
            stockItemsUpdatePage.setThumbnailUrlInput('thumbnailUrl'),
            stockItemsUpdatePage.stockItemOnReviewLineSelectLastOption(),
            stockItemsUpdatePage.itemLengthUnitSelectLastOption(),
            stockItemsUpdatePage.itemWidthUnitSelectLastOption(),
            stockItemsUpdatePage.itemHeightUnitSelectLastOption(),
            stockItemsUpdatePage.packageLengthUnitSelectLastOption(),
            stockItemsUpdatePage.packageWidthUnitSelectLastOption(),
            stockItemsUpdatePage.packageHeightUnitSelectLastOption(),
            stockItemsUpdatePage.itemPackageWeightUnitSelectLastOption(),
            stockItemsUpdatePage.productAttributeSelectLastOption(),
            stockItemsUpdatePage.productOptionSelectLastOption(),
            stockItemsUpdatePage.materialSelectLastOption(),
            stockItemsUpdatePage.currencySelectLastOption(),
            stockItemsUpdatePage.barcodeTypeSelectLastOption(),
            stockItemsUpdatePage.productSelectLastOption()
        ]);
        expect(await stockItemsUpdatePage.getStockItemNameInput()).to.eq('stockItemName');
        expect(await stockItemsUpdatePage.getVendorCodeInput()).to.eq('vendorCode');
        expect(await stockItemsUpdatePage.getVendorSKUInput()).to.eq('vendorSKU');
        expect(await stockItemsUpdatePage.getGeneratedSKUInput()).to.eq('generatedSKU');
        expect(await stockItemsUpdatePage.getBarcodeInput()).to.eq('barcode');
        expect(await stockItemsUpdatePage.getUnitPriceInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getRecommendedRetailPriceInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getQuantityOnHandInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getItemLengthInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getItemWidthInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getItemHeightInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getItemWeightInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getItemPackageLengthInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getItemPackageWidthInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getItemPackageHeightInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getItemPackageWeightInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getNoOfPiecesInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getNoOfItemsInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getManufactureInput()).to.eq('manufacture');
        expect(await stockItemsUpdatePage.getMarketingCommentsInput()).to.eq('marketingComments');
        expect(await stockItemsUpdatePage.getInternalCommentsInput()).to.eq('internalComments');
        expect(await stockItemsUpdatePage.getSellStartDateInput()).to.eq('2000-12-31');
        expect(await stockItemsUpdatePage.getSellEndDateInput()).to.eq('2000-12-31');
        expect(await stockItemsUpdatePage.getSellCountInput()).to.eq('5');
        expect(await stockItemsUpdatePage.getCustomFieldsInput()).to.eq('customFields');
        expect(await stockItemsUpdatePage.getThumbnailUrlInput()).to.eq('thumbnailUrl');
        const selectedActiveInd = stockItemsUpdatePage.getActiveIndInput();
        if (await selectedActiveInd.isSelected()) {
            await stockItemsUpdatePage.getActiveIndInput().click();
            expect(await stockItemsUpdatePage.getActiveIndInput().isSelected()).to.be.false;
        } else {
            await stockItemsUpdatePage.getActiveIndInput().click();
            expect(await stockItemsUpdatePage.getActiveIndInput().isSelected()).to.be.true;
        }
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
