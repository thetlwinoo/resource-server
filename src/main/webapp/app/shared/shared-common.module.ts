import { NgModule } from '@angular/core';

import { ResourceSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';
import { ProductExtendService } from 'app/shared/services';
import { ProductPhotoExtendService } from 'app/shared/services';

@NgModule({
    imports: [ResourceSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ResourceSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
    providers: [ProductExtendService, ProductPhotoExtendService]
})
export class ResourceSharedCommonModule {}
