import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from './purchase-orders.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers';
import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from 'app/entities/delivery-methods';

@Component({
    selector: 'jhi-purchase-orders-update',
    templateUrl: './purchase-orders-update.component.html'
})
export class PurchaseOrdersUpdateComponent implements OnInit {
    purchaseOrders: IPurchaseOrders;
    isSaving: boolean;

    people: IPeople[];

    suppliers: ISuppliers[];

    deliverymethods: IDeliveryMethods[];
    orderDateDp: any;
    expectedDeliveryDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected purchaseOrdersService: PurchaseOrdersService,
        protected peopleService: PeopleService,
        protected suppliersService: SuppliersService,
        protected deliveryMethodsService: DeliveryMethodsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseOrders }) => {
            this.purchaseOrders = purchaseOrders;
        });
        this.peopleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.suppliersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISuppliers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISuppliers[]>) => response.body)
            )
            .subscribe((res: ISuppliers[]) => (this.suppliers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.deliveryMethodsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDeliveryMethods[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDeliveryMethods[]>) => response.body)
            )
            .subscribe((res: IDeliveryMethods[]) => (this.deliverymethods = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.purchaseOrders.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseOrdersService.update(this.purchaseOrders));
        } else {
            this.subscribeToSaveResponse(this.purchaseOrdersService.create(this.purchaseOrders));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrders>>) {
        result.subscribe((res: HttpResponse<IPurchaseOrders>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPeopleById(index: number, item: IPeople) {
        return item.id;
    }

    trackSuppliersById(index: number, item: ISuppliers) {
        return item.id;
    }

    trackDeliveryMethodsById(index: number, item: IDeliveryMethods) {
        return item.id;
    }
}
