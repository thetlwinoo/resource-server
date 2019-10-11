import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { AccountService } from 'app/core';
import { UploadTransactionsService } from './upload-transactions.service';

@Component({
    selector: 'jhi-upload-transactions',
    templateUrl: './upload-transactions.component.html'
})
export class UploadTransactionsComponent implements OnInit, OnDestroy {
    uploadTransactions: IUploadTransactions[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected uploadTransactionsService: UploadTransactionsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.uploadTransactionsService
            .query()
            .pipe(
                filter((res: HttpResponse<IUploadTransactions[]>) => res.ok),
                map((res: HttpResponse<IUploadTransactions[]>) => res.body)
            )
            .subscribe(
                (res: IUploadTransactions[]) => {
                    this.uploadTransactions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUploadTransactions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUploadTransactions) {
        return item.id;
    }

    registerChangeInUploadTransactions() {
        this.eventSubscriber = this.eventManager.subscribe('uploadTransactionsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
