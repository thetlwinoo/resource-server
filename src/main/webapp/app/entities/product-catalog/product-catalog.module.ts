import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductCatalogComponent,
    ProductCatalogDetailComponent,
    ProductCatalogUpdateComponent,
    ProductCatalogDeletePopupComponent,
    ProductCatalogDeleteDialogComponent,
    productCatalogRoute,
    productCatalogPopupRoute
} from './';

const ENTITY_STATES = [...productCatalogRoute, ...productCatalogPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductCatalogComponent,
        ProductCatalogDetailComponent,
        ProductCatalogUpdateComponent,
        ProductCatalogDeleteDialogComponent,
        ProductCatalogDeletePopupComponent
    ],
    entryComponents: [
        ProductCatalogComponent,
        ProductCatalogUpdateComponent,
        ProductCatalogDeleteDialogComponent,
        ProductCatalogDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductCatalogModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
