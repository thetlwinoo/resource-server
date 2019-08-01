import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IPhoneNumberType } from 'app/shared/model/phone-number-type.model';
import { PhoneNumberTypeService } from './phone-number-type.service';

@Component({
    selector: 'jhi-phone-number-type-update',
    templateUrl: './phone-number-type-update.component.html'
})
export class PhoneNumberTypeUpdateComponent implements OnInit {
    phoneNumberType: IPhoneNumberType;
    isSaving: boolean;

    constructor(protected phoneNumberTypeService: PhoneNumberTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ phoneNumberType }) => {
            this.phoneNumberType = phoneNumberType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.phoneNumberType.id !== undefined) {
            this.subscribeToSaveResponse(this.phoneNumberTypeService.update(this.phoneNumberType));
        } else {
            this.subscribeToSaveResponse(this.phoneNumberTypeService.create(this.phoneNumberType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhoneNumberType>>) {
        result.subscribe((res: HttpResponse<IPhoneNumberType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
