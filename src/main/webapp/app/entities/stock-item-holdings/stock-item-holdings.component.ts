import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';
import { AccountService } from 'app/core';
import { StockItemHoldingsService } from './stock-item-holdings.service';

@Component({
    selector: 'jhi-stock-item-holdings',
    templateUrl: './stock-item-holdings.component.html'
})
export class StockItemHoldingsComponent implements OnInit, OnDestroy {
    stockItemHoldings: IStockItemHoldings[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected stockItemHoldingsService: StockItemHoldingsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.stockItemHoldingsService
            .query()
            .pipe(
                filter((res: HttpResponse<IStockItemHoldings[]>) => res.ok),
                map((res: HttpResponse<IStockItemHoldings[]>) => res.body)
            )
            .subscribe(
                (res: IStockItemHoldings[]) => {
                    this.stockItemHoldings = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStockItemHoldings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStockItemHoldings) {
        return item.id;
    }

    registerChangeInStockItemHoldings() {
        this.eventSubscriber = this.eventManager.subscribe('stockItemHoldingsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
