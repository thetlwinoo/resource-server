import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    AddressesComponent,
    AddressesDetailComponent,
    AddressesUpdateComponent,
    AddressesDeletePopupComponent,
    AddressesDeleteDialogComponent,
    addressesRoute,
    addressesPopupRoute
} from './';

const ENTITY_STATES = [...addressesRoute, ...addressesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AddressesComponent,
        AddressesDetailComponent,
        AddressesUpdateComponent,
        AddressesDeleteDialogComponent,
        AddressesDeletePopupComponent
    ],
    entryComponents: [AddressesComponent, AddressesUpdateComponent, AddressesDeleteDialogComponent, AddressesDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceAddressesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
