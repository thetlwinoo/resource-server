import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'manage-products',
                loadChildren: './manage-products/manage-products.module#PortalProductsModule'
            },
            {
                path: 'manage-images',
                loadChildren: './manage-images/manage-images.module#PortalImagesModule'
            }
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortalProductModule {}
