import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IOrderLines } from 'app/shared/model/order-lines.model';
import { OrderLinesService } from './order-lines.service';
import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from 'app/entities/package-types';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders';

@Component({
    selector: 'jhi-order-lines-update',
    templateUrl: './order-lines-update.component.html'
})
export class OrderLinesUpdateComponent implements OnInit {
    orderLines: IOrderLines;
    isSaving: boolean;

    packagetypes: IPackageTypes[];

    products: IProducts[];

    orders: IOrders[];
    pickingCompletedWhenDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected orderLinesService: OrderLinesService,
        protected packageTypesService: PackageTypesService,
        protected productsService: ProductsService,
        protected ordersService: OrdersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orderLines }) => {
            this.orderLines = orderLines;
        });
        this.packageTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPackageTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPackageTypes[]>) => response.body)
            )
            .subscribe((res: IPackageTypes[]) => (this.packagetypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.ordersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOrders[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOrders[]>) => response.body)
            )
            .subscribe((res: IOrders[]) => (this.orders = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.orderLines.id !== undefined) {
            this.subscribeToSaveResponse(this.orderLinesService.update(this.orderLines));
        } else {
            this.subscribeToSaveResponse(this.orderLinesService.create(this.orderLines));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderLines>>) {
        result.subscribe((res: HttpResponse<IOrderLines>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPackageTypesById(index: number, item: IPackageTypes) {
        return item.id;
    }

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }

    trackOrdersById(index: number, item: IOrders) {
        return item.id;
    }
}
