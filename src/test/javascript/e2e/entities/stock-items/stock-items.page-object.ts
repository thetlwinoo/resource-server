import { element, by, ElementFinder } from 'protractor';

export class StockItemsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-stock-items div table .btn-danger'));
    title = element.all(by.css('jhi-stock-items div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class StockItemsUpdatePage {
    pageTitle = element(by.id('jhi-stock-items-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    stockItemNameInput = element(by.id('field_stockItemName'));
    sellerSKUInput = element(by.id('field_sellerSKU'));
    generatedSKUInput = element(by.id('field_generatedSKU'));
    barcodeInput = element(by.id('field_barcode'));
    unitPriceInput = element(by.id('field_unitPrice'));
    recommendedRetailPriceInput = element(by.id('field_recommendedRetailPrice'));
    quantityPerOuterInput = element(by.id('field_quantityPerOuter'));
    typicalWeightPerUnitInput = element(by.id('field_typicalWeightPerUnit'));
    typicalLengthPerUnitInput = element(by.id('field_typicalLengthPerUnit'));
    typicalWidthPerUnitInput = element(by.id('field_typicalWidthPerUnit'));
    typicalHeightPerUnitInput = element(by.id('field_typicalHeightPerUnit'));
    marketingCommentsInput = element(by.id('field_marketingComments'));
    internalCommentsInput = element(by.id('field_internalComments'));
    discontinuedDateInput = element(by.id('field_discontinuedDate'));
    sellCountInput = element(by.id('field_sellCount'));
    customFieldsInput = element(by.id('field_customFields'));
    thumbnailUrlInput = element(by.id('field_thumbnailUrl'));
    reviewLineSelect = element(by.id('field_reviewLine'));
    productSelect = element(by.id('field_product'));
    lengthUnitMeasureCodeSelect = element(by.id('field_lengthUnitMeasureCode'));
    weightUnitMeasureCodeSelect = element(by.id('field_weightUnitMeasureCode'));
    widthUnitMeasureCodeSelect = element(by.id('field_widthUnitMeasureCode'));
    heightUnitMeasureCodeSelect = element(by.id('field_heightUnitMeasureCode'));
    productAttributeSelect = element(by.id('field_productAttribute'));
    productOptionSelect = element(by.id('field_productOption'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setStockItemNameInput(stockItemName) {
        await this.stockItemNameInput.sendKeys(stockItemName);
    }

    async getStockItemNameInput() {
        return this.stockItemNameInput.getAttribute('value');
    }

    async setSellerSKUInput(sellerSKU) {
        await this.sellerSKUInput.sendKeys(sellerSKU);
    }

    async getSellerSKUInput() {
        return this.sellerSKUInput.getAttribute('value');
    }

    async setGeneratedSKUInput(generatedSKU) {
        await this.generatedSKUInput.sendKeys(generatedSKU);
    }

    async getGeneratedSKUInput() {
        return this.generatedSKUInput.getAttribute('value');
    }

    async setBarcodeInput(barcode) {
        await this.barcodeInput.sendKeys(barcode);
    }

    async getBarcodeInput() {
        return this.barcodeInput.getAttribute('value');
    }

    async setUnitPriceInput(unitPrice) {
        await this.unitPriceInput.sendKeys(unitPrice);
    }

    async getUnitPriceInput() {
        return this.unitPriceInput.getAttribute('value');
    }

    async setRecommendedRetailPriceInput(recommendedRetailPrice) {
        await this.recommendedRetailPriceInput.sendKeys(recommendedRetailPrice);
    }

    async getRecommendedRetailPriceInput() {
        return this.recommendedRetailPriceInput.getAttribute('value');
    }

    async setQuantityPerOuterInput(quantityPerOuter) {
        await this.quantityPerOuterInput.sendKeys(quantityPerOuter);
    }

    async getQuantityPerOuterInput() {
        return this.quantityPerOuterInput.getAttribute('value');
    }

    async setTypicalWeightPerUnitInput(typicalWeightPerUnit) {
        await this.typicalWeightPerUnitInput.sendKeys(typicalWeightPerUnit);
    }

    async getTypicalWeightPerUnitInput() {
        return this.typicalWeightPerUnitInput.getAttribute('value');
    }

    async setTypicalLengthPerUnitInput(typicalLengthPerUnit) {
        await this.typicalLengthPerUnitInput.sendKeys(typicalLengthPerUnit);
    }

    async getTypicalLengthPerUnitInput() {
        return this.typicalLengthPerUnitInput.getAttribute('value');
    }

    async setTypicalWidthPerUnitInput(typicalWidthPerUnit) {
        await this.typicalWidthPerUnitInput.sendKeys(typicalWidthPerUnit);
    }

    async getTypicalWidthPerUnitInput() {
        return this.typicalWidthPerUnitInput.getAttribute('value');
    }

    async setTypicalHeightPerUnitInput(typicalHeightPerUnit) {
        await this.typicalHeightPerUnitInput.sendKeys(typicalHeightPerUnit);
    }

    async getTypicalHeightPerUnitInput() {
        return this.typicalHeightPerUnitInput.getAttribute('value');
    }

    async setMarketingCommentsInput(marketingComments) {
        await this.marketingCommentsInput.sendKeys(marketingComments);
    }

    async getMarketingCommentsInput() {
        return this.marketingCommentsInput.getAttribute('value');
    }

    async setInternalCommentsInput(internalComments) {
        await this.internalCommentsInput.sendKeys(internalComments);
    }

    async getInternalCommentsInput() {
        return this.internalCommentsInput.getAttribute('value');
    }

    async setDiscontinuedDateInput(discontinuedDate) {
        await this.discontinuedDateInput.sendKeys(discontinuedDate);
    }

    async getDiscontinuedDateInput() {
        return this.discontinuedDateInput.getAttribute('value');
    }

    async setSellCountInput(sellCount) {
        await this.sellCountInput.sendKeys(sellCount);
    }

    async getSellCountInput() {
        return this.sellCountInput.getAttribute('value');
    }

    async setCustomFieldsInput(customFields) {
        await this.customFieldsInput.sendKeys(customFields);
    }

    async getCustomFieldsInput() {
        return this.customFieldsInput.getAttribute('value');
    }

    async setThumbnailUrlInput(thumbnailUrl) {
        await this.thumbnailUrlInput.sendKeys(thumbnailUrl);
    }

    async getThumbnailUrlInput() {
        return this.thumbnailUrlInput.getAttribute('value');
    }

    async reviewLineSelectLastOption() {
        await this.reviewLineSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async reviewLineSelectOption(option) {
        await this.reviewLineSelect.sendKeys(option);
    }

    getReviewLineSelect(): ElementFinder {
        return this.reviewLineSelect;
    }

    async getReviewLineSelectedOption() {
        return this.reviewLineSelect.element(by.css('option:checked')).getText();
    }

    async productSelectLastOption() {
        await this.productSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productSelectOption(option) {
        await this.productSelect.sendKeys(option);
    }

    getProductSelect(): ElementFinder {
        return this.productSelect;
    }

    async getProductSelectedOption() {
        return this.productSelect.element(by.css('option:checked')).getText();
    }

    async lengthUnitMeasureCodeSelectLastOption() {
        await this.lengthUnitMeasureCodeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async lengthUnitMeasureCodeSelectOption(option) {
        await this.lengthUnitMeasureCodeSelect.sendKeys(option);
    }

    getLengthUnitMeasureCodeSelect(): ElementFinder {
        return this.lengthUnitMeasureCodeSelect;
    }

    async getLengthUnitMeasureCodeSelectedOption() {
        return this.lengthUnitMeasureCodeSelect.element(by.css('option:checked')).getText();
    }

    async weightUnitMeasureCodeSelectLastOption() {
        await this.weightUnitMeasureCodeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async weightUnitMeasureCodeSelectOption(option) {
        await this.weightUnitMeasureCodeSelect.sendKeys(option);
    }

    getWeightUnitMeasureCodeSelect(): ElementFinder {
        return this.weightUnitMeasureCodeSelect;
    }

    async getWeightUnitMeasureCodeSelectedOption() {
        return this.weightUnitMeasureCodeSelect.element(by.css('option:checked')).getText();
    }

    async widthUnitMeasureCodeSelectLastOption() {
        await this.widthUnitMeasureCodeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async widthUnitMeasureCodeSelectOption(option) {
        await this.widthUnitMeasureCodeSelect.sendKeys(option);
    }

    getWidthUnitMeasureCodeSelect(): ElementFinder {
        return this.widthUnitMeasureCodeSelect;
    }

    async getWidthUnitMeasureCodeSelectedOption() {
        return this.widthUnitMeasureCodeSelect.element(by.css('option:checked')).getText();
    }

    async heightUnitMeasureCodeSelectLastOption() {
        await this.heightUnitMeasureCodeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async heightUnitMeasureCodeSelectOption(option) {
        await this.heightUnitMeasureCodeSelect.sendKeys(option);
    }

    getHeightUnitMeasureCodeSelect(): ElementFinder {
        return this.heightUnitMeasureCodeSelect;
    }

    async getHeightUnitMeasureCodeSelectedOption() {
        return this.heightUnitMeasureCodeSelect.element(by.css('option:checked')).getText();
    }

    async productAttributeSelectLastOption() {
        await this.productAttributeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productAttributeSelectOption(option) {
        await this.productAttributeSelect.sendKeys(option);
    }

    getProductAttributeSelect(): ElementFinder {
        return this.productAttributeSelect;
    }

    async getProductAttributeSelectedOption() {
        return this.productAttributeSelect.element(by.css('option:checked')).getText();
    }

    async productOptionSelectLastOption() {
        await this.productOptionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productOptionSelectOption(option) {
        await this.productOptionSelect.sendKeys(option);
    }

    getProductOptionSelect(): ElementFinder {
        return this.productOptionSelect;
    }

    async getProductOptionSelectedOption() {
        return this.productOptionSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class StockItemsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-stockItems-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-stockItems'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
