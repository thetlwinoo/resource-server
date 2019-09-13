import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { AccountService } from 'app/core';
import { ProductOptionSetService } from './product-option-set.service';

@Component({
    selector: 'jhi-product-option-set',
    templateUrl: './product-option-set.component.html'
})
export class ProductOptionSetComponent implements OnInit, OnDestroy {
    productOptionSets: IProductOptionSet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productOptionSetService: ProductOptionSetService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productOptionSetService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductOptionSet[]>) => res.ok),
                map((res: HttpResponse<IProductOptionSet[]>) => res.body)
            )
            .subscribe(
                (res: IProductOptionSet[]) => {
                    this.productOptionSets = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductOptionSets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductOptionSet) {
        return item.id;
    }

    registerChangeInProductOptionSets() {
        this.eventSubscriber = this.eventManager.subscribe('productOptionSetListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
