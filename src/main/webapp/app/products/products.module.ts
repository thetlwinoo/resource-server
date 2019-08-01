import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'manage-products',
                loadChildren: './manage-products/manage-products.module#PortalProductsModule',
                data: {
                    breadcrumb: [
                        {
                            label: 'Products',
                            command: event => {
                                this.msgs.length = 0;
                                this.msgs.push({ severity: 'info', summary: event.item.label });
                            }
                        },
                        {
                            label: 'Manage Products',
                            command: event => {
                                this.msgs.length = 0;
                                this.msgs.push({ severity: 'info', summary: event.item.label });
                            }
                        }
                    ]
                }
            },
            {
                path: 'manage-images',
                loadChildren: './manage-images/manage-images.module#PortalImagesModule',
                data: {
                    breadcrumb: [
                        {
                            label: 'Products',
                            command: event => {
                                this.msgs.length = 0;
                                this.msgs.push({ severity: 'info', summary: event.item.label });
                            }
                        },
                        {
                            label: 'Manage Images',
                            command: event => {
                                this.msgs.length = 0;
                                this.msgs.push({ severity: 'info', summary: event.item.label });
                            }
                        }
                    ]
                }
            }
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PortalProductModule {}
