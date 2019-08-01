import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    StockItemStockGroupsComponent,
    StockItemStockGroupsDetailComponent,
    StockItemStockGroupsUpdateComponent,
    StockItemStockGroupsDeletePopupComponent,
    StockItemStockGroupsDeleteDialogComponent,
    stockItemStockGroupsRoute,
    stockItemStockGroupsPopupRoute
} from './';

const ENTITY_STATES = [...stockItemStockGroupsRoute, ...stockItemStockGroupsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockItemStockGroupsComponent,
        StockItemStockGroupsDetailComponent,
        StockItemStockGroupsUpdateComponent,
        StockItemStockGroupsDeleteDialogComponent,
        StockItemStockGroupsDeletePopupComponent
    ],
    entryComponents: [
        StockItemStockGroupsComponent,
        StockItemStockGroupsUpdateComponent,
        StockItemStockGroupsDeleteDialogComponent,
        StockItemStockGroupsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceStockItemStockGroupsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
