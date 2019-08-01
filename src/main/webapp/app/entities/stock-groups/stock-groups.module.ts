import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    StockGroupsComponent,
    StockGroupsDetailComponent,
    StockGroupsUpdateComponent,
    StockGroupsDeletePopupComponent,
    StockGroupsDeleteDialogComponent,
    stockGroupsRoute,
    stockGroupsPopupRoute
} from './';

const ENTITY_STATES = [...stockGroupsRoute, ...stockGroupsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockGroupsComponent,
        StockGroupsDetailComponent,
        StockGroupsUpdateComponent,
        StockGroupsDeleteDialogComponent,
        StockGroupsDeletePopupComponent
    ],
    entryComponents: [StockGroupsComponent, StockGroupsUpdateComponent, StockGroupsDeleteDialogComponent, StockGroupsDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceStockGroupsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
