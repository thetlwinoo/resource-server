import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    StockItemTransactionsComponent,
    StockItemTransactionsDetailComponent,
    StockItemTransactionsUpdateComponent,
    StockItemTransactionsDeletePopupComponent,
    StockItemTransactionsDeleteDialogComponent,
    stockItemTransactionsRoute,
    stockItemTransactionsPopupRoute
} from './';

const ENTITY_STATES = [...stockItemTransactionsRoute, ...stockItemTransactionsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockItemTransactionsComponent,
        StockItemTransactionsDetailComponent,
        StockItemTransactionsUpdateComponent,
        StockItemTransactionsDeleteDialogComponent,
        StockItemTransactionsDeletePopupComponent
    ],
    entryComponents: [
        StockItemTransactionsComponent,
        StockItemTransactionsUpdateComponent,
        StockItemTransactionsDeleteDialogComponent,
        StockItemTransactionsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceStockItemTransactionsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
