import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { AccountService } from 'app/core';
import { SpecialDealsService } from './special-deals.service';

@Component({
    selector: 'jhi-special-deals',
    templateUrl: './special-deals.component.html'
})
export class SpecialDealsComponent implements OnInit, OnDestroy {
    specialDeals: ISpecialDeals[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected specialDealsService: SpecialDealsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.specialDealsService
            .query()
            .pipe(
                filter((res: HttpResponse<ISpecialDeals[]>) => res.ok),
                map((res: HttpResponse<ISpecialDeals[]>) => res.body)
            )
            .subscribe(
                (res: ISpecialDeals[]) => {
                    this.specialDeals = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSpecialDeals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISpecialDeals) {
        return item.id;
    }

    registerChangeInSpecialDeals() {
        this.eventSubscriber = this.eventManager.subscribe('specialDealsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
