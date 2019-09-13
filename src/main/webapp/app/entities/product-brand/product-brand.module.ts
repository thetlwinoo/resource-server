import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductBrandComponent,
    ProductBrandDetailComponent,
    ProductBrandUpdateComponent,
    ProductBrandDeletePopupComponent,
    ProductBrandDeleteDialogComponent,
    productBrandRoute,
    productBrandPopupRoute
} from './';

const ENTITY_STATES = [...productBrandRoute, ...productBrandPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductBrandComponent,
        ProductBrandDetailComponent,
        ProductBrandUpdateComponent,
        ProductBrandDeleteDialogComponent,
        ProductBrandDeletePopupComponent
    ],
    entryComponents: [
        ProductBrandComponent,
        ProductBrandUpdateComponent,
        ProductBrandDeleteDialogComponent,
        ProductBrandDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductBrandModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
