import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductPhotoComponent,
    ProductPhotoDetailComponent,
    ProductPhotoUpdateComponent,
    ProductPhotoDeletePopupComponent,
    ProductPhotoDeleteDialogComponent,
    productPhotoRoute,
    productPhotoPopupRoute
} from './';

const ENTITY_STATES = [...productPhotoRoute, ...productPhotoPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductPhotoComponent,
        ProductPhotoDetailComponent,
        ProductPhotoUpdateComponent,
        ProductPhotoDeleteDialogComponent,
        ProductPhotoDeletePopupComponent
    ],
    entryComponents: [
        ProductPhotoComponent,
        ProductPhotoUpdateComponent,
        ProductPhotoDeleteDialogComponent,
        ProductPhotoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductPhotoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
