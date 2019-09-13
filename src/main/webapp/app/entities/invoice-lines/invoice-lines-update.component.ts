import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInvoiceLines } from 'app/shared/model/invoice-lines.model';
import { InvoiceLinesService } from './invoice-lines.service';
import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from 'app/entities/package-types';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items';
import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from 'app/entities/invoices';

@Component({
    selector: 'jhi-invoice-lines-update',
    templateUrl: './invoice-lines-update.component.html'
})
export class InvoiceLinesUpdateComponent implements OnInit {
    invoiceLines: IInvoiceLines;
    isSaving: boolean;

    packagetypes: IPackageTypes[];

    stockitems: IStockItems[];

    invoices: IInvoices[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected invoiceLinesService: InvoiceLinesService,
        protected packageTypesService: PackageTypesService,
        protected stockItemsService: StockItemsService,
        protected invoicesService: InvoicesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ invoiceLines }) => {
            this.invoiceLines = invoiceLines;
        });
        this.packageTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPackageTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPackageTypes[]>) => response.body)
            )
            .subscribe((res: IPackageTypes[]) => (this.packagetypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.stockItemsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockItems[]>) => response.body)
            )
            .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.invoiceLines.id !== undefined) {
            this.subscribeToSaveResponse(this.invoiceLinesService.update(this.invoiceLines));
        } else {
            this.subscribeToSaveResponse(this.invoiceLinesService.create(this.invoiceLines));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceLines>>) {
        result.subscribe((res: HttpResponse<IInvoiceLines>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStockItemsById(index: number, item: IStockItems) {
        return item.id;
    }

    trackInvoicesById(index: number, item: IInvoices) {
        return item.id;
    }
}
