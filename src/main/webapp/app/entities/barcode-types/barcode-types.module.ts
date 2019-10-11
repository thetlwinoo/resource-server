import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    BarcodeTypesComponent,
    BarcodeTypesDetailComponent,
    BarcodeTypesUpdateComponent,
    BarcodeTypesDeletePopupComponent,
    BarcodeTypesDeleteDialogComponent,
    barcodeTypesRoute,
    barcodeTypesPopupRoute
} from './';

const ENTITY_STATES = [...barcodeTypesRoute, ...barcodeTypesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BarcodeTypesComponent,
        BarcodeTypesDetailComponent,
        BarcodeTypesUpdateComponent,
        BarcodeTypesDeleteDialogComponent,
        BarcodeTypesDeletePopupComponent
    ],
    entryComponents: [
        BarcodeTypesComponent,
        BarcodeTypesUpdateComponent,
        BarcodeTypesDeleteDialogComponent,
        BarcodeTypesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceBarcodeTypesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
