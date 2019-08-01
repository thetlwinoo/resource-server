import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Products } from 'app/shared/model/products.model';
import { ManageProductsService } from './manage-products.service';
import { ManageProductsComponent } from './manage-products.component';
import { ManageProductsDetailComponent } from './manage-products-detail.component';
import { ManageProductsBatchComponent } from './manage-products-batch.component';
import { ManageProductsUpdateComponent } from './manage-products-update.component';
import { ProductsDeletePopupComponent } from './manage-products-delete-dialog.component';
import { IProducts } from 'app/shared/model/products.model';

@Injectable({ providedIn: 'root' })
export class ProductsResolve implements Resolve<IProducts> {
    constructor(private service: ManageProductsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProducts> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Products>) => response.ok),
                map((products: HttpResponse<Products>) => products.body)
            );
        }
        return of(new Products());
    }
}

export const manageProductsRoute: Routes = [
    {
        path: '',
        component: ManageProductsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portalApp.products.home.title'
        },
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ManageProductsDetailComponent,
        resolve: {
            products: ProductsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portalApp.products.home.title',
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
                    },
                    routerLink: '/manage-products'
                },
                {
                    label: 'Details',
                    command: event => {
                        this.msgs.length = 0;
                        this.msgs.push({ severity: 'info', summary: event.item.label });
                    }
                }
            ]
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ManageProductsUpdateComponent,
        resolve: {
            products: ProductsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portalApp.products.home.title',
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
                    },
                    routerLink: '/manage-products'
                },
                {
                    label: 'Create New Product',
                    command: event => {
                        this.msgs.length = 0;
                        this.msgs.push({ severity: 'info', summary: event.item.label });
                    }
                }
            ]
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'batch',
        component: ManageProductsBatchComponent,
        resolve: {
            products: ProductsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portalApp.products.home.title',
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
                    },
                    routerLink: '/manage-products'
                },
                {
                    label: 'Batch Upload',
                    command: event => {
                        this.msgs.length = 0;
                        this.msgs.push({ severity: 'info', summary: event.item.label });
                    }
                }
            ]
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ManageProductsUpdateComponent,
        resolve: {
            products: ProductsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portalApp.products.home.title',
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
                    },
                    routerLink: '/manage-products'
                },
                {
                    label: 'Edit Product',
                    command: event => {
                        this.msgs.length = 0;
                        this.msgs.push({ severity: 'info', summary: event.item.label });
                    }
                }
            ]
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductsDeletePopupComponent,
        resolve: {
            products: ProductsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portalApp.products.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
