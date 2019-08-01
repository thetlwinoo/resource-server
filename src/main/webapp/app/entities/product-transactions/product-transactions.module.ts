import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductTransactionsComponent,
    ProductTransactionsDetailComponent,
    ProductTransactionsUpdateComponent,
    ProductTransactionsDeletePopupComponent,
    ProductTransactionsDeleteDialogComponent,
    productTransactionsRoute,
    productTransactionsPopupRoute
} from './';

const ENTITY_STATES = [...productTransactionsRoute, ...productTransactionsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductTransactionsComponent,
        ProductTransactionsDetailComponent,
        ProductTransactionsUpdateComponent,
        ProductTransactionsDeleteDialogComponent,
        ProductTransactionsDeletePopupComponent
    ],
    entryComponents: [
        ProductTransactionsComponent,
        ProductTransactionsUpdateComponent,
        ProductTransactionsDeleteDialogComponent,
        ProductTransactionsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductTransactionsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
