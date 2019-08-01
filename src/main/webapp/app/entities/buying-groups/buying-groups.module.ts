import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    BuyingGroupsComponent,
    BuyingGroupsDetailComponent,
    BuyingGroupsUpdateComponent,
    BuyingGroupsDeletePopupComponent,
    BuyingGroupsDeleteDialogComponent,
    buyingGroupsRoute,
    buyingGroupsPopupRoute
} from './';

const ENTITY_STATES = [...buyingGroupsRoute, ...buyingGroupsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BuyingGroupsComponent,
        BuyingGroupsDetailComponent,
        BuyingGroupsUpdateComponent,
        BuyingGroupsDeleteDialogComponent,
        BuyingGroupsDeletePopupComponent
    ],
    entryComponents: [
        BuyingGroupsComponent,
        BuyingGroupsUpdateComponent,
        BuyingGroupsDeleteDialogComponent,
        BuyingGroupsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceBuyingGroupsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
