import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductSubCategory } from 'app/shared/model/product-sub-category.model';
import { ProductSubCategoryService } from './product-sub-category.service';
import { ProductSubCategoryComponent } from './product-sub-category.component';
import { ProductSubCategoryDetailComponent } from './product-sub-category-detail.component';
import { ProductSubCategoryUpdateComponent } from './product-sub-category-update.component';
import { ProductSubCategoryDeletePopupComponent } from './product-sub-category-delete-dialog.component';
import { IProductSubCategory } from 'app/shared/model/product-sub-category.model';

@Injectable({ providedIn: 'root' })
export class ProductSubCategoryResolve implements Resolve<IProductSubCategory> {
    constructor(private service: ProductSubCategoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductSubCategory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductSubCategory>) => response.ok),
                map((productSubCategory: HttpResponse<ProductSubCategory>) => productSubCategory.body)
            );
        }
        return of(new ProductSubCategory());
    }
}

export const productSubCategoryRoute: Routes = [
    {
        path: '',
        component: ProductSubCategoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productSubCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductSubCategoryDetailComponent,
        resolve: {
            productSubCategory: ProductSubCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productSubCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductSubCategoryUpdateComponent,
        resolve: {
            productSubCategory: ProductSubCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productSubCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductSubCategoryUpdateComponent,
        resolve: {
            productSubCategory: ProductSubCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productSubCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productSubCategoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductSubCategoryDeletePopupComponent,
        resolve: {
            productSubCategory: ProductSubCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productSubCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
