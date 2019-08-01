import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ShoppingCartsComponent,
    ShoppingCartsDetailComponent,
    ShoppingCartsUpdateComponent,
    ShoppingCartsDeletePopupComponent,
    ShoppingCartsDeleteDialogComponent,
    shoppingCartsRoute,
    shoppingCartsPopupRoute
} from './';

const ENTITY_STATES = [...shoppingCartsRoute, ...shoppingCartsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ShoppingCartsComponent,
        ShoppingCartsDetailComponent,
        ShoppingCartsUpdateComponent,
        ShoppingCartsDeleteDialogComponent,
        ShoppingCartsDeletePopupComponent
    ],
    entryComponents: [
        ShoppingCartsComponent,
        ShoppingCartsUpdateComponent,
        ShoppingCartsDeleteDialogComponent,
        ShoppingCartsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceShoppingCartsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
