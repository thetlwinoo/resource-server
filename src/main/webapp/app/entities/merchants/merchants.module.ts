import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    MerchantsComponent,
    MerchantsDetailComponent,
    MerchantsUpdateComponent,
    MerchantsDeletePopupComponent,
    MerchantsDeleteDialogComponent,
    merchantsRoute,
    merchantsPopupRoute
} from './';

const ENTITY_STATES = [...merchantsRoute, ...merchantsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MerchantsComponent,
        MerchantsDetailComponent,
        MerchantsUpdateComponent,
        MerchantsDeleteDialogComponent,
        MerchantsDeletePopupComponent
    ],
    entryComponents: [MerchantsComponent, MerchantsUpdateComponent, MerchantsDeleteDialogComponent, MerchantsDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceMerchantsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
