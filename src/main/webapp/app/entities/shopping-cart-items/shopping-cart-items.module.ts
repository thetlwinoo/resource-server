import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ShoppingCartItemsComponent,
    ShoppingCartItemsDetailComponent,
    ShoppingCartItemsUpdateComponent,
    ShoppingCartItemsDeletePopupComponent,
    ShoppingCartItemsDeleteDialogComponent,
    shoppingCartItemsRoute,
    shoppingCartItemsPopupRoute
} from './';

const ENTITY_STATES = [...shoppingCartItemsRoute, ...shoppingCartItemsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ShoppingCartItemsComponent,
        ShoppingCartItemsDetailComponent,
        ShoppingCartItemsUpdateComponent,
        ShoppingCartItemsDeleteDialogComponent,
        ShoppingCartItemsDeletePopupComponent
    ],
    entryComponents: [
        ShoppingCartItemsComponent,
        ShoppingCartItemsUpdateComponent,
        ShoppingCartItemsDeleteDialogComponent,
        ShoppingCartItemsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceShoppingCartItemsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
