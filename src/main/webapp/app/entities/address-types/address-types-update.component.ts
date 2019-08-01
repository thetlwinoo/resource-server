import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AddressTypesService } from './address-types.service';

@Component({
    selector: 'jhi-address-types-update',
    templateUrl: './address-types-update.component.html'
})
export class AddressTypesUpdateComponent implements OnInit {
    addressTypes: IAddressTypes;
    isSaving: boolean;

    constructor(protected addressTypesService: AddressTypesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ addressTypes }) => {
            this.addressTypes = addressTypes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.addressTypes.id !== undefined) {
            this.subscribeToSaveResponse(this.addressTypesService.update(this.addressTypes));
        } else {
            this.subscribeToSaveResponse(this.addressTypesService.create(this.addressTypes));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddressTypes>>) {
        result.subscribe((res: HttpResponse<IAddressTypes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
