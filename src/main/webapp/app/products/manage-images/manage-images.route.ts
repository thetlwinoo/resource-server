import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Products } from 'app/shared/model/products.model';
import { ManageImagesService } from './manage-images.service';
import { ManageImagesComponent } from './manage-images.component';
import { IProducts } from 'app/shared/model/products.model';
import { ProductPhotoService } from 'app/entities/product-photo';
import { ImageDeletePopupComponent } from './';
import { IProductPhoto, ProductPhoto } from 'app/shared/model/product-photo.model';
import { JhiResolvePagingParams } from 'ng-jhipster';

@Injectable({ providedIn: 'root' })
export class ProductImageResolve implements Resolve<IProductPhoto> {
    constructor(private service: ProductPhotoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductPhoto> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductPhoto>) => response.ok),
                map((productPhoto: HttpResponse<ProductPhoto>) => productPhoto.body)
            );
        }
        return of(new ProductPhoto());
    }
}

export const manageImagesRoute: Routes = [
    {
        path: '',
        component: ManageImagesComponent,
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'portalApp.products.home.title'
        },
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imagePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ImageDeletePopupComponent,
        resolve: {
            productPhoto: ProductImageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'portalApp.productPhoto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
