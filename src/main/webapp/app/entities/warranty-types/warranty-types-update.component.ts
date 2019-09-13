import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IWarrantyTypes } from 'app/shared/model/warranty-types.model';
import { WarrantyTypesService } from './warranty-types.service';

@Component({
    selector: 'jhi-warranty-types-update',
    templateUrl: './warranty-types-update.component.html'
})
export class WarrantyTypesUpdateComponent implements OnInit {
    warrantyTypes: IWarrantyTypes;
    isSaving: boolean;

    constructor(protected warrantyTypesService: WarrantyTypesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ warrantyTypes }) => {
            this.warrantyTypes = warrantyTypes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.warrantyTypes.id !== undefined) {
            this.subscribeToSaveResponse(this.warrantyTypesService.update(this.warrantyTypes));
        } else {
            this.subscribeToSaveResponse(this.warrantyTypesService.create(this.warrantyTypes));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IWarrantyTypes>>) {
        result.subscribe((res: HttpResponse<IWarrantyTypes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
