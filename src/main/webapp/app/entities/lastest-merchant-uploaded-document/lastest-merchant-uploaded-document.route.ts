import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LastestMerchantUploadedDocument } from 'app/shared/model/lastest-merchant-uploaded-document.model';
import { LastestMerchantUploadedDocumentService } from './lastest-merchant-uploaded-document.service';
import { LastestMerchantUploadedDocumentComponent } from './lastest-merchant-uploaded-document.component';
import { LastestMerchantUploadedDocumentDetailComponent } from './lastest-merchant-uploaded-document-detail.component';
import { LastestMerchantUploadedDocumentUpdateComponent } from './lastest-merchant-uploaded-document-update.component';
import { LastestMerchantUploadedDocumentDeletePopupComponent } from './lastest-merchant-uploaded-document-delete-dialog.component';
import { ILastestMerchantUploadedDocument } from 'app/shared/model/lastest-merchant-uploaded-document.model';

@Injectable({ providedIn: 'root' })
export class LastestMerchantUploadedDocumentResolve implements Resolve<ILastestMerchantUploadedDocument> {
    constructor(private service: LastestMerchantUploadedDocumentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILastestMerchantUploadedDocument> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LastestMerchantUploadedDocument>) => response.ok),
                map(
                    (lastestMerchantUploadedDocument: HttpResponse<LastestMerchantUploadedDocument>) => lastestMerchantUploadedDocument.body
                )
            );
        }
        return of(new LastestMerchantUploadedDocument());
    }
}

export const lastestMerchantUploadedDocumentRoute: Routes = [
    {
        path: '',
        component: LastestMerchantUploadedDocumentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.lastestMerchantUploadedDocument.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LastestMerchantUploadedDocumentDetailComponent,
        resolve: {
            lastestMerchantUploadedDocument: LastestMerchantUploadedDocumentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.lastestMerchantUploadedDocument.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LastestMerchantUploadedDocumentUpdateComponent,
        resolve: {
            lastestMerchantUploadedDocument: LastestMerchantUploadedDocumentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.lastestMerchantUploadedDocument.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LastestMerchantUploadedDocumentUpdateComponent,
        resolve: {
            lastestMerchantUploadedDocument: LastestMerchantUploadedDocumentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.lastestMerchantUploadedDocument.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lastestMerchantUploadedDocumentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LastestMerchantUploadedDocumentDeletePopupComponent,
        resolve: {
            lastestMerchantUploadedDocument: LastestMerchantUploadedDocumentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'resourceApp.lastestMerchantUploadedDocument.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
