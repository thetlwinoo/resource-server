import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    MaterialsComponent,
    MaterialsDetailComponent,
    MaterialsUpdateComponent,
    MaterialsDeletePopupComponent,
    MaterialsDeleteDialogComponent,
    materialsRoute,
    materialsPopupRoute
} from './';

const ENTITY_STATES = [...materialsRoute, ...materialsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MaterialsComponent,
        MaterialsDetailComponent,
        MaterialsUpdateComponent,
        MaterialsDeleteDialogComponent,
        MaterialsDeletePopupComponent
    ],
    entryComponents: [MaterialsComponent, MaterialsUpdateComponent, MaterialsDeleteDialogComponent, MaterialsDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceMaterialsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
