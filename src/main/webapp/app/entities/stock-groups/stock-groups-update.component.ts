import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IStockGroups } from 'app/shared/model/stock-groups.model';
import { StockGroupsService } from './stock-groups.service';

@Component({
    selector: 'jhi-stock-groups-update',
    templateUrl: './stock-groups-update.component.html'
})
export class StockGroupsUpdateComponent implements OnInit {
    stockGroups: IStockGroups;
    isSaving: boolean;
    validFromDp: any;
    validToDp: any;

    constructor(protected stockGroupsService: StockGroupsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockGroups }) => {
            this.stockGroups = stockGroups;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stockGroups.id !== undefined) {
            this.subscribeToSaveResponse(this.stockGroupsService.update(this.stockGroups));
        } else {
            this.subscribeToSaveResponse(this.stockGroupsService.create(this.stockGroups));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockGroups>>) {
        result.subscribe((res: HttpResponse<IStockGroups>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
