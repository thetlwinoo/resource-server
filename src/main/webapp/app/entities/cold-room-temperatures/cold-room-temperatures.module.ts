import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ColdRoomTemperaturesComponent,
    ColdRoomTemperaturesDetailComponent,
    ColdRoomTemperaturesUpdateComponent,
    ColdRoomTemperaturesDeletePopupComponent,
    ColdRoomTemperaturesDeleteDialogComponent,
    coldRoomTemperaturesRoute,
    coldRoomTemperaturesPopupRoute
} from './';

const ENTITY_STATES = [...coldRoomTemperaturesRoute, ...coldRoomTemperaturesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ColdRoomTemperaturesComponent,
        ColdRoomTemperaturesDetailComponent,
        ColdRoomTemperaturesUpdateComponent,
        ColdRoomTemperaturesDeleteDialogComponent,
        ColdRoomTemperaturesDeletePopupComponent
    ],
    entryComponents: [
        ColdRoomTemperaturesComponent,
        ColdRoomTemperaturesUpdateComponent,
        ColdRoomTemperaturesDeleteDialogComponent,
        ColdRoomTemperaturesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceColdRoomTemperaturesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
