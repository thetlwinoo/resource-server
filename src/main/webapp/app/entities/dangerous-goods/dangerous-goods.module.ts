import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    DangerousGoodsComponent,
    DangerousGoodsDetailComponent,
    DangerousGoodsUpdateComponent,
    DangerousGoodsDeletePopupComponent,
    DangerousGoodsDeleteDialogComponent,
    dangerousGoodsRoute,
    dangerousGoodsPopupRoute
} from './';

const ENTITY_STATES = [...dangerousGoodsRoute, ...dangerousGoodsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DangerousGoodsComponent,
        DangerousGoodsDetailComponent,
        DangerousGoodsUpdateComponent,
        DangerousGoodsDeleteDialogComponent,
        DangerousGoodsDeletePopupComponent
    ],
    entryComponents: [
        DangerousGoodsComponent,
        DangerousGoodsUpdateComponent,
        DangerousGoodsDeleteDialogComponent,
        DangerousGoodsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceDangerousGoodsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
