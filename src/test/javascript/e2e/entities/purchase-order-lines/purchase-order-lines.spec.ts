/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    PurchaseOrderLinesComponentsPage,
    PurchaseOrderLinesDeleteDialog,
    PurchaseOrderLinesUpdatePage
} from './purchase-order-lines.page-object';

const expect = chai.expect;

describe('PurchaseOrderLines e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let purchaseOrderLinesUpdatePage: PurchaseOrderLinesUpdatePage;
    let purchaseOrderLinesComponentsPage: PurchaseOrderLinesComponentsPage;
    let purchaseOrderLinesDeleteDialog: PurchaseOrderLinesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PurchaseOrderLines', async () => {
        await navBarPage.goToEntity('purchase-order-lines');
        purchaseOrderLinesComponentsPage = new PurchaseOrderLinesComponentsPage();
        await browser.wait(ec.visibilityOf(purchaseOrderLinesComponentsPage.title), 5000);
        expect(await purchaseOrderLinesComponentsPage.getTitle()).to.eq('resourceApp.purchaseOrderLines.home.title');
    });

    it('should load create PurchaseOrderLines page', async () => {
        await purchaseOrderLinesComponentsPage.clickOnCreateButton();
        purchaseOrderLinesUpdatePage = new PurchaseOrderLinesUpdatePage();
        expect(await purchaseOrderLinesUpdatePage.getPageTitle()).to.eq('resourceApp.purchaseOrderLines.home.createOrEditLabel');
        await purchaseOrderLinesUpdatePage.cancel();
    });

    it('should create and save PurchaseOrderLines', async () => {
        const nbButtonsBeforeCreate = await purchaseOrderLinesComponentsPage.countDeleteButtons();

        await purchaseOrderLinesComponentsPage.clickOnCreateButton();
        await promise.all([
            purchaseOrderLinesUpdatePage.setOrdersOutersInput('5'),
            purchaseOrderLinesUpdatePage.setDescriptionInput('description'),
            purchaseOrderLinesUpdatePage.setReceivedOutersInput('5'),
            purchaseOrderLinesUpdatePage.setExpectedUnitPricePerOuterInput('5'),
            purchaseOrderLinesUpdatePage.setLastReceiptDateInput('2000-12-31'),
            purchaseOrderLinesUpdatePage.productSelectLastOption(),
            purchaseOrderLinesUpdatePage.packageTypeSelectLastOption(),
            purchaseOrderLinesUpdatePage.purchaseOrderSelectLastOption()
        ]);
        expect(await purchaseOrderLinesUpdatePage.getOrdersOutersInput()).to.eq('5');
        expect(await purchaseOrderLinesUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await purchaseOrderLinesUpdatePage.getReceivedOutersInput()).to.eq('5');
        expect(await purchaseOrderLinesUpdatePage.getExpectedUnitPricePerOuterInput()).to.eq('5');
        expect(await purchaseOrderLinesUpdatePage.getLastReceiptDateInput()).to.eq('2000-12-31');
        const selectedIsOrderLineFinalized = purchaseOrderLinesUpdatePage.getIsOrderLineFinalizedInput();
        if (await selectedIsOrderLineFinalized.isSelected()) {
            await purchaseOrderLinesUpdatePage.getIsOrderLineFinalizedInput().click();
            expect(await purchaseOrderLinesUpdatePage.getIsOrderLineFinalizedInput().isSelected()).to.be.false;
        } else {
            await purchaseOrderLinesUpdatePage.getIsOrderLineFinalizedInput().click();
            expect(await purchaseOrderLinesUpdatePage.getIsOrderLineFinalizedInput().isSelected()).to.be.true;
        }
        await purchaseOrderLinesUpdatePage.save();
        expect(await purchaseOrderLinesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await purchaseOrderLinesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PurchaseOrderLines', async () => {
        const nbButtonsBeforeDelete = await purchaseOrderLinesComponentsPage.countDeleteButtons();
        await purchaseOrderLinesComponentsPage.clickOnLastDeleteButton();

        purchaseOrderLinesDeleteDialog = new PurchaseOrderLinesDeleteDialog();
        expect(await purchaseOrderLinesDeleteDialog.getDialogTitle()).to.eq('resourceApp.purchaseOrderLines.delete.question');
        await purchaseOrderLinesDeleteDialog.clickOnConfirmButton();

        expect(await purchaseOrderLinesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
