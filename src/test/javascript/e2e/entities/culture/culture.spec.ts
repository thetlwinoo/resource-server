/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CultureComponentsPage, CultureDeleteDialog, CultureUpdatePage } from './culture.page-object';

const expect = chai.expect;

describe('Culture e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let cultureUpdatePage: CultureUpdatePage;
    let cultureComponentsPage: CultureComponentsPage;
    let cultureDeleteDialog: CultureDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Cultures', async () => {
        await navBarPage.goToEntity('culture');
        cultureComponentsPage = new CultureComponentsPage();
        await browser.wait(ec.visibilityOf(cultureComponentsPage.title), 5000);
        expect(await cultureComponentsPage.getTitle()).to.eq('resourceApp.culture.home.title');
    });

    it('should load create Culture page', async () => {
        await cultureComponentsPage.clickOnCreateButton();
        cultureUpdatePage = new CultureUpdatePage();
        expect(await cultureUpdatePage.getPageTitle()).to.eq('resourceApp.culture.home.createOrEditLabel');
        await cultureUpdatePage.cancel();
    });

    it('should create and save Cultures', async () => {
        const nbButtonsBeforeCreate = await cultureComponentsPage.countDeleteButtons();

        await cultureComponentsPage.clickOnCreateButton();
        await promise.all([cultureUpdatePage.setCultureCodeInput('cultureCode'), cultureUpdatePage.setCultureNameInput('cultureName')]);
        expect(await cultureUpdatePage.getCultureCodeInput()).to.eq('cultureCode');
        expect(await cultureUpdatePage.getCultureNameInput()).to.eq('cultureName');
        await cultureUpdatePage.save();
        expect(await cultureUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await cultureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Culture', async () => {
        const nbButtonsBeforeDelete = await cultureComponentsPage.countDeleteButtons();
        await cultureComponentsPage.clickOnLastDeleteButton();

        cultureDeleteDialog = new CultureDeleteDialog();
        expect(await cultureDeleteDialog.getDialogTitle()).to.eq('resourceApp.culture.delete.question');
        await cultureDeleteDialog.clickOnConfirmButton();

        expect(await cultureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
