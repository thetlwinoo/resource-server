import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    VehicleTemperaturesComponent,
    VehicleTemperaturesDetailComponent,
    VehicleTemperaturesUpdateComponent,
    VehicleTemperaturesDeletePopupComponent,
    VehicleTemperaturesDeleteDialogComponent,
    vehicleTemperaturesRoute,
    vehicleTemperaturesPopupRoute
} from './';

const ENTITY_STATES = [...vehicleTemperaturesRoute, ...vehicleTemperaturesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VehicleTemperaturesComponent,
        VehicleTemperaturesDetailComponent,
        VehicleTemperaturesUpdateComponent,
        VehicleTemperaturesDeleteDialogComponent,
        VehicleTemperaturesDeletePopupComponent
    ],
    entryComponents: [
        VehicleTemperaturesComponent,
        VehicleTemperaturesUpdateComponent,
        VehicleTemperaturesDeleteDialogComponent,
        VehicleTemperaturesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceVehicleTemperaturesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
