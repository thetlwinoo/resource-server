/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrdersComponentsPage, OrdersDeleteDialog, OrdersUpdatePage } from './orders.page-object';

const expect = chai.expect;

describe('Orders e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let ordersUpdatePage: OrdersUpdatePage;
    let ordersComponentsPage: OrdersComponentsPage;
    let ordersDeleteDialog: OrdersDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Orders', async () => {
        await navBarPage.goToEntity('orders');
        ordersComponentsPage = new OrdersComponentsPage();
        await browser.wait(ec.visibilityOf(ordersComponentsPage.title), 5000);
        expect(await ordersComponentsPage.getTitle()).to.eq('resourceApp.orders.home.title');
    });

    it('should load create Orders page', async () => {
        await ordersComponentsPage.clickOnCreateButton();
        ordersUpdatePage = new OrdersUpdatePage();
        expect(await ordersUpdatePage.getPageTitle()).to.eq('resourceApp.orders.home.createOrEditLabel');
        await ordersUpdatePage.cancel();
    });

    it('should create and save Orders', async () => {
        const nbButtonsBeforeCreate = await ordersComponentsPage.countDeleteButtons();

        await ordersComponentsPage.clickOnCreateButton();
        await promise.all([
            ordersUpdatePage.setOrderDateInput('2000-12-31'),
            ordersUpdatePage.setDueDateInput('2000-12-31'),
            ordersUpdatePage.setShipDateInput('2000-12-31'),
            ordersUpdatePage.setPaymentStatusInput('5'),
            ordersUpdatePage.setOrderFlagInput('5'),
            ordersUpdatePage.setOrderNumberInput('orderNumber'),
            ordersUpdatePage.setSubTotalInput('5'),
            ordersUpdatePage.setTaxAmountInput('5'),
            ordersUpdatePage.setFrieightInput('5'),
            ordersUpdatePage.setTotalDueInput('5'),
            ordersUpdatePage.setCommentsInput('comments'),
            ordersUpdatePage.setDeliveryInstructionsInput('deliveryInstructions'),
            ordersUpdatePage.setInternalCommentsInput('internalComments'),
            ordersUpdatePage.setPickingCompletedWhenInput('2000-12-31'),
            ordersUpdatePage.orderReviewSelectLastOption(),
            ordersUpdatePage.customerSelectLastOption(),
            ordersUpdatePage.shipToAddressSelectLastOption(),
            ordersUpdatePage.billToAddressSelectLastOption(),
            ordersUpdatePage.shipMethodSelectLastOption(),
            ordersUpdatePage.currencyRateSelectLastOption(),
            ordersUpdatePage.specialDealsSelectLastOption()
        ]);
        expect(await ordersUpdatePage.getOrderDateInput()).to.eq('2000-12-31');
        expect(await ordersUpdatePage.getDueDateInput()).to.eq('2000-12-31');
        expect(await ordersUpdatePage.getShipDateInput()).to.eq('2000-12-31');
        expect(await ordersUpdatePage.getPaymentStatusInput()).to.eq('5');
        expect(await ordersUpdatePage.getOrderFlagInput()).to.eq('5');
        expect(await ordersUpdatePage.getOrderNumberInput()).to.eq('orderNumber');
        expect(await ordersUpdatePage.getSubTotalInput()).to.eq('5');
        expect(await ordersUpdatePage.getTaxAmountInput()).to.eq('5');
        expect(await ordersUpdatePage.getFrieightInput()).to.eq('5');
        expect(await ordersUpdatePage.getTotalDueInput()).to.eq('5');
        expect(await ordersUpdatePage.getCommentsInput()).to.eq('comments');
        expect(await ordersUpdatePage.getDeliveryInstructionsInput()).to.eq('deliveryInstructions');
        expect(await ordersUpdatePage.getInternalCommentsInput()).to.eq('internalComments');
        expect(await ordersUpdatePage.getPickingCompletedWhenInput()).to.eq('2000-12-31');
        await ordersUpdatePage.save();
        expect(await ordersUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await ordersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Orders', async () => {
        const nbButtonsBeforeDelete = await ordersComponentsPage.countDeleteButtons();
        await ordersComponentsPage.clickOnLastDeleteButton();

        ordersDeleteDialog = new OrdersDeleteDialog();
        expect(await ordersDeleteDialog.getDialogTitle()).to.eq('resourceApp.orders.delete.question');
        await ordersDeleteDialog.clickOnConfirmButton();

        expect(await ordersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
