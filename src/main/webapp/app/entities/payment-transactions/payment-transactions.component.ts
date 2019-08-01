import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { AccountService } from 'app/core';
import { PaymentTransactionsService } from './payment-transactions.service';

@Component({
    selector: 'jhi-payment-transactions',
    templateUrl: './payment-transactions.component.html'
})
export class PaymentTransactionsComponent implements OnInit, OnDestroy {
    paymentTransactions: IPaymentTransactions[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected paymentTransactionsService: PaymentTransactionsService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.paymentTransactionsService
            .query()
            .pipe(
                filter((res: HttpResponse<IPaymentTransactions[]>) => res.ok),
                map((res: HttpResponse<IPaymentTransactions[]>) => res.body)
            )
            .subscribe(
                (res: IPaymentTransactions[]) => {
                    this.paymentTransactions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPaymentTransactions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPaymentTransactions) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInPaymentTransactions() {
        this.eventSubscriber = this.eventManager.subscribe('paymentTransactionsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
