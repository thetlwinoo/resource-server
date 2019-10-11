import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    UploadActionTypesComponent,
    UploadActionTypesDetailComponent,
    UploadActionTypesUpdateComponent,
    UploadActionTypesDeletePopupComponent,
    UploadActionTypesDeleteDialogComponent,
    uploadActionTypesRoute,
    uploadActionTypesPopupRoute
} from './';

const ENTITY_STATES = [...uploadActionTypesRoute, ...uploadActionTypesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UploadActionTypesComponent,
        UploadActionTypesDetailComponent,
        UploadActionTypesUpdateComponent,
        UploadActionTypesDeleteDialogComponent,
        UploadActionTypesDeletePopupComponent
    ],
    entryComponents: [
        UploadActionTypesComponent,
        UploadActionTypesUpdateComponent,
        UploadActionTypesDeleteDialogComponent,
        UploadActionTypesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceUploadActionTypesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
