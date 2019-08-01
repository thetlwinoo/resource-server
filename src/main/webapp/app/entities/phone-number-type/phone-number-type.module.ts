import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    PhoneNumberTypeComponent,
    PhoneNumberTypeDetailComponent,
    PhoneNumberTypeUpdateComponent,
    PhoneNumberTypeDeletePopupComponent,
    PhoneNumberTypeDeleteDialogComponent,
    phoneNumberTypeRoute,
    phoneNumberTypePopupRoute
} from './';

const ENTITY_STATES = [...phoneNumberTypeRoute, ...phoneNumberTypePopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PhoneNumberTypeComponent,
        PhoneNumberTypeDetailComponent,
        PhoneNumberTypeUpdateComponent,
        PhoneNumberTypeDeleteDialogComponent,
        PhoneNumberTypeDeletePopupComponent
    ],
    entryComponents: [
        PhoneNumberTypeComponent,
        PhoneNumberTypeUpdateComponent,
        PhoneNumberTypeDeleteDialogComponent,
        PhoneNumberTypeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourcePhoneNumberTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
