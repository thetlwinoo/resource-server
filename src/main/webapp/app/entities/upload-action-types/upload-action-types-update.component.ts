import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';
import { UploadActionTypesService } from './upload-action-types.service';

@Component({
    selector: 'jhi-upload-action-types-update',
    templateUrl: './upload-action-types-update.component.html'
})
export class UploadActionTypesUpdateComponent implements OnInit {
    uploadActionTypes: IUploadActionTypes;
    isSaving: boolean;

    constructor(protected uploadActionTypesService: UploadActionTypesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ uploadActionTypes }) => {
            this.uploadActionTypes = uploadActionTypes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.uploadActionTypes.id !== undefined) {
            this.subscribeToSaveResponse(this.uploadActionTypesService.update(this.uploadActionTypes));
        } else {
            this.subscribeToSaveResponse(this.uploadActionTypesService.create(this.uploadActionTypes));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUploadActionTypes>>) {
        result.subscribe((res: HttpResponse<IUploadActionTypes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
