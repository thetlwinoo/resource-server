import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    StockItemHoldingsComponent,
    StockItemHoldingsDetailComponent,
    StockItemHoldingsUpdateComponent,
    StockItemHoldingsDeletePopupComponent,
    StockItemHoldingsDeleteDialogComponent,
    stockItemHoldingsRoute,
    stockItemHoldingsPopupRoute
} from './';

const ENTITY_STATES = [...stockItemHoldingsRoute, ...stockItemHoldingsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockItemHoldingsComponent,
        StockItemHoldingsDetailComponent,
        StockItemHoldingsUpdateComponent,
        StockItemHoldingsDeleteDialogComponent,
        StockItemHoldingsDeletePopupComponent
    ],
    entryComponents: [
        StockItemHoldingsComponent,
        StockItemHoldingsUpdateComponent,
        StockItemHoldingsDeleteDialogComponent,
        StockItemHoldingsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceStockItemHoldingsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
