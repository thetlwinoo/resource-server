import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';
import { SupplierImportedDocumentService } from './supplier-imported-document.service';
import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from 'app/entities/upload-transactions';

@Component({
    selector: 'jhi-supplier-imported-document-update',
    templateUrl: './supplier-imported-document-update.component.html'
})
export class SupplierImportedDocumentUpdateComponent implements OnInit {
    supplierImportedDocument: ISupplierImportedDocument;
    isSaving: boolean;

    uploadtransactions: IUploadTransactions[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected supplierImportedDocumentService: SupplierImportedDocumentService,
        protected uploadTransactionsService: UploadTransactionsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplierImportedDocument }) => {
            this.supplierImportedDocument = supplierImportedDocument;
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
        if (this.supplierImportedDocument.id !== undefined) {
            this.subscribeToSaveResponse(this.supplierImportedDocumentService.update(this.supplierImportedDocument));
        } else {
            this.subscribeToSaveResponse(this.supplierImportedDocumentService.create(this.supplierImportedDocument));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierImportedDocument>>) {
        result.subscribe(
            (res: HttpResponse<ISupplierImportedDocument>) => this.onSaveSuccess(),
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

    trackUploadTransactionsById(index: number, item: IUploadTransactions) {
        return item.id;
    }
}
