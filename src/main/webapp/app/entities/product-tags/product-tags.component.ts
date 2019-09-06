import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductTags } from 'app/shared/model/product-tags.model';
import { AccountService } from 'app/core';
import { ProductTagsService } from './product-tags.service';

@Component({
    selector: 'jhi-product-tags',
    templateUrl: './product-tags.component.html'
})
export class ProductTagsComponent implements OnInit, OnDestroy {
    productTags: IProductTags[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productTagsService: ProductTagsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productTagsService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductTags[]>) => res.ok),
                map((res: HttpResponse<IProductTags[]>) => res.body)
            )
            .subscribe(
                (res: IProductTags[]) => {
                    this.productTags = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductTags();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductTags) {
        return item.id;
    }

    registerChangeInProductTags() {
        this.eventSubscriber = this.eventManager.subscribe('productTagsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
