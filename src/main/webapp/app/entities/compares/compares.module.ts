import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ComparesComponent,
    ComparesDetailComponent,
    ComparesUpdateComponent,
    ComparesDeletePopupComponent,
    ComparesDeleteDialogComponent,
    comparesRoute,
    comparesPopupRoute
} from './';

const ENTITY_STATES = [...comparesRoute, ...comparesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ComparesComponent,
        ComparesDetailComponent,
        ComparesUpdateComponent,
        ComparesDeleteDialogComponent,
        ComparesDeletePopupComponent
    ],
    entryComponents: [ComparesComponent, ComparesUpdateComponent, ComparesDeleteDialogComponent, ComparesDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceComparesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
