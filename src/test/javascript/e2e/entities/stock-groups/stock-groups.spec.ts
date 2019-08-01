/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StockGroupsComponentsPage, StockGroupsDeleteDialog, StockGroupsUpdatePage } from './stock-groups.page-object';

const expect = chai.expect;

describe('StockGroups e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let stockGroupsUpdatePage: StockGroupsUpdatePage;
    let stockGroupsComponentsPage: StockGroupsComponentsPage;
    let stockGroupsDeleteDialog: StockGroupsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load StockGroups', async () => {
        await navBarPage.goToEntity('stock-groups');
        stockGroupsComponentsPage = new StockGroupsComponentsPage();
        await browser.wait(ec.visibilityOf(stockGroupsComponentsPage.title), 5000);
        expect(await stockGroupsComponentsPage.getTitle()).to.eq('resourceApp.stockGroups.home.title');
    });

    it('should load create StockGroups page', async () => {
        await stockGroupsComponentsPage.clickOnCreateButton();
        stockGroupsUpdatePage = new StockGroupsUpdatePage();
        expect(await stockGroupsUpdatePage.getPageTitle()).to.eq('resourceApp.stockGroups.home.createOrEditLabel');
        await stockGroupsUpdatePage.cancel();
    });

    it('should create and save StockGroups', async () => {
        const nbButtonsBeforeCreate = await stockGroupsComponentsPage.countDeleteButtons();

        await stockGroupsComponentsPage.clickOnCreateButton();
        await promise.all([
            stockGroupsUpdatePage.setStockGroupNameInput('stockGroupName'),
            stockGroupsUpdatePage.setValidFromInput('2000-12-31'),
            stockGroupsUpdatePage.setValidToInput('2000-12-31')
        ]);
        expect(await stockGroupsUpdatePage.getStockGroupNameInput()).to.eq('stockGroupName');
        expect(await stockGroupsUpdatePage.getValidFromInput()).to.eq('2000-12-31');
        expect(await stockGroupsUpdatePage.getValidToInput()).to.eq('2000-12-31');
        await stockGroupsUpdatePage.save();
        expect(await stockGroupsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await stockGroupsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last StockGroups', async () => {
        const nbButtonsBeforeDelete = await stockGroupsComponentsPage.countDeleteButtons();
        await stockGroupsComponentsPage.clickOnLastDeleteButton();

        stockGroupsDeleteDialog = new StockGroupsDeleteDialog();
        expect(await stockGroupsDeleteDialog.getDialogTitle()).to.eq('resourceApp.stockGroups.delete.question');
        await stockGroupsDeleteDialog.clickOnConfirmButton();

        expect(await stockGroupsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
