import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    LastestMerchantUploadedDocumentComponent,
    LastestMerchantUploadedDocumentDetailComponent,
    LastestMerchantUploadedDocumentUpdateComponent,
    LastestMerchantUploadedDocumentDeletePopupComponent,
    LastestMerchantUploadedDocumentDeleteDialogComponent,
    lastestMerchantUploadedDocumentRoute,
    lastestMerchantUploadedDocumentPopupRoute
} from './';

const ENTITY_STATES = [...lastestMerchantUploadedDocumentRoute, ...lastestMerchantUploadedDocumentPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LastestMerchantUploadedDocumentComponent,
        LastestMerchantUploadedDocumentDetailComponent,
        LastestMerchantUploadedDocumentUpdateComponent,
        LastestMerchantUploadedDocumentDeleteDialogComponent,
        LastestMerchantUploadedDocumentDeletePopupComponent
    ],
    entryComponents: [
        LastestMerchantUploadedDocumentComponent,
        LastestMerchantUploadedDocumentUpdateComponent,
        LastestMerchantUploadedDocumentDeleteDialogComponent,
        LastestMerchantUploadedDocumentDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceLastestMerchantUploadedDocumentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
