import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IBarcodeTypes } from 'app/shared/model/barcode-types.model';
import { BarcodeTypesService } from './barcode-types.service';

@Component({
    selector: 'jhi-barcode-types-update',
    templateUrl: './barcode-types-update.component.html'
})
export class BarcodeTypesUpdateComponent implements OnInit {
    barcodeTypes: IBarcodeTypes;
    isSaving: boolean;

    constructor(protected barcodeTypesService: BarcodeTypesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ barcodeTypes }) => {
            this.barcodeTypes = barcodeTypes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.barcodeTypes.id !== undefined) {
            this.subscribeToSaveResponse(this.barcodeTypesService.update(this.barcodeTypes));
        } else {
            this.subscribeToSaveResponse(this.barcodeTypesService.create(this.barcodeTypes));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBarcodeTypes>>) {
        result.subscribe((res: HttpResponse<IBarcodeTypes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
