import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductTagsComponent,
    ProductTagsDetailComponent,
    ProductTagsUpdateComponent,
    ProductTagsDeletePopupComponent,
    ProductTagsDeleteDialogComponent,
    productTagsRoute,
    productTagsPopupRoute
} from './';

const ENTITY_STATES = [...productTagsRoute, ...productTagsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductTagsComponent,
        ProductTagsDetailComponent,
        ProductTagsUpdateComponent,
        ProductTagsDeleteDialogComponent,
        ProductTagsDeletePopupComponent
    ],
    entryComponents: [ProductTagsComponent, ProductTagsUpdateComponent, ProductTagsDeleteDialogComponent, ProductTagsDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductTagsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
