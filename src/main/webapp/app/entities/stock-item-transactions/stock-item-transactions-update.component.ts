import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IStockItemTransactions } from 'app/shared/model/stock-item-transactions.model';
import { StockItemTransactionsService } from './stock-item-transactions.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers';
import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from 'app/entities/invoices';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers';
import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from 'app/entities/transaction-types';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from 'app/entities/purchase-orders';

@Component({
    selector: 'jhi-stock-item-transactions-update',
    templateUrl: './stock-item-transactions-update.component.html'
})
export class StockItemTransactionsUpdateComponent implements OnInit {
    stockItemTransactions: IStockItemTransactions;
    isSaving: boolean;

    stockitems: IStockItems[];

    customers: ICustomers[];

    invoices: IInvoices[];

    suppliers: ISuppliers[];

    transactiontypes: ITransactionTypes[];

    purchaseorders: IPurchaseOrders[];
    transactionOccurredWhenDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockItemTransactionsService: StockItemTransactionsService,
        protected stockItemsService: StockItemsService,
        protected customersService: CustomersService,
        protected invoicesService: InvoicesService,
        protected suppliersService: SuppliersService,
        protected transactionTypesService: TransactionTypesService,
        protected purchaseOrdersService: PurchaseOrdersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockItemTransactions }) => {
            this.stockItemTransactions = stockItemTransactions;
        });
        this.stockItemsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockItems[]>) => response.body)
            )
            .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.customersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomers[]>) => response.body)
            )
            .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.invoicesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInvoices[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInvoices[]>) => response.body)
            )
            .subscribe((res: IInvoices[]) => (this.invoices = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stockItemTransactions.id !== undefined) {
            this.subscribeToSaveResponse(this.stockItemTransactionsService.update(this.stockItemTransactions));
        } else {
            this.subscribeToSaveResponse(this.stockItemTransactionsService.create(this.stockItemTransactions));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItemTransactions>>) {
        result.subscribe(
            (res: HttpResponse<IStockItemTransactions>) => this.onSaveSuccess(),
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

    trackStockItemsById(index: number, item: IStockItems) {
        return item.id;
    }

    trackCustomersById(index: number, item: ICustomers) {
        return item.id;
    }

    trackInvoicesById(index: number, item: IInvoices) {
        return item.id;
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
}
