import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';
import { IReviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from 'app/entities/reviews';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers';
import { IAddresses } from 'app/shared/model/addresses.model';
import { AddressesService } from 'app/entities/addresses';
import { IShipMethod } from 'app/shared/model/ship-method.model';
import { ShipMethodService } from 'app/entities/ship-method';
import { ICurrencyRate } from 'app/shared/model/currency-rate.model';
import { CurrencyRateService } from 'app/entities/currency-rate';
import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from 'app/entities/payment-transactions';
import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from 'app/entities/special-deals';

@Component({
    selector: 'jhi-orders-update',
    templateUrl: './orders-update.component.html'
})
export class OrdersUpdateComponent implements OnInit {
    orders: IOrders;
    isSaving: boolean;

    orderreviews: IReviews[];

    customers: ICustomers[];

    addresses: IAddresses[];

    shipmethods: IShipMethod[];

    currencyrates: ICurrencyRate[];

    paymenttransactions: IPaymentTransactions[];

    specialdeals: ISpecialDeals[];
    orderDateDp: any;
    dueDateDp: any;
    shipDateDp: any;
    pickingCompletedWhenDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected ordersService: OrdersService,
        protected reviewsService: ReviewsService,
        protected customersService: CustomersService,
        protected addressesService: AddressesService,
        protected shipMethodService: ShipMethodService,
        protected currencyRateService: CurrencyRateService,
        protected paymentTransactionsService: PaymentTransactionsService,
        protected specialDealsService: SpecialDealsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orders }) => {
            this.orders = orders;
        });
        this.reviewsService
            .query({ 'orderId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IReviews[]>) => mayBeOk.ok),
                map((response: HttpResponse<IReviews[]>) => response.body)
            )
            .subscribe(
                (res: IReviews[]) => {
                    if (!this.orders.orderReviewId) {
                        this.orderreviews = res;
                    } else {
                        this.reviewsService
                            .find(this.orders.orderReviewId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IReviews>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IReviews>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IReviews) => (this.orderreviews = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.customersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomers[]>) => response.body)
            )
            .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.addressesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAddresses[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAddresses[]>) => response.body)
            )
            .subscribe((res: IAddresses[]) => (this.addresses = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.shipMethodService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IShipMethod[]>) => mayBeOk.ok),
                map((response: HttpResponse<IShipMethod[]>) => response.body)
            )
            .subscribe((res: IShipMethod[]) => (this.shipmethods = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.currencyRateService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICurrencyRate[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICurrencyRate[]>) => response.body)
            )
            .subscribe((res: ICurrencyRate[]) => (this.currencyrates = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.paymentTransactionsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPaymentTransactions[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPaymentTransactions[]>) => response.body)
            )
            .subscribe(
                (res: IPaymentTransactions[]) => (this.paymenttransactions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.specialDealsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISpecialDeals[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISpecialDeals[]>) => response.body)
            )
            .subscribe((res: ISpecialDeals[]) => (this.specialdeals = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.orders.id !== undefined) {
            this.subscribeToSaveResponse(this.ordersService.update(this.orders));
        } else {
            this.subscribeToSaveResponse(this.ordersService.create(this.orders));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrders>>) {
        result.subscribe((res: HttpResponse<IOrders>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackReviewsById(index: number, item: IReviews) {
        return item.id;
    }

    trackCustomersById(index: number, item: ICustomers) {
        return item.id;
    }

    trackAddressesById(index: number, item: IAddresses) {
        return item.id;
    }

    trackShipMethodById(index: number, item: IShipMethod) {
        return item.id;
    }

    trackCurrencyRateById(index: number, item: ICurrencyRate) {
        return item.id;
    }

    trackPaymentTransactionsById(index: number, item: IPaymentTransactions) {
        return item.id;
    }

    trackSpecialDealsById(index: number, item: ISpecialDeals) {
        return item.id;
    }
}
