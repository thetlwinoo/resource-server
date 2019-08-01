import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from './transaction-types.service';

@Component({
    selector: 'jhi-transaction-types-update',
    templateUrl: './transaction-types-update.component.html'
})
export class TransactionTypesUpdateComponent implements OnInit {
    transactionTypes: ITransactionTypes;
    isSaving: boolean;
    validFromDp: any;
    validToDp: any;

    constructor(protected transactionTypesService: TransactionTypesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ transactionTypes }) => {
            this.transactionTypes = transactionTypes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.transactionTypes.id !== undefined) {
            this.subscribeToSaveResponse(this.transactionTypesService.update(this.transactionTypes));
        } else {
            this.subscribeToSaveResponse(this.transactionTypesService.create(this.transactionTypes));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionTypes>>) {
        result.subscribe((res: HttpResponse<ITransactionTypes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
