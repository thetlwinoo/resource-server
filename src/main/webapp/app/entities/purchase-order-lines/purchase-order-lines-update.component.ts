import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';
import { PurchaseOrderLinesService } from './purchase-order-lines.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';
import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from 'app/entities/package-types';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from 'app/entities/purchase-orders';

@Component({
    selector: 'jhi-purchase-order-lines-update',
    templateUrl: './purchase-order-lines-update.component.html'
})
export class PurchaseOrderLinesUpdateComponent implements OnInit {
    purchaseOrderLines: IPurchaseOrderLines;
    isSaving: boolean;

    products: IProducts[];

    packagetypes: IPackageTypes[];

    purchaseorders: IPurchaseOrders[];
    lastReceiptDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected purchaseOrderLinesService: PurchaseOrderLinesService,
        protected productsService: ProductsService,
        protected packageTypesService: PackageTypesService,
        protected purchaseOrdersService: PurchaseOrdersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseOrderLines }) => {
            this.purchaseOrderLines = purchaseOrderLines;
        });
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.packageTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPackageTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPackageTypes[]>) => response.body)
            )
            .subscribe((res: IPackageTypes[]) => (this.packagetypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.purchaseOrdersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPurchaseOrders[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPurchaseOrders[]>) => response.body)
            )
            .subscribe((res: IPurchaseOrders[]) => (this.purchaseorders = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.purchaseOrderLines.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseOrderLinesService.update(this.purchaseOrderLines));
        } else {
            this.subscribeToSaveResponse(this.purchaseOrderLinesService.create(this.purchaseOrderLines));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrderLines>>) {
        result.subscribe((res: HttpResponse<IPurchaseOrderLines>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }

    trackPackageTypesById(index: number, item: IPackageTypes) {
        return item.id;
    }

    trackPurchaseOrdersById(index: number, item: IPurchaseOrders) {
        return item.id;
    }
}
