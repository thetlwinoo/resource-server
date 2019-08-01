import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    WishlistProductsComponent,
    WishlistProductsDetailComponent,
    WishlistProductsUpdateComponent,
    WishlistProductsDeletePopupComponent,
    WishlistProductsDeleteDialogComponent,
    wishlistProductsRoute,
    wishlistProductsPopupRoute
} from './';

const ENTITY_STATES = [...wishlistProductsRoute, ...wishlistProductsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WishlistProductsComponent,
        WishlistProductsDetailComponent,
        WishlistProductsUpdateComponent,
        WishlistProductsDeleteDialogComponent,
        WishlistProductsDeletePopupComponent
    ],
    entryComponents: [
        WishlistProductsComponent,
        WishlistProductsUpdateComponent,
        WishlistProductsDeleteDialogComponent,
        WishlistProductsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceWishlistProductsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
