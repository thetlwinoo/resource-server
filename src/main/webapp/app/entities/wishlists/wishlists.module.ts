import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    WishlistsComponent,
    WishlistsDetailComponent,
    WishlistsUpdateComponent,
    WishlistsDeletePopupComponent,
    WishlistsDeleteDialogComponent,
    wishlistsRoute,
    wishlistsPopupRoute
} from './';

const ENTITY_STATES = [...wishlistsRoute, ...wishlistsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WishlistsComponent,
        WishlistsDetailComponent,
        WishlistsUpdateComponent,
        WishlistsDeleteDialogComponent,
        WishlistsDeletePopupComponent
    ],
    entryComponents: [WishlistsComponent, WishlistsUpdateComponent, WishlistsDeleteDialogComponent, WishlistsDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceWishlistsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
