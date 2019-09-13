import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';
import { StockItemHoldingsService } from './stock-item-holdings.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items';

@Component({
    selector: 'jhi-stock-item-holdings-update',
    templateUrl: './stock-item-holdings-update.component.html'
})
export class StockItemHoldingsUpdateComponent implements OnInit {
    stockItemHoldings: IStockItemHoldings;
    isSaving: boolean;

    stockitems: IStockItems[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockItemHoldingsService: StockItemHoldingsService,
        protected stockItemsService: StockItemsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockItemHoldings }) => {
            this.stockItemHoldings = stockItemHoldings;
        });
        this.stockItemsService
            .query({ filter: 'stockitemholding-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockItems[]>) => response.body)
            )
            .subscribe(
                (res: IStockItems[]) => {
                    if (!this.stockItemHoldings.stockItemId) {
                        this.stockitems = res;
                    } else {
                        this.stockItemsService
                            .find(this.stockItemHoldings.stockItemId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IStockItems>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IStockItems>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IStockItems) => (this.stockitems = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stockItemHoldings.id !== undefined) {
            this.subscribeToSaveResponse(this.stockItemHoldingsService.update(this.stockItemHoldings));
        } else {
            this.subscribeToSaveResponse(this.stockItemHoldingsService.create(this.stockItemHoldings));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItemHoldings>>) {
        result.subscribe((res: HttpResponse<IStockItemHoldings>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackStockItemsById(index: number, item: IStockItems) {
        return item.id;
    }
}
