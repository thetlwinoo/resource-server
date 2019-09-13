import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductModelDescriptionComponent,
    ProductModelDescriptionDetailComponent,
    ProductModelDescriptionUpdateComponent,
    ProductModelDescriptionDeletePopupComponent,
    ProductModelDescriptionDeleteDialogComponent,
    productModelDescriptionRoute,
    productModelDescriptionPopupRoute
} from './';

const ENTITY_STATES = [...productModelDescriptionRoute, ...productModelDescriptionPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductModelDescriptionComponent,
        ProductModelDescriptionDetailComponent,
        ProductModelDescriptionUpdateComponent,
        ProductModelDescriptionDeleteDialogComponent,
        ProductModelDescriptionDeletePopupComponent
    ],
    entryComponents: [
        ProductModelDescriptionComponent,
        ProductModelDescriptionUpdateComponent,
        ProductModelDescriptionDeleteDialogComponent,
        ProductModelDescriptionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductModelDescriptionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
