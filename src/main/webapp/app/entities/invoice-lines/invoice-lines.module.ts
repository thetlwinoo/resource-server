import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    InvoiceLinesComponent,
    InvoiceLinesDetailComponent,
    InvoiceLinesUpdateComponent,
    InvoiceLinesDeletePopupComponent,
    InvoiceLinesDeleteDialogComponent,
    invoiceLinesRoute,
    invoiceLinesPopupRoute
} from './';

const ENTITY_STATES = [...invoiceLinesRoute, ...invoiceLinesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvoiceLinesComponent,
        InvoiceLinesDetailComponent,
        InvoiceLinesUpdateComponent,
        InvoiceLinesDeleteDialogComponent,
        InvoiceLinesDeletePopupComponent
    ],
    entryComponents: [
        InvoiceLinesComponent,
        InvoiceLinesUpdateComponent,
        InvoiceLinesDeleteDialogComponent,
        InvoiceLinesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceInvoiceLinesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
