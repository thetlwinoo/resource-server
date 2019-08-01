import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductsComponent,
    ProductsDetailComponent,
    ProductsUpdateComponent,
    ProductsDeletePopupComponent,
    ProductsDeleteDialogComponent,
    productsRoute,
    productsPopupRoute
} from './';

const ENTITY_STATES = [...productsRoute, ...productsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductsComponent,
        ProductsDetailComponent,
        ProductsUpdateComponent,
        ProductsDeleteDialogComponent,
        ProductsDeletePopupComponent
    ],
    entryComponents: [ProductsComponent, ProductsUpdateComponent, ProductsDeleteDialogComponent, ProductsDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
