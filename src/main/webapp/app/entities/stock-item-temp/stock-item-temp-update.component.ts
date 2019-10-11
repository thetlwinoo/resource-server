import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IStockItemTemp } from 'app/shared/model/stock-item-temp.model';
import { StockItemTempService } from './stock-item-temp.service';
import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from 'app/entities/upload-transactions';

@Component({
    selector: 'jhi-stock-item-temp-update',
    templateUrl: './stock-item-temp-update.component.html'
})
export class StockItemTempUpdateComponent implements OnInit {
    stockItemTemp: IStockItemTemp;
    isSaving: boolean;

    uploadtransactions: IUploadTransactions[];
    sellStartDateDp: any;
    sellEndDateDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected stockItemTempService: StockItemTempService,
        protected uploadTransactionsService: UploadTransactionsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockItemTemp }) => {
            this.stockItemTemp = stockItemTemp;
        });
        this.uploadTransactionsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUploadTransactions[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUploadTransactions[]>) => response.body)
            )
            .subscribe(
                (res: IUploadTransactions[]) => (this.uploadtransactions = res),
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
        if (this.stockItemTemp.id !== undefined) {
            this.subscribeToSaveResponse(this.stockItemTempService.update(this.stockItemTemp));
        } else {
            this.subscribeToSaveResponse(this.stockItemTempService.create(this.stockItemTemp));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItemTemp>>) {
        result.subscribe((res: HttpResponse<IStockItemTemp>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUploadTransactionsById(index: number, item: IUploadTransactions) {
        return item.id;
    }
}
