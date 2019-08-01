import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductModel } from 'app/shared/model/product-model.model';
import { ProductModelService } from './product-model.service';
import { ProductModelComponent } from './product-model.component';
import { ProductModelDetailComponent } from './product-model-detail.component';
import { ProductModelUpdateComponent } from './product-model-update.component';
import { ProductModelDeletePopupComponent } from './product-model-delete-dialog.component';
import { IProductModel } from 'app/shared/model/product-model.model';

@Injectable({ providedIn: 'root' })
export class ProductModelResolve implements Resolve<IProductModel> {
    constructor(private service: ProductModelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductModel> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductModel>) => response.ok),
                map((productModel: HttpResponse<ProductModel>) => productModel.body)
            );
        }
        return of(new ProductModel());
    }
}

export const productModelRoute: Routes = [
    {
        path: '',
        component: ProductModelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductModelDetailComponent,
        resolve: {
            productModel: ProductModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductModelUpdateComponent,
        resolve: {
            productModel: ProductModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductModelUpdateComponent,
        resolve: {
            productModel: ProductModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productModelPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductModelDeletePopupComponent,
        resolve: {
            productModel: ProductModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productModel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
