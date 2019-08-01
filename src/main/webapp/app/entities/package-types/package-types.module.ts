import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ResourceSharedModule } from 'app/shared';
import {
    PackageTypesComponent,
    PackageTypesDetailComponent,
    PackageTypesUpdateComponent,
    PackageTypesDeletePopupComponent,
    PackageTypesDeleteDialogComponent,
    packageTypesRoute,
    packageTypesPopupRoute
} from './';

const ENTITY_STATES = [...packageTypesRoute, ...packageTypesPopupRoute];

@NgModule({
    imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PackageTypesComponent,
        PackageTypesDetailComponent,
        PackageTypesUpdateComponent,
        PackageTypesDeleteDialogComponent,
        PackageTypesDeletePopupComponent
    ],
    entryComponents: [
        PackageTypesComponent,
        PackageTypesUpdateComponent,
        PackageTypesDeleteDialogComponent,
        PackageTypesDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResourcePackageTypesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
