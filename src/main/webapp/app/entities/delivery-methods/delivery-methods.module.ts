import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    DeliveryMethodsComponent,
    DeliveryMethodsDetailComponent,
    DeliveryMethodsUpdateComponent,
    DeliveryMethodsDeletePopupComponent,
    DeliveryMethodsDeleteDialogComponent,
    deliveryMethodsRoute,
    deliveryMethodsPopupRoute
} from './';

const ENTITY_STATES = [...deliveryMethodsRoute, ...deliveryMethodsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DeliveryMethodsComponent,
        DeliveryMethodsDetailComponent,
        DeliveryMethodsUpdateComponent,
        DeliveryMethodsDeleteDialogComponent,
        DeliveryMethodsDeletePopupComponent
    ],
    entryComponents: [
        DeliveryMethodsComponent,
        DeliveryMethodsUpdateComponent,
        DeliveryMethodsDeleteDialogComponent,
        DeliveryMethodsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceDeliveryMethodsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
