import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    SuppliersComponent,
    SuppliersDetailComponent,
    SuppliersUpdateComponent,
    SuppliersDeletePopupComponent,
    SuppliersDeleteDialogComponent,
    suppliersRoute,
    suppliersPopupRoute
} from './';

const ENTITY_STATES = [...suppliersRoute, ...suppliersPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SuppliersComponent,
        SuppliersDetailComponent,
        SuppliersUpdateComponent,
        SuppliersDeleteDialogComponent,
        SuppliersDeletePopupComponent
    ],
    entryComponents: [SuppliersComponent, SuppliersUpdateComponent, SuppliersDeleteDialogComponent, SuppliersDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceSuppliersModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
