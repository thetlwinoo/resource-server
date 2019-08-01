import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    PersonEmailAddressComponent,
    PersonEmailAddressDetailComponent,
    PersonEmailAddressUpdateComponent,
    PersonEmailAddressDeletePopupComponent,
    PersonEmailAddressDeleteDialogComponent,
    personEmailAddressRoute,
    personEmailAddressPopupRoute
} from './';

const ENTITY_STATES = [...personEmailAddressRoute, ...personEmailAddressPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PersonEmailAddressComponent,
        PersonEmailAddressDetailComponent,
        PersonEmailAddressUpdateComponent,
        PersonEmailAddressDeleteDialogComponent,
        PersonEmailAddressDeletePopupComponent
    ],
    entryComponents: [
        PersonEmailAddressComponent,
        PersonEmailAddressUpdateComponent,
        PersonEmailAddressDeleteDialogComponent,
        PersonEmailAddressDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourcePersonEmailAddressModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
