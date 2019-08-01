import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    SystemParametersComponent,
    SystemParametersDetailComponent,
    SystemParametersUpdateComponent,
    SystemParametersDeletePopupComponent,
    SystemParametersDeleteDialogComponent,
    systemParametersRoute,
    systemParametersPopupRoute
} from './';

const ENTITY_STATES = [...systemParametersRoute, ...systemParametersPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SystemParametersComponent,
        SystemParametersDetailComponent,
        SystemParametersUpdateComponent,
        SystemParametersDeleteDialogComponent,
        SystemParametersDeletePopupComponent
    ],
    entryComponents: [
        SystemParametersComponent,
        SystemParametersUpdateComponent,
        SystemParametersDeleteDialogComponent,
        SystemParametersDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceSystemParametersModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
