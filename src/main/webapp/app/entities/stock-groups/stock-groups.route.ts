import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockGroups } from 'app/shared/model/stock-groups.model';
import { StockGroupsService } from './stock-groups.service';
import { StockGroupsComponent } from './stock-groups.component';
import { StockGroupsDetailComponent } from './stock-groups-detail.component';
import { StockGroupsUpdateComponent } from './stock-groups-update.component';
import { StockGroupsDeletePopupComponent } from './stock-groups-delete-dialog.component';
import { IStockGroups } from 'app/shared/model/stock-groups.model';

@Injectable({ providedIn: 'root' })
export class StockGroupsResolve implements Resolve<IStockGroups> {
    constructor(private service: StockGroupsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockGroups> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StockGroups>) => response.ok),
                map((stockGroups: HttpResponse<StockGroups>) => stockGroups.body)
            );
        }
        return of(new StockGroups());
    }
}

export const stockGroupsRoute: Routes = [
    {
        path: '',
        component: StockGroupsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.stockGroups.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockGroupsDetailComponent,
        resolve: {
            stockGroups: StockGroupsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.stockGroups.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockGroupsUpdateComponent,
        resolve: {
            stockGroups: StockGroupsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.stockGroups.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockGroupsUpdateComponent,
        resolve: {
            stockGroups: StockGroupsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.stockGroups.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockGroupsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StockGroupsDeletePopupComponent,
        resolve: {
            stockGroups: StockGroupsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.stockGroups.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
