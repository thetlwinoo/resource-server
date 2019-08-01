import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    CompareProductsComponent,
    CompareProductsDetailComponent,
    CompareProductsUpdateComponent,
    CompareProductsDeletePopupComponent,
    CompareProductsDeleteDialogComponent,
    compareProductsRoute,
    compareProductsPopupRoute
} from './';

const ENTITY_STATES = [...compareProductsRoute, ...compareProductsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompareProductsComponent,
        CompareProductsDetailComponent,
        CompareProductsUpdateComponent,
        CompareProductsDeleteDialogComponent,
        CompareProductsDeletePopupComponent
    ],
    entryComponents: [
        CompareProductsComponent,
        CompareProductsUpdateComponent,
        CompareProductsDeleteDialogComponent,
        CompareProductsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceCompareProductsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
