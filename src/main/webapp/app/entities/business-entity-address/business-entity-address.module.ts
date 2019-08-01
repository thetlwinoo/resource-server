import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    BusinessEntityAddressComponent,
    BusinessEntityAddressDetailComponent,
    BusinessEntityAddressUpdateComponent,
    BusinessEntityAddressDeletePopupComponent,
    BusinessEntityAddressDeleteDialogComponent,
    businessEntityAddressRoute,
    businessEntityAddressPopupRoute
} from './';

const ENTITY_STATES = [...businessEntityAddressRoute, ...businessEntityAddressPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BusinessEntityAddressComponent,
        BusinessEntityAddressDetailComponent,
        BusinessEntityAddressUpdateComponent,
        BusinessEntityAddressDeleteDialogComponent,
        BusinessEntityAddressDeletePopupComponent
    ],
    entryComponents: [
        BusinessEntityAddressComponent,
        BusinessEntityAddressUpdateComponent,
        BusinessEntityAddressDeleteDialogComponent,
        BusinessEntityAddressDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceBusinessEntityAddressModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
