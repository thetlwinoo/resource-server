import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ContactTypeComponent,
    ContactTypeDetailComponent,
    ContactTypeUpdateComponent,
    ContactTypeDeletePopupComponent,
    ContactTypeDeleteDialogComponent,
    contactTypeRoute,
    contactTypePopupRoute
} from './';

const ENTITY_STATES = [...contactTypeRoute, ...contactTypePopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContactTypeComponent,
        ContactTypeDetailComponent,
        ContactTypeUpdateComponent,
        ContactTypeDeleteDialogComponent,
        ContactTypeDeletePopupComponent
    ],
    entryComponents: [ContactTypeComponent, ContactTypeUpdateComponent, ContactTypeDeleteDialogComponent, ContactTypeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceContactTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
