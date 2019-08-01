import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    UnitMeasureComponent,
    UnitMeasureDetailComponent,
    UnitMeasureUpdateComponent,
    UnitMeasureDeletePopupComponent,
    UnitMeasureDeleteDialogComponent,
    unitMeasureRoute,
    unitMeasurePopupRoute
} from './';

const ENTITY_STATES = [...unitMeasureRoute, ...unitMeasurePopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UnitMeasureComponent,
        UnitMeasureDetailComponent,
        UnitMeasureUpdateComponent,
        UnitMeasureDeleteDialogComponent,
        UnitMeasureDeletePopupComponent
    ],
    entryComponents: [UnitMeasureComponent, UnitMeasureUpdateComponent, UnitMeasureDeleteDialogComponent, UnitMeasureDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceUnitMeasureModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
