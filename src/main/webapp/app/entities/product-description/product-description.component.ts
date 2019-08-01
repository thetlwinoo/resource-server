import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductDescription } from 'app/shared/model/product-description.model';
import { AccountService } from 'app/core';
import { ProductDescriptionService } from './product-description.service';

@Component({
    selector: 'jhi-product-description',
    templateUrl: './product-description.component.html'
})
export class ProductDescriptionComponent implements OnInit, OnDestroy {
    productDescriptions: IProductDescription[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productDescriptionService: ProductDescriptionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productDescriptionService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductDescription[]>) => res.ok),
                map((res: HttpResponse<IProductDescription[]>) => res.body)
            )
            .subscribe(
                (res: IProductDescription[]) => {
                    this.productDescriptions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductDescriptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductDescription) {
        return item.id;
    }

    registerChangeInProductDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('productDescriptionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
