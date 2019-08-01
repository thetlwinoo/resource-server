import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomerTransactions } from 'app/shared/model/customer-transactions.model';
import { CustomerTransactionsService } from './customer-transactions.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers';
import { IPaymentMethods } from 'app/shared/model/payment-methods.model';
import { PaymentMethodsService } from 'app/entities/payment-methods';
import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from 'app/entities/payment-transactions';
import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from 'app/entities/transaction-types';
import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from 'app/entities/invoices';

@Component({
    selector: 'jhi-customer-transactions-update',
    templateUrl: './customer-transactions-update.component.html'
})
export class CustomerTransactionsUpdateComponent implements OnInit {
    customerTransactions: ICustomerTransactions;
    isSaving: boolean;

    customers: ICustomers[];

    paymentmethods: IPaymentMethods[];

    paymenttransactions: IPaymentTransactions[];

    transactiontypes: ITransactionTypes[];

    invoices: IInvoices[];
    transactionDateDp: any;
    finalizationDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected customerTransactionsService: CustomerTransactionsService,
        protected customersService: CustomersService,
        protected paymentMethodsService: PaymentMethodsService,
        protected paymentTransactionsService: PaymentTransactionsService,
        protected transactionTypesService: TransactionTypesService,
        protected invoicesService: InvoicesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ customerTransactions }) => {
            this.customerTransactions = customerTransactions;
        });
        this.customersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomers[]>) => response.body)
            )
            .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.paymentMethodsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPaymentMethods[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPaymentMethods[]>) => response.body)
            )
            .subscribe((res: IPaymentMethods[]) => (this.paymentmethods = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.transactionTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITransactionTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITransactionTypes[]>) => response.body)
            )
            .subscribe((res: ITransactionTypes[]) => (this.transactiontypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.invoicesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInvoices[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInvoices[]>) => response.body)
            )
            .subscribe((res: IInvoices[]) => (this.invoices = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customerTransactions.id !== undefined) {
            this.subscribeToSaveResponse(this.customerTransactionsService.update(this.customerTransactions));
        } else {
            this.subscribeToSaveResponse(this.customerTransactionsService.create(this.customerTransactions));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerTransactions>>) {
        result.subscribe(
            (res: HttpResponse<ICustomerTransactions>) => this.onSaveSuccess(),
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

    trackCustomersById(index: number, item: ICustomers) {
        return item.id;
    }

    trackPaymentMethodsById(index: number, item: IPaymentMethods) {
        return item.id;
    }

    trackPaymentTransactionsById(index: number, item: IPaymentTransactions) {
        return item.id;
    }

    trackTransactionTypesById(index: number, item: ITransactionTypes) {
        return item.id;
    }

    trackInvoicesById(index: number, item: IInvoices) {
        return item.id;
    }
}
