import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    SupplierImportedDocumentComponent,
    SupplierImportedDocumentDetailComponent,
    SupplierImportedDocumentUpdateComponent,
    SupplierImportedDocumentDeletePopupComponent,
    SupplierImportedDocumentDeleteDialogComponent,
    supplierImportedDocumentRoute,
    supplierImportedDocumentPopupRoute
} from './';

const ENTITY_STATES = [...supplierImportedDocumentRoute, ...supplierImportedDocumentPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplierImportedDocumentComponent,
        SupplierImportedDocumentDetailComponent,
        SupplierImportedDocumentUpdateComponent,
        SupplierImportedDocumentDeleteDialogComponent,
        SupplierImportedDocumentDeletePopupComponent
    ],
    entryComponents: [
        SupplierImportedDocumentComponent,
        SupplierImportedDocumentUpdateComponent,
        SupplierImportedDocumentDeleteDialogComponent,
        SupplierImportedDocumentDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceSupplierImportedDocumentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
