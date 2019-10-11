import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiDataUtils } from 'ng-jhipster';
import { ILastestMerchantUploadedDocument } from 'app/shared/model/lastest-merchant-uploaded-document.model';
import { LastestMerchantUploadedDocumentService } from './lastest-merchant-uploaded-document.service';

@Component({
    selector: 'jhi-lastest-merchant-uploaded-document-update',
    templateUrl: './lastest-merchant-uploaded-document-update.component.html'
})
export class LastestMerchantUploadedDocumentUpdateComponent implements OnInit {
    lastestMerchantUploadedDocument: ILastestMerchantUploadedDocument;
    isSaving: boolean;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected lastestMerchantUploadedDocumentService: LastestMerchantUploadedDocumentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lastestMerchantUploadedDocument }) => {
            this.lastestMerchantUploadedDocument = lastestMerchantUploadedDocument;
        });
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
        if (this.lastestMerchantUploadedDocument.id !== undefined) {
            this.subscribeToSaveResponse(this.lastestMerchantUploadedDocumentService.update(this.lastestMerchantUploadedDocument));
        } else {
            this.subscribeToSaveResponse(this.lastestMerchantUploadedDocumentService.create(this.lastestMerchantUploadedDocument));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILastestMerchantUploadedDocument>>) {
        result.subscribe(
            (res: HttpResponse<ILastestMerchantUploadedDocument>) => this.onSaveSuccess(),
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
}
