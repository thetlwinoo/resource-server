import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { AccountService } from 'app/core';
import { TransactionTypesService } from './transaction-types.service';

@Component({
    selector: 'jhi-transaction-types',
    templateUrl: './transaction-types.component.html'
})
export class TransactionTypesComponent implements OnInit, OnDestroy {
    transactionTypes: ITransactionTypes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected transactionTypesService: TransactionTypesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.transactionTypesService
            .query()
            .pipe(
                filter((res: HttpResponse<ITransactionTypes[]>) => res.ok),
                map((res: HttpResponse<ITransactionTypes[]>) => res.body)
            )
            .subscribe(
                (res: ITransactionTypes[]) => {
                    this.transactionTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTransactionTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITransactionTypes) {
        return item.id;
    }

    registerChangeInTransactionTypes() {
        this.eventSubscriber = this.eventManager.subscribe('transactionTypesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
