import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IPaymentMethods } from 'app/shared/model/payment-methods.model';
import { PaymentMethodsService } from './payment-methods.service';

@Component({
    selector: 'jhi-payment-methods-update',
    templateUrl: './payment-methods-update.component.html'
})
export class PaymentMethodsUpdateComponent implements OnInit {
    paymentMethods: IPaymentMethods;
    isSaving: boolean;
    validFromDp: any;
    validToDp: any;

    constructor(protected paymentMethodsService: PaymentMethodsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paymentMethods }) => {
            this.paymentMethods = paymentMethods;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.paymentMethods.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentMethodsService.update(this.paymentMethods));
        } else {
            this.subscribeToSaveResponse(this.paymentMethodsService.create(this.paymentMethods));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethods>>) {
        result.subscribe((res: HttpResponse<IPaymentMethods>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
