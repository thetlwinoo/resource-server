import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    BusinessEntityContactComponent,
    BusinessEntityContactDetailComponent,
    BusinessEntityContactUpdateComponent,
    BusinessEntityContactDeletePopupComponent,
    BusinessEntityContactDeleteDialogComponent,
    businessEntityContactRoute,
    businessEntityContactPopupRoute
} from './';

const ENTITY_STATES = [...businessEntityContactRoute, ...businessEntityContactPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BusinessEntityContactComponent,
        BusinessEntityContactDetailComponent,
        BusinessEntityContactUpdateComponent,
        BusinessEntityContactDeleteDialogComponent,
        BusinessEntityContactDeletePopupComponent
    ],
    entryComponents: [
        BusinessEntityContactComponent,
        BusinessEntityContactUpdateComponent,
        BusinessEntityContactDeleteDialogComponent,
        BusinessEntityContactDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceBusinessEntityContactModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
