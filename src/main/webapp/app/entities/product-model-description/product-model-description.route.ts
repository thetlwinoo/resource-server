import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductModelDescription } from 'app/shared/model/product-model-description.model';
import { ProductModelDescriptionService } from './product-model-description.service';
import { ProductModelDescriptionComponent } from './product-model-description.component';
import { ProductModelDescriptionDetailComponent } from './product-model-description-detail.component';
import { ProductModelDescriptionUpdateComponent } from './product-model-description-update.component';
import { ProductModelDescriptionDeletePopupComponent } from './product-model-description-delete-dialog.component';
import { IProductModelDescription } from 'app/shared/model/product-model-description.model';

@Injectable({ providedIn: 'root' })
export class ProductModelDescriptionResolve implements Resolve<IProductModelDescription> {
    constructor(private service: ProductModelDescriptionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductModelDescription> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductModelDescription>) => response.ok),
                map((productModelDescription: HttpResponse<ProductModelDescription>) => productModelDescription.body)
            );
        }
        return of(new ProductModelDescription());
    }
}

export const productModelDescriptionRoute: Routes = [
    {
        path: '',
        component: ProductModelDescriptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productModelDescription.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductModelDescriptionDetailComponent,
        resolve: {
            productModelDescription: ProductModelDescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productModelDescription.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductModelDescriptionUpdateComponent,
        resolve: {
            productModelDescription: ProductModelDescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productModelDescription.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductModelDescriptionUpdateComponent,
        resolve: {
            productModelDescription: ProductModelDescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productModelDescription.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productModelDescriptionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductModelDescriptionDeletePopupComponent,
        resolve: {
            productModelDescription: ProductModelDescriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productModelDescription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
