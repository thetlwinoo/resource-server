import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISupplierTransactions } from 'app/shared/model/supplier-transactions.model';
import { SupplierTransactionsService } from './supplier-transactions.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers';
import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from 'app/entities/transaction-types';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from 'app/entities/purchase-orders';
import { IPaymentMethods } from 'app/shared/model/payment-methods.model';
import { PaymentMethodsService } from 'app/entities/payment-methods';

@Component({
    selector: 'jhi-supplier-transactions-update',
    templateUrl: './supplier-transactions-update.component.html'
})
export class SupplierTransactionsUpdateComponent implements OnInit {
    supplierTransactions: ISupplierTransactions;
    isSaving: boolean;

    suppliers: ISuppliers[];

    transactiontypes: ITransactionTypes[];

    purchaseorders: IPurchaseOrders[];

    paymentmethods: IPaymentMethods[];
    transactionDateDp: any;
    finalizationDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplierTransactionsService: SupplierTransactionsService,
        protected suppliersService: SuppliersService,
        protected transactionTypesService: TransactionTypesService,
        protected purchaseOrdersService: PurchaseOrdersService,
        protected paymentMethodsService: PaymentMethodsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplierTransactions }) => {
            this.supplierTransactions = supplierTransactions;
        });
        this.suppliersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISuppliers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISuppliers[]>) => response.body)
            )
            .subscribe((res: ISuppliers[]) => (this.suppliers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.transactionTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITransactionTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITransactionTypes[]>) => response.body)
            )
            .subscribe((res: ITransactionTypes[]) => (this.transactiontypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.purchaseOrdersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPurchaseOrders[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPurchaseOrders[]>) => response.body)
            )
            .subscribe((res: IPurchaseOrders[]) => (this.purchaseorders = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.paymentMethodsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPaymentMethods[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPaymentMethods[]>) => response.body)
            )
            .subscribe((res: IPaymentMethods[]) => (this.paymentmethods = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.supplierTransactions.id !== undefined) {
            this.subscribeToSaveResponse(this.supplierTransactionsService.update(this.supplierTransactions));
        } else {
            this.subscribeToSaveResponse(this.supplierTransactionsService.create(this.supplierTransactions));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierTransactions>>) {
        result.subscribe(
            (res: HttpResponse<ISupplierTransactions>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackSuppliersById(index: number, item: ISuppliers) {
        return item.id;
    }

    trackTransactionTypesById(index: number, item: ITransactionTypes) {
        return item.id;
    }

    trackPurchaseOrdersById(index: number, item: IPurchaseOrders) {
        return item.id;
    }

    trackPaymentMethodsById(index: number, item: IPaymentMethods) {
        return item.id;
    }
}
