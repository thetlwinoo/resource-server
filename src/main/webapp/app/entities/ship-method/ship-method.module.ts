import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ShipMethodComponent,
    ShipMethodDetailComponent,
    ShipMethodUpdateComponent,
    ShipMethodDeletePopupComponent,
    ShipMethodDeleteDialogComponent,
    shipMethodRoute,
    shipMethodPopupRoute
} from './';

const ENTITY_STATES = [...shipMethodRoute, ...shipMethodPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ShipMethodComponent,
        ShipMethodDetailComponent,
        ShipMethodUpdateComponent,
        ShipMethodDeleteDialogComponent,
        ShipMethodDeletePopupComponent
    ],
    entryComponents: [ShipMethodComponent, ShipMethodUpdateComponent, ShipMethodDeleteDialogComponent, ShipMethodDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceShipMethodModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
