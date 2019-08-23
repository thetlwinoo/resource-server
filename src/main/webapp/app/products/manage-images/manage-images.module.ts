import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { ResourceSharedModule } from 'app/shared';
import {
    ImageDeletePopupComponent,
    ImageDeleteDialogComponent,
    ManageImagesComponent,
    ImageUploaderComponent,
    ImageSelectorComponent,
    manageImagesRoute,
    imagePopupRoute
} from './';

import { GrowlModule } from 'primeng/primeng';
import { ButtonModule } from 'primeng/primeng';
import { FileUploadModule } from 'primeng/components/fileupload/fileupload';
import { CheckboxModule } from 'primeng/components/checkbox/checkbox';
import { WizardModule } from 'primeng-extensions/components/wizard/wizard.js';
import { TabMenuModule } from 'primeng/tabmenu';
import { TableModule } from 'primeng/table';
import { DataViewModule } from 'primeng/dataview';
import { ProgressBarModule } from 'primeng/progressbar';
import { InputTextModule } from 'primeng/inputtext';
import { jhiCloudinaryModule } from 'app/shared/components';
import { ResourcePipesModule } from 'app/shared/pipes/pipes.module';

const ENTITY_STATES = [...manageImagesRoute, ...imagePopupRoute];

@NgModule({
    imports: [
        ResourceSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        FileUploadModule,
        CheckboxModule,
        GrowlModule,
        ButtonModule,
        WizardModule,
        TabMenuModule,
        TableModule,
        DataViewModule,
        ProgressBarModule,
        InputTextModule,
        jhiCloudinaryModule,
        ResourcePipesModule
    ],
    declarations: [
        ImageDeletePopupComponent,
        ImageDeleteDialogComponent,
        ManageImagesComponent,
        ImageUploaderComponent,
        ImageSelectorComponent
    ],
    entryComponents: [ImageDeletePopupComponent, ImageDeleteDialogComponent, ManageImagesComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortalImagesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
