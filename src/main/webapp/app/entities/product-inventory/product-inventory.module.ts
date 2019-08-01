import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ProductInventoryComponent,
    ProductInventoryDetailComponent,
    ProductInventoryUpdateComponent,
    ProductInventoryDeletePopupComponent,
    ProductInventoryDeleteDialogComponent,
    productInventoryRoute,
    productInventoryPopupRoute
} from './';

const ENTITY_STATES = [...productInventoryRoute, ...productInventoryPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductInventoryComponent,
        ProductInventoryDetailComponent,
        ProductInventoryUpdateComponent,
        ProductInventoryDeleteDialogComponent,
        ProductInventoryDeletePopupComponent
    ],
    entryComponents: [
        ProductInventoryComponent,
        ProductInventoryUpdateComponent,
        ProductInventoryDeleteDialogComponent,
        ProductInventoryDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceProductInventoryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
