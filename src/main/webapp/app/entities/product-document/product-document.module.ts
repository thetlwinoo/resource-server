import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductDocumentComponent,
    ProductDocumentDetailComponent,
    ProductDocumentUpdateComponent,
    ProductDocumentDeletePopupComponent,
    ProductDocumentDeleteDialogComponent,
    productDocumentRoute,
    productDocumentPopupRoute
} from './';

const ENTITY_STATES = [...productDocumentRoute, ...productDocumentPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductDocumentComponent,
        ProductDocumentDetailComponent,
        ProductDocumentUpdateComponent,
        ProductDocumentDeleteDialogComponent,
        ProductDocumentDeletePopupComponent
    ],
    entryComponents: [
        ProductDocumentComponent,
        ProductDocumentUpdateComponent,
        ProductDocumentDeleteDialogComponent,
        ProductDocumentDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductDocumentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
