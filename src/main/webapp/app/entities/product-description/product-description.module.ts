import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductDescriptionComponent,
    ProductDescriptionDetailComponent,
    ProductDescriptionUpdateComponent,
    ProductDescriptionDeletePopupComponent,
    ProductDescriptionDeleteDialogComponent,
    productDescriptionRoute,
    productDescriptionPopupRoute
} from './';

const ENTITY_STATES = [...productDescriptionRoute, ...productDescriptionPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductDescriptionComponent,
        ProductDescriptionDetailComponent,
        ProductDescriptionUpdateComponent,
        ProductDescriptionDeleteDialogComponent,
        ProductDescriptionDeletePopupComponent
    ],
    entryComponents: [
        ProductDescriptionComponent,
        ProductDescriptionUpdateComponent,
        ProductDescriptionDeleteDialogComponent,
        ProductDescriptionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductDescriptionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
