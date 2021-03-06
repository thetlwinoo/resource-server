import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ICurrencyRate } from 'app/shared/model/currency-rate.model';
import { CurrencyRateService } from './currency-rate.service';

@Component({
    selector: 'jhi-currency-rate-update',
    templateUrl: './currency-rate-update.component.html'
})
export class CurrencyRateUpdateComponent implements OnInit {
    currencyRate: ICurrencyRate;
    isSaving: boolean;
    currencyRateDateDp: any;

    constructor(protected currencyRateService: CurrencyRateService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ currencyRate }) => {
            this.currencyRate = currencyRate;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.currencyRate.id !== undefined) {
            this.subscribeToSaveResponse(this.currencyRateService.update(this.currencyRate));
        } else {
            this.subscribeToSaveResponse(this.currencyRateService.create(this.currencyRate));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrencyRate>>) {
        result.subscribe((res: HttpResponse<ICurrencyRate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
