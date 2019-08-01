import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInvoices } from 'app/shared/model/invoices.model';
import { AccountService } from 'app/core';
import { InvoicesService } from './invoices.service';

@Component({
    selector: 'jhi-invoices',
    templateUrl: './invoices.component.html'
})
export class InvoicesComponent implements OnInit, OnDestroy {
    invoices: IInvoices[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected invoicesService: InvoicesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.invoicesService
            .query()
            .pipe(
                filter((res: HttpResponse<IInvoices[]>) => res.ok),
                map((res: HttpResponse<IInvoices[]>) => res.body)
            )
            .subscribe(
                (res: IInvoices[]) => {
                    this.invoices = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInInvoices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IInvoices) {
        return item.id;
    }

    registerChangeInInvoices() {
        this.eventSubscriber = this.eventManager.subscribe('invoicesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
