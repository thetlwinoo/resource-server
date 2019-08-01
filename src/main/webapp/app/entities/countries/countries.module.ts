import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    CountriesComponent,
    CountriesDetailComponent,
    CountriesUpdateComponent,
    CountriesDeletePopupComponent,
    CountriesDeleteDialogComponent,
    countriesRoute,
    countriesPopupRoute
} from './';

const ENTITY_STATES = [...countriesRoute, ...countriesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CountriesComponent,
        CountriesDetailComponent,
        CountriesUpdateComponent,
        CountriesDeleteDialogComponent,
        CountriesDeletePopupComponent
    ],
    entryComponents: [CountriesComponent, CountriesUpdateComponent, CountriesDeleteDialogComponent, CountriesDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceCountriesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
