/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MerchantsComponentsPage, MerchantsDeleteDialog, MerchantsUpdatePage } from './merchants.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Merchants e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let merchantsUpdatePage: MerchantsUpdatePage;
    let merchantsComponentsPage: MerchantsComponentsPage;
    let merchantsDeleteDialog: MerchantsDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Merchants', async () => {
        await navBarPage.goToEntity('merchants');
        merchantsComponentsPage = new MerchantsComponentsPage();
        await browser.wait(ec.visibilityOf(merchantsComponentsPage.title), 5000);
        expect(await merchantsComponentsPage.getTitle()).to.eq('resourceApp.merchants.home.title');
    });

    it('should load create Merchants page', async () => {
        await merchantsComponentsPage.clickOnCreateButton();
        merchantsUpdatePage = new MerchantsUpdatePage();
        expect(await merchantsUpdatePage.getPageTitle()).to.eq('resourceApp.merchants.home.createOrEditLabel');
        await merchantsUpdatePage.cancel();
    });

    it('should create and save Merchants', async () => {
        const nbButtonsBeforeCreate = await merchantsComponentsPage.countDeleteButtons();

        await merchantsComponentsPage.clickOnCreateButton();
        await promise.all([
            merchantsUpdatePage.setAccountNumberInput('accountNumber'),
            merchantsUpdatePage.setMerchantNameInput('merchantName'),
            merchantsUpdatePage.setCreditRatingInput('5'),
            merchantsUpdatePage.setWebServiceUrlInput('webServiceUrl'),
            merchantsUpdatePage.setWebSiteUrlInput('webSiteUrl'),
            merchantsUpdatePage.setAvatarInput(absolutePath),
            merchantsUpdatePage.personSelectLastOption()
        ]);
        expect(await merchantsUpdatePage.getAccountNumberInput()).to.eq('accountNumber');
        expect(await merchantsUpdatePage.getMerchantNameInput()).to.eq('merchantName');
        expect(await merchantsUpdatePage.getCreditRatingInput()).to.eq('5');
        const selectedActiveFlag = merchantsUpdatePage.getActiveFlagInput();
        if (await selectedActiveFlag.isSelected()) {
            await merchantsUpdatePage.getActiveFlagInput().click();
            expect(await merchantsUpdatePage.getActiveFlagInput().isSelected()).to.be.false;
        } else {
            await merchantsUpdatePage.getActiveFlagInput().click();
            expect(await merchantsUpdatePage.getActiveFlagInput().isSelected()).to.be.true;
        }
        expect(await merchantsUpdatePage.getWebServiceUrlInput()).to.eq('webServiceUrl');
        expect(await merchantsUpdatePage.getWebSiteUrlInput()).to.eq('webSiteUrl');
        expect(await merchantsUpdatePage.getAvatarInput()).to.endsWith(fileNameToUpload);
        await merchantsUpdatePage.save();
        expect(await merchantsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await merchantsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Merchants', async () => {
        const nbButtonsBeforeDelete = await merchantsComponentsPage.countDeleteButtons();
        await merchantsComponentsPage.clickOnLastDeleteButton();

        merchantsDeleteDialog = new MerchantsDeleteDialog();
        expect(await merchantsDeleteDialog.getDialogTitle()).to.eq('resourceApp.merchants.delete.question');
        await merchantsDeleteDialog.clickOnConfirmButton();

        expect(await merchantsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
