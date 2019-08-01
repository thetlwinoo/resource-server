import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    SupplierTransactionsComponent,
    SupplierTransactionsDetailComponent,
    SupplierTransactionsUpdateComponent,
    SupplierTransactionsDeletePopupComponent,
    SupplierTransactionsDeleteDialogComponent,
    supplierTransactionsRoute,
    supplierTransactionsPopupRoute
} from './';

const ENTITY_STATES = [...supplierTransactionsRoute, ...supplierTransactionsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplierTransactionsComponent,
        SupplierTransactionsDetailComponent,
        SupplierTransactionsUpdateComponent,
        SupplierTransactionsDeleteDialogComponent,
        SupplierTransactionsDeletePopupComponent
    ],
    entryComponents: [
        SupplierTransactionsComponent,
        SupplierTransactionsUpdateComponent,
        SupplierTransactionsDeleteDialogComponent,
        SupplierTransactionsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceSupplierTransactionsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
