import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStockGroups } from 'app/shared/model/stock-groups.model';
import { AccountService } from 'app/core';
import { StockGroupsService } from './stock-groups.service';

@Component({
    selector: 'jhi-stock-groups',
    templateUrl: './stock-groups.component.html'
})
export class StockGroupsComponent implements OnInit, OnDestroy {
    stockGroups: IStockGroups[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected stockGroupsService: StockGroupsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.stockGroupsService
            .query()
            .pipe(
                filter((res: HttpResponse<IStockGroups[]>) => res.ok),
                map((res: HttpResponse<IStockGroups[]>) => res.body)
            )
            .subscribe(
                (res: IStockGroups[]) => {
                    this.stockGroups = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStockGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStockGroups) {
        return item.id;
    }

    registerChangeInStockGroups() {
        this.eventSubscriber = this.eventManager.subscribe('stockGroupsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
