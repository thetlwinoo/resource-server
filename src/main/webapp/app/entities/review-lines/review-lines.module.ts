import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ReviewLinesComponent,
    ReviewLinesDetailComponent,
    ReviewLinesUpdateComponent,
    ReviewLinesDeletePopupComponent,
    ReviewLinesDeleteDialogComponent,
    reviewLinesRoute,
    reviewLinesPopupRoute
} from './';

const ENTITY_STATES = [...reviewLinesRoute, ...reviewLinesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReviewLinesComponent,
        ReviewLinesDetailComponent,
        ReviewLinesUpdateComponent,
        ReviewLinesDeleteDialogComponent,
        ReviewLinesDeletePopupComponent
    ],
    entryComponents: [ReviewLinesComponent, ReviewLinesUpdateComponent, ReviewLinesDeleteDialogComponent, ReviewLinesDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceReviewLinesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
