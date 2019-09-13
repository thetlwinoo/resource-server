import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductOptionComponent,
    ProductOptionDetailComponent,
    ProductOptionUpdateComponent,
    ProductOptionDeletePopupComponent,
    ProductOptionDeleteDialogComponent,
    productOptionRoute,
    productOptionPopupRoute
} from './';

const ENTITY_STATES = [...productOptionRoute, ...productOptionPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductOptionComponent,
        ProductOptionDetailComponent,
        ProductOptionUpdateComponent,
        ProductOptionDeleteDialogComponent,
        ProductOptionDeletePopupComponent
    ],
    entryComponents: [
        ProductOptionComponent,
        ProductOptionUpdateComponent,
        ProductOptionDeleteDialogComponent,
        ProductOptionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductOptionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
