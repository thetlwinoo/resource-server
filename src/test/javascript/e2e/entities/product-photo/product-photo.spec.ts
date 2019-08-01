/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductPhotoComponentsPage, ProductPhotoDeleteDialog, ProductPhotoUpdatePage } from './product-photo.page-object';

const expect = chai.expect;

describe('ProductPhoto e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productPhotoUpdatePage: ProductPhotoUpdatePage;
    let productPhotoComponentsPage: ProductPhotoComponentsPage;
    let productPhotoDeleteDialog: ProductPhotoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductPhotos', async () => {
        await navBarPage.goToEntity('product-photo');
        productPhotoComponentsPage = new ProductPhotoComponentsPage();
        await browser.wait(ec.visibilityOf(productPhotoComponentsPage.title), 5000);
        expect(await productPhotoComponentsPage.getTitle()).to.eq('resourceApp.productPhoto.home.title');
    });

    it('should load create ProductPhoto page', async () => {
        await productPhotoComponentsPage.clickOnCreateButton();
        productPhotoUpdatePage = new ProductPhotoUpdatePage();
        expect(await productPhotoUpdatePage.getPageTitle()).to.eq('resourceApp.productPhoto.home.createOrEditLabel');
        await productPhotoUpdatePage.cancel();
    });

    it('should create and save ProductPhotos', async () => {
        const nbButtonsBeforeCreate = await productPhotoComponentsPage.countDeleteButtons();

        await productPhotoComponentsPage.clickOnCreateButton();
        await promise.all([
            productPhotoUpdatePage.setThumbnailPhotoInput('thumbnailPhoto'),
            productPhotoUpdatePage.setOriginalPhotoInput('originalPhoto'),
            productPhotoUpdatePage.setBannerTallPhotoInput('bannerTallPhoto'),
            productPhotoUpdatePage.setBannerWidePhotoInput('bannerWidePhoto'),
            productPhotoUpdatePage.setCirclePhotoInput('circlePhoto'),
            productPhotoUpdatePage.setSharpenedPhotoInput('sharpenedPhoto'),
            productPhotoUpdatePage.setSquarePhotoInput('squarePhoto'),
            productPhotoUpdatePage.setWatermarkPhotoInput('watermarkPhoto'),
            productPhotoUpdatePage.setPriorityInput('5'),
            productPhotoUpdatePage.setDeleteTokenInput('deleteToken'),
            productPhotoUpdatePage.productSelectLastOption()
        ]);
        expect(await productPhotoUpdatePage.getThumbnailPhotoInput()).to.eq('thumbnailPhoto');
        expect(await productPhotoUpdatePage.getOriginalPhotoInput()).to.eq('originalPhoto');
        expect(await productPhotoUpdatePage.getBannerTallPhotoInput()).to.eq('bannerTallPhoto');
        expect(await productPhotoUpdatePage.getBannerWidePhotoInput()).to.eq('bannerWidePhoto');
        expect(await productPhotoUpdatePage.getCirclePhotoInput()).to.eq('circlePhoto');
        expect(await productPhotoUpdatePage.getSharpenedPhotoInput()).to.eq('sharpenedPhoto');
        expect(await productPhotoUpdatePage.getSquarePhotoInput()).to.eq('squarePhoto');
        expect(await productPhotoUpdatePage.getWatermarkPhotoInput()).to.eq('watermarkPhoto');
        expect(await productPhotoUpdatePage.getPriorityInput()).to.eq('5');
        const selectedDefaultInd = productPhotoUpdatePage.getDefaultIndInput();
        if (await selectedDefaultInd.isSelected()) {
            await productPhotoUpdatePage.getDefaultIndInput().click();
            expect(await productPhotoUpdatePage.getDefaultIndInput().isSelected()).to.be.false;
        } else {
            await productPhotoUpdatePage.getDefaultIndInput().click();
            expect(await productPhotoUpdatePage.getDefaultIndInput().isSelected()).to.be.true;
        }
        expect(await productPhotoUpdatePage.getDeleteTokenInput()).to.eq('deleteToken');
        await productPhotoUpdatePage.save();
        expect(await productPhotoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productPhotoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductPhoto', async () => {
        const nbButtonsBeforeDelete = await productPhotoComponentsPage.countDeleteButtons();
        await productPhotoComponentsPage.clickOnLastDeleteButton();

        productPhotoDeleteDialog = new ProductPhotoDeleteDialog();
        expect(await productPhotoDeleteDialog.getDialogTitle()).to.eq('resourceApp.productPhoto.delete.question');
        await productPhotoDeleteDialog.clickOnConfirmButton();

        expect(await productPhotoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
