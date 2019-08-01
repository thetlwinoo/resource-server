import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    PaymentMethodsComponent,
    PaymentMethodsDetailComponent,
    PaymentMethodsUpdateComponent,
    PaymentMethodsDeletePopupComponent,
    PaymentMethodsDeleteDialogComponent,
    paymentMethodsRoute,
    paymentMethodsPopupRoute
} from './';

const ENTITY_STATES = [...paymentMethodsRoute, ...paymentMethodsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PaymentMethodsComponent,
        PaymentMethodsDetailComponent,
        PaymentMethodsUpdateComponent,
        PaymentMethodsDeleteDialogComponent,
        PaymentMethodsDeletePopupComponent
    ],
    entryComponents: [
        PaymentMethodsComponent,
        PaymentMethodsUpdateComponent,
        PaymentMethodsDeleteDialogComponent,
        PaymentMethodsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourcePaymentMethodsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
