import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from './payment-transactions.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders';

@Component({
    selector: 'jhi-payment-transactions-update',
    templateUrl: './payment-transactions-update.component.html'
})
export class PaymentTransactionsUpdateComponent implements OnInit {
    paymentTransactions: IPaymentTransactions;
    isSaving: boolean;

    orders: IOrders[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected paymentTransactionsService: PaymentTransactionsService,
        protected ordersService: OrdersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paymentTransactions }) => {
            this.paymentTransactions = paymentTransactions;
        });
        this.ordersService
            .query({ filter: 'paymenttransaction-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IOrders[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOrders[]>) => response.body)
            )
            .subscribe(
                (res: IOrders[]) => {
                    if (!this.paymentTransactions.orderId) {
                        this.orders = res;
                    } else {
                        this.ordersService
                            .find(this.paymentTransactions.orderId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IOrders>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IOrders>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IOrders) => (this.orders = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.paymentTransactions.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentTransactionsService.update(this.paymentTransactions));
        } else {
            this.subscribeToSaveResponse(this.paymentTransactionsService.create(this.paymentTransactions));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentTransactions>>) {
        result.subscribe((res: HttpResponse<IPaymentTransactions>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrdersById(index: number, item: IOrders) {
        return item.id;
    }
}
