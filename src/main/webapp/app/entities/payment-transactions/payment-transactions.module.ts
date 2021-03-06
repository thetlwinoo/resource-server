import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    PaymentTransactionsComponent,
    PaymentTransactionsDetailComponent,
    PaymentTransactionsUpdateComponent,
    PaymentTransactionsDeletePopupComponent,
    PaymentTransactionsDeleteDialogComponent,
    paymentTransactionsRoute,
    paymentTransactionsPopupRoute
} from './';

const ENTITY_STATES = [...paymentTransactionsRoute, ...paymentTransactionsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PaymentTransactionsComponent,
        PaymentTransactionsDetailComponent,
        PaymentTransactionsUpdateComponent,
        PaymentTransactionsDeleteDialogComponent,
        PaymentTransactionsDeletePopupComponent
    ],
    entryComponents: [
        PaymentTransactionsComponent,
        PaymentTransactionsUpdateComponent,
        PaymentTransactionsDeleteDialogComponent,
        PaymentTransactionsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourcePaymentTransactionsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
