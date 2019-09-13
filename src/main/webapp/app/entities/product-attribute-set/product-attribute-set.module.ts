import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductAttributeSetComponent,
    ProductAttributeSetDetailComponent,
    ProductAttributeSetUpdateComponent,
    ProductAttributeSetDeletePopupComponent,
    ProductAttributeSetDeleteDialogComponent,
    productAttributeSetRoute,
    productAttributeSetPopupRoute
} from './';

const ENTITY_STATES = [...productAttributeSetRoute, ...productAttributeSetPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductAttributeSetComponent,
        ProductAttributeSetDetailComponent,
        ProductAttributeSetUpdateComponent,
        ProductAttributeSetDeleteDialogComponent,
        ProductAttributeSetDeletePopupComponent
    ],
    entryComponents: [
        ProductAttributeSetComponent,
        ProductAttributeSetUpdateComponent,
        ProductAttributeSetDeleteDialogComponent,
        ProductAttributeSetDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductAttributeSetModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
