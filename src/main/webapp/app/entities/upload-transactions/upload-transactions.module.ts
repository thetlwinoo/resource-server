import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    UploadTransactionsComponent,
    UploadTransactionsDetailComponent,
    UploadTransactionsUpdateComponent,
    UploadTransactionsDeletePopupComponent,
    UploadTransactionsDeleteDialogComponent,
    uploadTransactionsRoute,
    uploadTransactionsPopupRoute
} from './';

const ENTITY_STATES = [...uploadTransactionsRoute, ...uploadTransactionsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UploadTransactionsComponent,
        UploadTransactionsDetailComponent,
        UploadTransactionsUpdateComponent,
        UploadTransactionsDeleteDialogComponent,
        UploadTransactionsDeletePopupComponent
    ],
    entryComponents: [
        UploadTransactionsComponent,
        UploadTransactionsUpdateComponent,
        UploadTransactionsDeleteDialogComponent,
        UploadTransactionsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceUploadTransactionsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
