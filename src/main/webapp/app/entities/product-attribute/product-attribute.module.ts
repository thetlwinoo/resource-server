import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductAttributeComponent,
    ProductAttributeDetailComponent,
    ProductAttributeUpdateComponent,
    ProductAttributeDeletePopupComponent,
    ProductAttributeDeleteDialogComponent,
    productAttributeRoute,
    productAttributePopupRoute
} from './';

const ENTITY_STATES = [...productAttributeRoute, ...productAttributePopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductAttributeComponent,
        ProductAttributeDetailComponent,
        ProductAttributeUpdateComponent,
        ProductAttributeDeleteDialogComponent,
        ProductAttributeDeletePopupComponent
    ],
    entryComponents: [
        ProductAttributeComponent,
        ProductAttributeUpdateComponent,
        ProductAttributeDeleteDialogComponent,
        ProductAttributeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductAttributeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
