import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    InvoicesComponent,
    InvoicesDetailComponent,
    InvoicesUpdateComponent,
    InvoicesDeletePopupComponent,
    InvoicesDeleteDialogComponent,
    invoicesRoute,
    invoicesPopupRoute
} from './';

const ENTITY_STATES = [...invoicesRoute, ...invoicesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvoicesComponent,
        InvoicesDetailComponent,
        InvoicesUpdateComponent,
        InvoicesDeleteDialogComponent,
        InvoicesDeletePopupComponent
    ],
    entryComponents: [InvoicesComponent, InvoicesUpdateComponent, InvoicesDeleteDialogComponent, InvoicesDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceInvoicesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
