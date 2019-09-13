import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    PhotosComponent,
    PhotosDetailComponent,
    PhotosUpdateComponent,
    PhotosDeletePopupComponent,
    PhotosDeleteDialogComponent,
    photosRoute,
    photosPopupRoute
} from './';

const ENTITY_STATES = [...photosRoute, ...photosPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PhotosComponent, PhotosDetailComponent, PhotosUpdateComponent, PhotosDeleteDialogComponent, PhotosDeletePopupComponent],
    entryComponents: [PhotosComponent, PhotosUpdateComponent, PhotosDeleteDialogComponent, PhotosDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourcePhotosModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
