import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    CultureComponent,
    CultureDetailComponent,
    CultureUpdateComponent,
    CultureDeletePopupComponent,
    CultureDeleteDialogComponent,
    cultureRoute,
    culturePopupRoute
} from './';

const ENTITY_STATES = [...cultureRoute, ...culturePopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CultureComponent,
        CultureDetailComponent,
        CultureUpdateComponent,
        CultureDeleteDialogComponent,
        CultureDeletePopupComponent
    ],
    entryComponents: [CultureComponent, CultureUpdateComponent, CultureDeleteDialogComponent, CultureDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceCultureModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
