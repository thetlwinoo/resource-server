import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from './delivery-methods.service';

@Component({
    selector: 'jhi-delivery-methods-update',
    templateUrl: './delivery-methods-update.component.html'
})
export class DeliveryMethodsUpdateComponent implements OnInit {
    deliveryMethods: IDeliveryMethods;
    isSaving: boolean;
    validFromDp: any;
    validToDp: any;

    constructor(protected deliveryMethodsService: DeliveryMethodsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deliveryMethods }) => {
            this.deliveryMethods = deliveryMethods;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.deliveryMethods.id !== undefined) {
            this.subscribeToSaveResponse(this.deliveryMethodsService.update(this.deliveryMethods));
        } else {
            this.subscribeToSaveResponse(this.deliveryMethodsService.create(this.deliveryMethods));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeliveryMethods>>) {
        result.subscribe((res: HttpResponse<IDeliveryMethods>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
