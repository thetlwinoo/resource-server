import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    LocationsComponent,
    LocationsDetailComponent,
    LocationsUpdateComponent,
    LocationsDeletePopupComponent,
    LocationsDeleteDialogComponent,
    locationsRoute,
    locationsPopupRoute
} from './';

const ENTITY_STATES = [...locationsRoute, ...locationsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LocationsComponent,
        LocationsDetailComponent,
        LocationsUpdateComponent,
        LocationsDeleteDialogComponent,
        LocationsDeletePopupComponent
    ],
    entryComponents: [LocationsComponent, LocationsUpdateComponent, LocationsDeleteDialogComponent, LocationsDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceLocationsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
