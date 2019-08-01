import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    SupplierCategoriesComponent,
    SupplierCategoriesDetailComponent,
    SupplierCategoriesUpdateComponent,
    SupplierCategoriesDeletePopupComponent,
    SupplierCategoriesDeleteDialogComponent,
    supplierCategoriesRoute,
    supplierCategoriesPopupRoute
} from './';

const ENTITY_STATES = [...supplierCategoriesRoute, ...supplierCategoriesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplierCategoriesComponent,
        SupplierCategoriesDetailComponent,
        SupplierCategoriesUpdateComponent,
        SupplierCategoriesDeleteDialogComponent,
        SupplierCategoriesDeletePopupComponent
    ],
    entryComponents: [
        SupplierCategoriesComponent,
        SupplierCategoriesUpdateComponent,
        SupplierCategoriesDeleteDialogComponent,
        SupplierCategoriesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceSupplierCategoriesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
