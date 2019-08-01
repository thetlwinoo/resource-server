import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    AddressTypesComponent,
    AddressTypesDetailComponent,
    AddressTypesUpdateComponent,
    AddressTypesDeletePopupComponent,
    AddressTypesDeleteDialogComponent,
    addressTypesRoute,
    addressTypesPopupRoute
} from './';

const ENTITY_STATES = [...addressTypesRoute, ...addressTypesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AddressTypesComponent,
        AddressTypesDetailComponent,
        AddressTypesUpdateComponent,
        AddressTypesDeleteDialogComponent,
        AddressTypesDeletePopupComponent
    ],
    entryComponents: [
        AddressTypesComponent,
        AddressTypesUpdateComponent,
        AddressTypesDeleteDialogComponent,
        AddressTypesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceAddressTypesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
