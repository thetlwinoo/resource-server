import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockItemStockGroups } from 'app/shared/model/stock-item-stock-groups.model';
import { StockItemStockGroupsService } from './stock-item-stock-groups.service';
import { StockItemStockGroupsComponent } from './stock-item-stock-groups.component';
import { StockItemStockGroupsDetailComponent } from './stock-item-stock-groups-detail.component';
import { StockItemStockGroupsUpdateComponent } from './stock-item-stock-groups-update.component';
import { StockItemStockGroupsDeletePopupComponent } from './stock-item-stock-groups-delete-dialog.component';
import { IStockItemStockGroups } from 'app/shared/model/stock-item-stock-groups.model';

@Injectable({ providedIn: 'root' })
export class StockItemStockGroupsResolve implements Resolve<IStockItemStockGroups> {
    constructor(private service: StockItemStockGroupsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockItemStockGroups> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StockItemStockGroups>) => response.ok),
                map((stockItemStockGroups: HttpResponse<StockItemStockGroups>) => stockItemStockGroups.body)
            );
        }
        return of(new StockItemStockGroups());
    }
}

export const stockItemStockGroupsRoute: Routes = [
    {
        path: '',
        component: StockItemStockGroupsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.stockItemStockGroups.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockItemStockGroupsDetailComponent,
        resolve: {
            stockItemStockGroups: StockItemStockGroupsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.stockItemStockGroups.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockItemStockGroupsUpdateComponent,
        resolve: {
            stockItemStockGroups: StockItemStockGroupsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.stockItemStockGroups.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockItemStockGroupsUpdateComponent,
        resolve: {
            stockItemStockGroups: StockItemStockGroupsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.stockItemStockGroups.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockItemStockGroupsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StockItemStockGroupsDeletePopupComponent,
        resolve: {
            stockItemStockGroups: StockItemStockGroupsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.stockItemStockGroups.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
