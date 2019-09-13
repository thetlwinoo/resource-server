import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductChoiceComponent,
    ProductChoiceDetailComponent,
    ProductChoiceUpdateComponent,
    ProductChoiceDeletePopupComponent,
    ProductChoiceDeleteDialogComponent,
    productChoiceRoute,
    productChoicePopupRoute
} from './';

const ENTITY_STATES = [...productChoiceRoute, ...productChoicePopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductChoiceComponent,
        ProductChoiceDetailComponent,
        ProductChoiceUpdateComponent,
        ProductChoiceDeleteDialogComponent,
        ProductChoiceDeletePopupComponent
    ],
    entryComponents: [
        ProductChoiceComponent,
        ProductChoiceUpdateComponent,
        ProductChoiceDeleteDialogComponent,
        ProductChoiceDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductChoiceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
