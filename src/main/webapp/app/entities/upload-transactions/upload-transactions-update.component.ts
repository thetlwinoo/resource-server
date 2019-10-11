import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from './upload-transactions.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers';
import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';
import { UploadActionTypesService } from 'app/entities/upload-action-types';

@Component({
    selector: 'jhi-upload-transactions-update',
    templateUrl: './upload-transactions-update.component.html'
})
export class UploadTransactionsUpdateComponent implements OnInit {
    uploadTransactions: IUploadTransactions;
    isSaving: boolean;

    suppliers: ISuppliers[];

    uploadactiontypes: IUploadActionTypes[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected uploadTransactionsService: UploadTransactionsService,
        protected suppliersService: SuppliersService,
        protected uploadActionTypesService: UploadActionTypesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ uploadTransactions }) => {
            this.uploadTransactions = uploadTransactions;
        });
        this.suppliersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISuppliers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISuppliers[]>) => response.body)
            )
            .subscribe((res: ISuppliers[]) => (this.suppliers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.uploadActionTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUploadActionTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUploadActionTypes[]>) => response.body)
            )
            .subscribe(
                (res: IUploadActionTypes[]) => (this.uploadactiontypes = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.uploadTransactions.id !== undefined) {
            this.subscribeToSaveResponse(this.uploadTransactionsService.update(this.uploadTransactions));
        } else {
            this.subscribeToSaveResponse(this.uploadTransactionsService.create(this.uploadTransactions));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUploadTransactions>>) {
        result.subscribe((res: HttpResponse<IUploadTransactions>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUploadActionTypesById(index: number, item: IUploadActionTypes) {
        return item.id;
    }
}
