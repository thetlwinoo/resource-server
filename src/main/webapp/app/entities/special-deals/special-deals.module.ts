import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    SpecialDealsComponent,
    SpecialDealsDetailComponent,
    SpecialDealsUpdateComponent,
    SpecialDealsDeletePopupComponent,
    SpecialDealsDeleteDialogComponent,
    specialDealsRoute,
    specialDealsPopupRoute
} from './';

const ENTITY_STATES = [...specialDealsRoute, ...specialDealsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SpecialDealsComponent,
        SpecialDealsDetailComponent,
        SpecialDealsUpdateComponent,
        SpecialDealsDeleteDialogComponent,
        SpecialDealsDeletePopupComponent
    ],
    entryComponents: [
        SpecialDealsComponent,
        SpecialDealsUpdateComponent,
        SpecialDealsDeleteDialogComponent,
        SpecialDealsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceSpecialDealsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
