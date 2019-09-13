import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductSetDetailsComponent,
    ProductSetDetailsDetailComponent,
    ProductSetDetailsUpdateComponent,
    ProductSetDetailsDeletePopupComponent,
    ProductSetDetailsDeleteDialogComponent,
    productSetDetailsRoute,
    productSetDetailsPopupRoute
} from './';

const ENTITY_STATES = [...productSetDetailsRoute, ...productSetDetailsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductSetDetailsComponent,
        ProductSetDetailsDetailComponent,
        ProductSetDetailsUpdateComponent,
        ProductSetDetailsDeleteDialogComponent,
        ProductSetDetailsDeletePopupComponent
    ],
    entryComponents: [
        ProductSetDetailsComponent,
        ProductSetDetailsUpdateComponent,
        ProductSetDetailsDeleteDialogComponent,
        ProductSetDetailsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductSetDetailsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
