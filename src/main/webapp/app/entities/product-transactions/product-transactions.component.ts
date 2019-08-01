import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductTransactions } from 'app/shared/model/product-transactions.model';
import { AccountService } from 'app/core';
import { ProductTransactionsService } from './product-transactions.service';

@Component({
    selector: 'jhi-product-transactions',
    templateUrl: './product-transactions.component.html'
})
export class ProductTransactionsComponent implements OnInit, OnDestroy {
    productTransactions: IProductTransactions[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productTransactionsService: ProductTransactionsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productTransactionsService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductTransactions[]>) => res.ok),
                map((res: HttpResponse<IProductTransactions[]>) => res.body)
            )
            .subscribe(
                (res: IProductTransactions[]) => {
                    this.productTransactions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductTransactions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductTransactions) {
        return item.id;
    }

    registerChangeInProductTransactions() {
        this.eventSubscriber = this.eventManager.subscribe('productTransactionsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
