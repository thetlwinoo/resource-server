import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductInventory } from 'app/shared/model/product-inventory.model';
import { ProductInventoryService } from './product-inventory.service';
import { ProductInventoryComponent } from './product-inventory.component';
import { ProductInventoryDetailComponent } from './product-inventory-detail.component';
import { ProductInventoryUpdateComponent } from './product-inventory-update.component';
import { ProductInventoryDeletePopupComponent } from './product-inventory-delete-dialog.component';
import { IProductInventory } from 'app/shared/model/product-inventory.model';

@Injectable({ providedIn: 'root' })
export class ProductInventoryResolve implements Resolve<IProductInventory> {
    constructor(private service: ProductInventoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductInventory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductInventory>) => response.ok),
                map((productInventory: HttpResponse<ProductInventory>) => productInventory.body)
            );
        }
        return of(new ProductInventory());
    }
}

export const productInventoryRoute: Routes = [
    {
        path: '',
        component: ProductInventoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productInventory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductInventoryDetailComponent,
        resolve: {
            productInventory: ProductInventoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productInventory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductInventoryUpdateComponent,
        resolve: {
            productInventory: ProductInventoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productInventory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductInventoryUpdateComponent,
        resolve: {
            productInventory: ProductInventoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productInventory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productInventoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductInventoryDeletePopupComponent,
        resolve: {
            productInventory: ProductInventoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productInventory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
