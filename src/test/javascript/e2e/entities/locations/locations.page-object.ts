import { element, by, ElementFinder } from 'protractor';

export class LocationsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-locations div table .btn-danger'));
    title = element.all(by.css('jhi-locations div h2#page-heading span')).first();

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

export class LocationsUpdatePage {
    pageTitle = element(by.id('jhi-locations-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    locationNameInput = element(by.id('field_locationName'));
    costRateInput = element(by.id('field_costRate'));
    availabilityInput = element(by.id('field_availability'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLocationNameInput(locationName) {
        await this.locationNameInput.sendKeys(locationName);
    }

    async getLocationNameInput() {
        return this.locationNameInput.getAttribute('value');
    }

    async setCostRateInput(costRate) {
        await this.costRateInput.sendKeys(costRate);
    }

    async getCostRateInput() {
        return this.costRateInput.getAttribute('value');
    }

    async setAvailabilityInput(availability) {
        await this.availabilityInput.sendKeys(availability);
    }

    async getAvailabilityInput() {
        return this.availabilityInput.getAttribute('value');
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

export class LocationsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-locations-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-locations'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
