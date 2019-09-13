import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    StockItemsComponent,
    StockItemsDetailComponent,
    StockItemsUpdateComponent,
    StockItemsDeletePopupComponent,
    StockItemsDeleteDialogComponent,
    stockItemsRoute,
    stockItemsPopupRoute
} from './';

const ENTITY_STATES = [...stockItemsRoute, ...stockItemsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockItemsComponent,
        StockItemsDetailComponent,
        StockItemsUpdateComponent,
        StockItemsDeleteDialogComponent,
        StockItemsDeletePopupComponent
    ],
    entryComponents: [StockItemsComponent, StockItemsUpdateComponent, StockItemsDeleteDialogComponent, StockItemsDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceStockItemsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
