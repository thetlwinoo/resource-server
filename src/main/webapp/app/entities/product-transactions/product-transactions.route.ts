import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductTransactions } from 'app/shared/model/product-transactions.model';
import { ProductTransactionsService } from './product-transactions.service';
import { ProductTransactionsComponent } from './product-transactions.component';
import { ProductTransactionsDetailComponent } from './product-transactions-detail.component';
import { ProductTransactionsUpdateComponent } from './product-transactions-update.component';
import { ProductTransactionsDeletePopupComponent } from './product-transactions-delete-dialog.component';
import { IProductTransactions } from 'app/shared/model/product-transactions.model';

@Injectable({ providedIn: 'root' })
export class ProductTransactionsResolve implements Resolve<IProductTransactions> {
    constructor(private service: ProductTransactionsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductTransactions> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductTransactions>) => response.ok),
                map((productTransactions: HttpResponse<ProductTransactions>) => productTransactions.body)
            );
        }
        return of(new ProductTransactions());
    }
}

export const productTransactionsRoute: Routes = [
    {
        path: '',
        component: ProductTransactionsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productTransactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductTransactionsDetailComponent,
        resolve: {
            productTransactions: ProductTransactionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productTransactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductTransactionsUpdateComponent,
        resolve: {
            productTransactions: ProductTransactionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productTransactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductTransactionsUpdateComponent,
        resolve: {
            productTransactions: ProductTransactionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productTransactions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productTransactionsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductTransactionsDeletePopupComponent,
        resolve: {
            productTransactions: ProductTransactionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.productTransactions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
