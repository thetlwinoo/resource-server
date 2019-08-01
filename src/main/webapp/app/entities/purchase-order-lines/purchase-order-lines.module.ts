import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    PurchaseOrderLinesComponent,
    PurchaseOrderLinesDetailComponent,
    PurchaseOrderLinesUpdateComponent,
    PurchaseOrderLinesDeletePopupComponent,
    PurchaseOrderLinesDeleteDialogComponent,
    purchaseOrderLinesRoute,
    purchaseOrderLinesPopupRoute
} from './';

const ENTITY_STATES = [...purchaseOrderLinesRoute, ...purchaseOrderLinesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PurchaseOrderLinesComponent,
        PurchaseOrderLinesDetailComponent,
        PurchaseOrderLinesUpdateComponent,
        PurchaseOrderLinesDeleteDialogComponent,
        PurchaseOrderLinesDeletePopupComponent
    ],
    entryComponents: [
        PurchaseOrderLinesComponent,
        PurchaseOrderLinesUpdateComponent,
        PurchaseOrderLinesDeleteDialogComponent,
        PurchaseOrderLinesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourcePurchaseOrderLinesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
