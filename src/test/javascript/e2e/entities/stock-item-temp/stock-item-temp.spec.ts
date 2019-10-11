/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StockItemTempComponentsPage, StockItemTempDeleteDialog, StockItemTempUpdatePage } from './stock-item-temp.page-object';

const expect = chai.expect;

describe('StockItemTemp e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let stockItemTempUpdatePage: StockItemTempUpdatePage;
    let stockItemTempComponentsPage: StockItemTempComponentsPage;
    let stockItemTempDeleteDialog: StockItemTempDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StockItemTemps', async () => {
        await navBarPage.goToEntity('stock-item-temp');
        stockItemTempComponentsPage = new StockItemTempComponentsPage();
        await browser.wait(ec.visibilityOf(stockItemTempComponentsPage.title), 5000);
        expect(await stockItemTempComponentsPage.getTitle()).to.eq('resourceApp.stockItemTemp.home.title');
    });

    it('should load create StockItemTemp page', async () => {
        await stockItemTempComponentsPage.clickOnCreateButton();
        stockItemTempUpdatePage = new StockItemTempUpdatePage();
        expect(await stockItemTempUpdatePage.getPageTitle()).to.eq('resourceApp.stockItemTemp.home.createOrEditLabel');
        await stockItemTempUpdatePage.cancel();
    });

    it('should create and save StockItemTemps', async () => {
        const nbButtonsBeforeCreate = await stockItemTempComponentsPage.countDeleteButtons();

        await stockItemTempComponentsPage.clickOnCreateButton();
        await promise.all([
            stockItemTempUpdatePage.setStockItemNameInput('stockItemName'),
            stockItemTempUpdatePage.setVendorCodeInput('vendorCode'),
            stockItemTempUpdatePage.setVendorSKUInput('vendorSKU'),
            stockItemTempUpdatePage.setBarcodeInput('barcode'),
            stockItemTempUpdatePage.setBarcodeTypeIdInput('5'),
            stockItemTempUpdatePage.setBarcodeTypeNameInput('barcodeTypeName'),
            stockItemTempUpdatePage.setProductTypeInput('productType'),
            stockItemTempUpdatePage.setProductCategoryIdInput('5'),
            stockItemTempUpdatePage.setProductCategoryNameInput('productCategoryName'),
            stockItemTempUpdatePage.setProductAttributeSetIdInput('5'),
            stockItemTempUpdatePage.setProductAttributeIdInput('5'),
            stockItemTempUpdatePage.setProductAttributeValueInput('productAttributeValue'),
            stockItemTempUpdatePage.setProductOptionSetIdInput('5'),
            stockItemTempUpdatePage.setProductOptionIdInput('5'),
            stockItemTempUpdatePage.setProductOptionValueInput('productOptionValue'),
            stockItemTempUpdatePage.setModelNameInput('modelName'),
            stockItemTempUpdatePage.setModelNumberInput('modelNumber'),
            stockItemTempUpdatePage.setMaterialIdInput('5'),
            stockItemTempUpdatePage.setMaterialNameInput('materialName'),
            stockItemTempUpdatePage.setShortDescriptionInput('shortDescription'),
            stockItemTempUpdatePage.setDescriptionInput('description'),
            stockItemTempUpdatePage.setProductBrandIdInput('5'),
            stockItemTempUpdatePage.setProductBrandNameInput('productBrandName'),
            stockItemTempUpdatePage.setHighlightsInput('highlights'),
            stockItemTempUpdatePage.setSearchDetailsInput('searchDetails'),
            stockItemTempUpdatePage.setCareInstructionsInput('careInstructions'),
            stockItemTempUpdatePage.setDangerousGoodsInput('dangerousGoods'),
            stockItemTempUpdatePage.setVideoUrlInput('videoUrl'),
            stockItemTempUpdatePage.setUnitPriceInput('5'),
            stockItemTempUpdatePage.setRemommendedRetailPriceInput('5'),
            stockItemTempUpdatePage.setCurrencyCodeInput('currencyCode'),
            stockItemTempUpdatePage.setQuantityOnHandInput('5'),
            stockItemTempUpdatePage.setWarrantyPeriodInput('warrantyPeriod'),
            stockItemTempUpdatePage.setWarrantyPolicyInput('warrantyPolicy'),
            stockItemTempUpdatePage.setWarrantyTypeIdInput('5'),
            stockItemTempUpdatePage.setWarrantyTypeNameInput('warrantyTypeName'),
            stockItemTempUpdatePage.setWhatInTheBoxInput('whatInTheBox'),
            stockItemTempUpdatePage.setItemLengthInput('5'),
            stockItemTempUpdatePage.setItemWidthInput('5'),
            stockItemTempUpdatePage.setItemHeightInput('5'),
            stockItemTempUpdatePage.setItemWeightInput('5'),
            stockItemTempUpdatePage.setItemPackageLengthInput('5'),
            stockItemTempUpdatePage.setItemPackageWidthInput('5'),
            stockItemTempUpdatePage.setItemPackageHeightInput('5'),
            stockItemTempUpdatePage.setItemPackageWeightInput('5'),
            stockItemTempUpdatePage.setItemLengthUnitMeasureIdInput('5'),
            stockItemTempUpdatePage.setItemLengthUnitMeasureCodeInput('itemLengthUnitMeasureCode'),
            stockItemTempUpdatePage.setItemWidthUnitMeasureIdInput('5'),
            stockItemTempUpdatePage.setItemWidthUnitMeasureCodeInput('itemWidthUnitMeasureCode'),
            stockItemTempUpdatePage.setItemHeightUnitMeasureIdInput('5'),
            stockItemTempUpdatePage.setItemHeightUnitMeasureCodeInput('itemHeightUnitMeasureCode'),
            stockItemTempUpdatePage.setItemWeightUnitMeasureIdInput('5'),
            stockItemTempUpdatePage.setItemWeightUnitMeasureCodeInput('itemWeightUnitMeasureCode'),
            stockItemTempUpdatePage.setItemPackageLengthUnitMeasureIdInput('5'),
            stockItemTempUpdatePage.setItemPackageLengthUnitMeasureCodeInput('itemPackageLengthUnitMeasureCode'),
            stockItemTempUpdatePage.setItemPackageWidthUnitMeasureIdInput('5'),
            stockItemTempUpdatePage.setItemPackageWidthUnitMeasureCodeInput('itemPackageWidthUnitMeasureCode'),
            stockItemTempUpdatePage.setItemPackageHeightUnitMeasureIdInput('5'),
            stockItemTempUpdatePage.setItemPackageHeightUnitMeasureCodeInput('itemPackageHeightUnitMeasureCode'),
            stockItemTempUpdatePage.setItemPackageWeightUnitMeasureIdInput('5'),
            stockItemTempUpdatePage.setItemPackageWeightUnitMeasureCodeInput('itemPackageWeightUnitMeasureCode'),
            stockItemTempUpdatePage.setNoOfPiecesInput('5'),
            stockItemTempUpdatePage.setNoOfItemsInput('5'),
            stockItemTempUpdatePage.setManufactureInput('manufacture'),
            stockItemTempUpdatePage.setSpecialFeacturesInput('specialFeactures'),
            stockItemTempUpdatePage.setProductComplianceCertificateInput('productComplianceCertificate'),
            stockItemTempUpdatePage.setCountryOfOriginInput('countryOfOrigin'),
            stockItemTempUpdatePage.setUsageAndSideEffectsInput('usageAndSideEffects'),
            stockItemTempUpdatePage.setSafetyWarnningInput('safetyWarnning'),
            stockItemTempUpdatePage.setSellStartDateInput('2000-12-31'),
            stockItemTempUpdatePage.setSellEndDateInput('2000-12-31'),
            stockItemTempUpdatePage.setStatusInput('5'),
            stockItemTempUpdatePage.uploadTransactionSelectLastOption()
        ]);
        expect(await stockItemTempUpdatePage.getStockItemNameInput()).to.eq('stockItemName');
        expect(await stockItemTempUpdatePage.getVendorCodeInput()).to.eq('vendorCode');
        expect(await stockItemTempUpdatePage.getVendorSKUInput()).to.eq('vendorSKU');
        expect(await stockItemTempUpdatePage.getBarcodeInput()).to.eq('barcode');
        expect(await stockItemTempUpdatePage.getBarcodeTypeIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getBarcodeTypeNameInput()).to.eq('barcodeTypeName');
        expect(await stockItemTempUpdatePage.getProductTypeInput()).to.eq('productType');
        expect(await stockItemTempUpdatePage.getProductCategoryIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getProductCategoryNameInput()).to.eq('productCategoryName');
        expect(await stockItemTempUpdatePage.getProductAttributeSetIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getProductAttributeIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getProductAttributeValueInput()).to.eq('productAttributeValue');
        expect(await stockItemTempUpdatePage.getProductOptionSetIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getProductOptionIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getProductOptionValueInput()).to.eq('productOptionValue');
        expect(await stockItemTempUpdatePage.getModelNameInput()).to.eq('modelName');
        expect(await stockItemTempUpdatePage.getModelNumberInput()).to.eq('modelNumber');
        expect(await stockItemTempUpdatePage.getMaterialIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getMaterialNameInput()).to.eq('materialName');
        expect(await stockItemTempUpdatePage.getShortDescriptionInput()).to.eq('shortDescription');
        expect(await stockItemTempUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await stockItemTempUpdatePage.getProductBrandIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getProductBrandNameInput()).to.eq('productBrandName');
        expect(await stockItemTempUpdatePage.getHighlightsInput()).to.eq('highlights');
        expect(await stockItemTempUpdatePage.getSearchDetailsInput()).to.eq('searchDetails');
        expect(await stockItemTempUpdatePage.getCareInstructionsInput()).to.eq('careInstructions');
        expect(await stockItemTempUpdatePage.getDangerousGoodsInput()).to.eq('dangerousGoods');
        expect(await stockItemTempUpdatePage.getVideoUrlInput()).to.eq('videoUrl');
        expect(await stockItemTempUpdatePage.getUnitPriceInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getRemommendedRetailPriceInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getCurrencyCodeInput()).to.eq('currencyCode');
        expect(await stockItemTempUpdatePage.getQuantityOnHandInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getWarrantyPeriodInput()).to.eq('warrantyPeriod');
        expect(await stockItemTempUpdatePage.getWarrantyPolicyInput()).to.eq('warrantyPolicy');
        expect(await stockItemTempUpdatePage.getWarrantyTypeIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getWarrantyTypeNameInput()).to.eq('warrantyTypeName');
        expect(await stockItemTempUpdatePage.getWhatInTheBoxInput()).to.eq('whatInTheBox');
        expect(await stockItemTempUpdatePage.getItemLengthInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemWidthInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemHeightInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemWeightInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemPackageLengthInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemPackageWidthInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemPackageHeightInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemPackageWeightInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemLengthUnitMeasureIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemLengthUnitMeasureCodeInput()).to.eq('itemLengthUnitMeasureCode');
        expect(await stockItemTempUpdatePage.getItemWidthUnitMeasureIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemWidthUnitMeasureCodeInput()).to.eq('itemWidthUnitMeasureCode');
        expect(await stockItemTempUpdatePage.getItemHeightUnitMeasureIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemHeightUnitMeasureCodeInput()).to.eq('itemHeightUnitMeasureCode');
        expect(await stockItemTempUpdatePage.getItemWeightUnitMeasureIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemWeightUnitMeasureCodeInput()).to.eq('itemWeightUnitMeasureCode');
        expect(await stockItemTempUpdatePage.getItemPackageLengthUnitMeasureIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemPackageLengthUnitMeasureCodeInput()).to.eq('itemPackageLengthUnitMeasureCode');
        expect(await stockItemTempUpdatePage.getItemPackageWidthUnitMeasureIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemPackageWidthUnitMeasureCodeInput()).to.eq('itemPackageWidthUnitMeasureCode');
        expect(await stockItemTempUpdatePage.getItemPackageHeightUnitMeasureIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemPackageHeightUnitMeasureCodeInput()).to.eq('itemPackageHeightUnitMeasureCode');
        expect(await stockItemTempUpdatePage.getItemPackageWeightUnitMeasureIdInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getItemPackageWeightUnitMeasureCodeInput()).to.eq('itemPackageWeightUnitMeasureCode');
        expect(await stockItemTempUpdatePage.getNoOfPiecesInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getNoOfItemsInput()).to.eq('5');
        expect(await stockItemTempUpdatePage.getManufactureInput()).to.eq('manufacture');
        expect(await stockItemTempUpdatePage.getSpecialFeacturesInput()).to.eq('specialFeactures');
        expect(await stockItemTempUpdatePage.getProductComplianceCertificateInput()).to.eq('productComplianceCertificate');
        const selectedGenuineAndLegal = stockItemTempUpdatePage.getGenuineAndLegalInput();
        if (await selectedGenuineAndLegal.isSelected()) {
            await stockItemTempUpdatePage.getGenuineAndLegalInput().click();
            expect(await stockItemTempUpdatePage.getGenuineAndLegalInput().isSelected()).to.be.false;
        } else {
            await stockItemTempUpdatePage.getGenuineAndLegalInput().click();
            expect(await stockItemTempUpdatePage.getGenuineAndLegalInput().isSelected()).to.be.true;
        }
        expect(await stockItemTempUpdatePage.getCountryOfOriginInput()).to.eq('countryOfOrigin');
        expect(await stockItemTempUpdatePage.getUsageAndSideEffectsInput()).to.eq('usageAndSideEffects');
        expect(await stockItemTempUpdatePage.getSafetyWarnningInput()).to.eq('safetyWarnning');
        expect(await stockItemTempUpdatePage.getSellStartDateInput()).to.eq('2000-12-31');
        expect(await stockItemTempUpdatePage.getSellEndDateInput()).to.eq('2000-12-31');
        expect(await stockItemTempUpdatePage.getStatusInput()).to.eq('5');
        await stockItemTempUpdatePage.save();
        expect(await stockItemTempUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await stockItemTempComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StockItemTemp', async () => {
        const nbButtonsBeforeDelete = await stockItemTempComponentsPage.countDeleteButtons();
        await stockItemTempComponentsPage.clickOnLastDeleteButton();

        stockItemTempDeleteDialog = new StockItemTempDeleteDialog();
        expect(await stockItemTempDeleteDialog.getDialogTitle()).to.eq('resourceApp.stockItemTemp.delete.question');
        await stockItemTempDeleteDialog.clickOnConfirmButton();

        expect(await stockItemTempComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
