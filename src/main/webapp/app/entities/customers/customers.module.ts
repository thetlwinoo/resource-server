import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    CustomersComponent,
    CustomersDetailComponent,
    CustomersUpdateComponent,
    CustomersDeletePopupComponent,
    CustomersDeleteDialogComponent,
    customersRoute,
    customersPopupRoute
} from './';

const ENTITY_STATES = [...customersRoute, ...customersPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CustomersComponent,
        CustomersDetailComponent,
        CustomersUpdateComponent,
        CustomersDeleteDialogComponent,
        CustomersDeletePopupComponent
    ],
    entryComponents: [CustomersComponent, CustomersUpdateComponent, CustomersDeleteDialogComponent, CustomersDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceCustomersModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
