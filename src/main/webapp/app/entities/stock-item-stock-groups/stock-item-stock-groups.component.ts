import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStockItemStockGroups } from 'app/shared/model/stock-item-stock-groups.model';
import { AccountService } from 'app/core';
import { StockItemStockGroupsService } from './stock-item-stock-groups.service';

@Component({
    selector: 'jhi-stock-item-stock-groups',
    templateUrl: './stock-item-stock-groups.component.html'
})
export class StockItemStockGroupsComponent implements OnInit, OnDestroy {
    stockItemStockGroups: IStockItemStockGroups[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected stockItemStockGroupsService: StockItemStockGroupsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.stockItemStockGroupsService
            .query()
            .pipe(
                filter((res: HttpResponse<IStockItemStockGroups[]>) => res.ok),
                map((res: HttpResponse<IStockItemStockGroups[]>) => res.body)
            )
            .subscribe(
                (res: IStockItemStockGroups[]) => {
                    this.stockItemStockGroups = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStockItemStockGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStockItemStockGroups) {
        return item.id;
    }

    registerChangeInStockItemStockGroups() {
        this.eventSubscriber = this.eventManager.subscribe('stockItemStockGroupsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
