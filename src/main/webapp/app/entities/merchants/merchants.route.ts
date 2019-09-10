import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Merchants } from 'app/shared/model/merchants.model';
import { MerchantsService } from './merchants.service';
import { MerchantsComponent } from './merchants.component';
import { MerchantsDetailComponent } from './merchants-detail.component';
import { MerchantsUpdateComponent } from './merchants-update.component';
import { MerchantsDeletePopupComponent } from './merchants-delete-dialog.component';
import { IMerchants } from 'app/shared/model/merchants.model';

@Injectable({ providedIn: 'root' })
export class MerchantsResolve implements Resolve<IMerchants> {
    constructor(private service: MerchantsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMerchants> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Merchants>) => response.ok),
                map((merchants: HttpResponse<Merchants>) => merchants.body)
            );
        }
        return of(new Merchants());
    }
}

export const merchantsRoute: Routes = [
    {
        path: '',
        component: MerchantsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.merchants.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MerchantsDetailComponent,
        resolve: {
            merchants: MerchantsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.merchants.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MerchantsUpdateComponent,
        resolve: {
            merchants: MerchantsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.merchants.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MerchantsUpdateComponent,
        resolve: {
            merchants: MerchantsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.merchants.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const merchantsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MerchantsDeletePopupComponent,
        resolve: {
            merchants: MerchantsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.merchants.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
