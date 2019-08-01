import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    ReviewsComponent,
    ReviewsDetailComponent,
    ReviewsUpdateComponent,
    ReviewsDeletePopupComponent,
    ReviewsDeleteDialogComponent,
    reviewsRoute,
    reviewsPopupRoute
} from './';

const ENTITY_STATES = [...reviewsRoute, ...reviewsPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReviewsComponent,
        ReviewsDetailComponent,
        ReviewsUpdateComponent,
        ReviewsDeleteDialogComponent,
        ReviewsDeletePopupComponent
    ],
    entryComponents: [ReviewsComponent, ReviewsUpdateComponent, ReviewsDeleteDialogComponent, ReviewsDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourceReviewsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
