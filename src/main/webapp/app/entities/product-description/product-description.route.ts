import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductDescription } from 'app/shared/model/product-description.model';
import { ProductDescriptionService } from './product-description.service';
import { ProductDescriptionComponent } from './product-description.component';
import { ProductDescriptionDetailComponent } from './product-description-detail.component';
import { ProductDescriptionUpdateComponent } from './product-description-update.component';
import { ProductDescriptionDeletePopupComponent } from './product-description-delete-dialog.component';
import { IProductDescription } from 'app/shared/model/product-description.model';

@Injectable({ providedIn: 'root' })
export class ProductDescriptionResolve implements Resolve<IProductDescription> {
    constructor(private service: ProductDescriptionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductDescription> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductDescription>) => response.ok),
                map((productDescription: HttpResponse<ProductDescription>) => productDescription.body)
            );
        }
        return of(new ProductDescription());
    }
}

export const productDescriptionRoute: Routes = [
    {
        path: '',
        component: ProductDescriptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productDescription.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductDescriptionDetailComponent,
        resolve: {
            productDescription: ProductDescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productDescription.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductDescriptionUpdateComponent,
        resolve: {
            productDescription: ProductDescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productDescription.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductDescriptionUpdateComponent,
        resolve: {
            productDescription: ProductDescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productDescription.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productDescriptionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductDescriptionDeletePopupComponent,
        resolve: {
            productDescription: ProductDescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productDescription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
