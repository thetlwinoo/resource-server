import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    WarrantyTypesComponent,
    WarrantyTypesDetailComponent,
    WarrantyTypesUpdateComponent,
    WarrantyTypesDeletePopupComponent,
    WarrantyTypesDeleteDialogComponent,
    warrantyTypesRoute,
    warrantyTypesPopupRoute
} from './';

const ENTITY_STATES = [...warrantyTypesRoute, ...warrantyTypesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WarrantyTypesComponent,
        WarrantyTypesDetailComponent,
        WarrantyTypesUpdateComponent,
        WarrantyTypesDeleteDialogComponent,
        WarrantyTypesDeletePopupComponent
    ],
    entryComponents: [
        WarrantyTypesComponent,
        WarrantyTypesUpdateComponent,
        WarrantyTypesDeleteDialogComponent,
        WarrantyTypesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceWarrantyTypesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
