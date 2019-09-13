import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductSetComponent,
    ProductSetDetailComponent,
    ProductSetUpdateComponent,
    ProductSetDeletePopupComponent,
    ProductSetDeleteDialogComponent,
    productSetRoute,
    productSetPopupRoute
} from './';

const ENTITY_STATES = [...productSetRoute, ...productSetPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductSetComponent,
        ProductSetDetailComponent,
        ProductSetUpdateComponent,
        ProductSetDeleteDialogComponent,
        ProductSetDeletePopupComponent
    ],
    entryComponents: [ProductSetComponent, ProductSetUpdateComponent, ProductSetDeleteDialogComponent, ProductSetDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductSetModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
