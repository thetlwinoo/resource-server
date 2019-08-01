import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    CustomerCategoriesComponent,
    CustomerCategoriesDetailComponent,
    CustomerCategoriesUpdateComponent,
    CustomerCategoriesDeletePopupComponent,
    CustomerCategoriesDeleteDialogComponent,
    customerCategoriesRoute,
    customerCategoriesPopupRoute
} from './';

const ENTITY_STATES = [...customerCategoriesRoute, ...customerCategoriesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CustomerCategoriesComponent,
        CustomerCategoriesDetailComponent,
        CustomerCategoriesUpdateComponent,
        CustomerCategoriesDeleteDialogComponent,
        CustomerCategoriesDeletePopupComponent
    ],
    entryComponents: [
        CustomerCategoriesComponent,
        CustomerCategoriesUpdateComponent,
        CustomerCategoriesDeleteDialogComponent,
        CustomerCategoriesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceCustomerCategoriesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
