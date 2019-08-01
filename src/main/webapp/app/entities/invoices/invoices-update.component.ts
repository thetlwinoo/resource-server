import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from './invoices.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers';
import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from 'app/entities/delivery-methods';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders';

@Component({
    selector: 'jhi-invoices-update',
    templateUrl: './invoices-update.component.html'
})
export class InvoicesUpdateComponent implements OnInit {
    invoices: IInvoices;
    isSaving: boolean;

    people: IPeople[];

    customers: ICustomers[];

    deliverymethods: IDeliveryMethods[];

    orders: IOrders[];
    invoiceDateDp: any;
    confirmedDeliveryTime: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected invoicesService: InvoicesService,
        protected peopleService: PeopleService,
        protected customersService: CustomersService,
        protected deliveryMethodsService: DeliveryMethodsService,
        protected ordersService: OrdersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ invoices }) => {
            this.invoices = invoices;
            this.confirmedDeliveryTime =
                this.invoices.confirmedDeliveryTime != null ? this.invoices.confirmedDeliveryTime.format(DATE_TIME_FORMAT) : null;
        });
        this.peopleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.customersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomers[]>) => response.body)
            )
            .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.deliveryMethodsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDeliveryMethods[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDeliveryMethods[]>) => response.body)
            )
            .subscribe((res: IDeliveryMethods[]) => (this.deliverymethods = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.invoices.confirmedDeliveryTime =
            this.confirmedDeliveryTime != null ? moment(this.confirmedDeliveryTime, DATE_TIME_FORMAT) : null;
        if (this.invoices.id !== undefined) {
            this.subscribeToSaveResponse(this.invoicesService.update(this.invoices));
        } else {
            this.subscribeToSaveResponse(this.invoicesService.create(this.invoices));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoices>>) {
        result.subscribe((res: HttpResponse<IInvoices>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCustomersById(index: number, item: ICustomers) {
        return item.id;
    }

    trackDeliveryMethodsById(index: number, item: IDeliveryMethods) {
        return item.id;
    }

    trackOrdersById(index: number, item: IOrders) {
        return item.id;
    }
}
