import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ManageProductsComponent,
    ManageProductsDetailComponent,
    ManageProductsBatchComponent,
    ManageProductsUpdateComponent,
    ProductsDeletePopupComponent,
    ManageProductsDeleteDialogComponent,
    manageProductsRoute,
    productsPopupRoute
} from './';

import { GrowlModule } from 'primeng/primeng';
import { ButtonModule } from 'primeng/primeng';
import { FileUploadModule } from 'primeng/components/fileupload/fileupload';
import { CheckboxModule } from 'primeng/components/checkbox/checkbox';
import { WizardModule } from 'primeng-extensions/components/wizard/wizard.js';

const ENTITY_STATES = [...manageProductsRoute, ...productsPopupRoute];

@NgModule({
    imports: [
        ResourceSharedModule,
        FileUploadModule,
        CheckboxModule,
        GrowlModule,
        ButtonModule,
        WizardModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ManageProductsComponent,
        ManageProductsDetailComponent,
        ManageProductsBatchComponent,
        ManageProductsUpdateComponent,
        ManageProductsDeleteDialogComponent,
        ProductsDeletePopupComponent
    ],
    entryComponents: [
        ManageProductsComponent,
        ManageProductsUpdateComponent,
        ManageProductsDeleteDialogComponent,
        ProductsDeletePopupComponent,
        ManageProductsBatchComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortalProductsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
