import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompareProducts } from 'app/shared/model/compare-products.model';
import { AccountService } from 'app/core';
import { CompareProductsService } from './compare-products.service';

@Component({
    selector: 'jhi-compare-products',
    templateUrl: './compare-products.component.html'
})
export class CompareProductsComponent implements OnInit, OnDestroy {
    compareProducts: ICompareProducts[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected compareProductsService: CompareProductsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.compareProductsService
            .query()
            .pipe(
                filter((res: HttpResponse<ICompareProducts[]>) => res.ok),
                map((res: HttpResponse<ICompareProducts[]>) => res.body)
            )
            .subscribe(
                (res: ICompareProducts[]) => {
                    this.compareProducts = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCompareProducts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICompareProducts) {
        return item.id;
    }

    registerChangeInCompareProducts() {
        this.eventSubscriber = this.eventManager.subscribe('compareProductsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
