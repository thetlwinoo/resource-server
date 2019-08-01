import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProductTransactions } from 'app/shared/model/product-transactions.model';
import { ProductTransactionsService } from './product-transactions.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';
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
    selector: 'jhi-product-transactions-update',
    templateUrl: './product-transactions-update.component.html'
})
export class ProductTransactionsUpdateComponent implements OnInit {
    productTransactions: IProductTransactions;
    isSaving: boolean;

    products: IProducts[];

    customers: ICustomers[];

    invoices: IInvoices[];

    suppliers: ISuppliers[];

    transactiontypes: ITransactionTypes[];

    purchaseorders: IPurchaseOrders[];
    transactionOccuredWhenDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productTransactionsService: ProductTransactionsService,
        protected productsService: ProductsService,
        protected customersService: CustomersService,
        protected invoicesService: InvoicesService,
        protected suppliersService: SuppliersService,
        protected transactionTypesService: TransactionTypesService,
        protected purchaseOrdersService: PurchaseOrdersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productTransactions }) => {
            this.productTransactions = productTransactions;
        });
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.productTransactions.id !== undefined) {
            this.subscribeToSaveResponse(this.productTransactionsService.update(this.productTransactions));
        } else {
            this.subscribeToSaveResponse(this.productTransactionsService.create(this.productTransactions));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductTransactions>>) {
        result.subscribe((res: HttpResponse<IProductTransactions>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
