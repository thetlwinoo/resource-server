import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    StateProvincesComponent,
    StateProvincesDetailComponent,
    StateProvincesUpdateComponent,
    StateProvincesDeletePopupComponent,
    StateProvincesDeleteDialogComponent,
    stateProvincesRoute,
    stateProvincesPopupRoute
} from './';

const ENTITY_STATES = [...stateProvincesRoute, ...stateProvincesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StateProvincesComponent,
        StateProvincesDetailComponent,
        StateProvincesUpdateComponent,
        StateProvincesDeleteDialogComponent,
        StateProvincesDeletePopupComponent
    ],
    entryComponents: [
        StateProvincesComponent,
        StateProvincesUpdateComponent,
        StateProvincesDeleteDialogComponent,
        StateProvincesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceStateProvincesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
