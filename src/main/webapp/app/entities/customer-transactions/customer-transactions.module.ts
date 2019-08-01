import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    CustomerTransactionsComponent,
    CustomerTransactionsDetailComponent,
    CustomerTransactionsUpdateComponent,
    CustomerTransactionsDeletePopupComponent,
    CustomerTransactionsDeleteDialogComponent,
    customerTransactionsRoute,
    customerTransactionsPopupRoute
} from './';

const ENTITY_STATES = [...customerTransactionsRoute, ...customerTransactionsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CustomerTransactionsComponent,
        CustomerTransactionsDetailComponent,
        CustomerTransactionsUpdateComponent,
        CustomerTransactionsDeleteDialogComponent,
        CustomerTransactionsDeletePopupComponent
    ],
    entryComponents: [
        CustomerTransactionsComponent,
        CustomerTransactionsUpdateComponent,
        CustomerTransactionsDeleteDialogComponent,
        CustomerTransactionsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceCustomerTransactionsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
