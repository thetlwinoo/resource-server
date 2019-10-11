import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    StockItemTempComponent,
    StockItemTempDetailComponent,
    StockItemTempUpdateComponent,
    StockItemTempDeletePopupComponent,
    StockItemTempDeleteDialogComponent,
    stockItemTempRoute,
    stockItemTempPopupRoute
} from './';

const ENTITY_STATES = [...stockItemTempRoute, ...stockItemTempPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockItemTempComponent,
        StockItemTempDetailComponent,
        StockItemTempUpdateComponent,
        StockItemTempDeleteDialogComponent,
        StockItemTempDeletePopupComponent
    ],
    entryComponents: [
        StockItemTempComponent,
        StockItemTempUpdateComponent,
        StockItemTempDeleteDialogComponent,
        StockItemTempDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceStockItemTempModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
