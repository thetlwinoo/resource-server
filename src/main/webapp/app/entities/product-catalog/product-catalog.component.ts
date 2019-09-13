import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductCatalog } from 'app/shared/model/product-catalog.model';
import { AccountService } from 'app/core';
import { ProductCatalogService } from './product-catalog.service';

@Component({
    selector: 'jhi-product-catalog',
    templateUrl: './product-catalog.component.html'
})
export class ProductCatalogComponent implements OnInit, OnDestroy {
    productCatalogs: IProductCatalog[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productCatalogService: ProductCatalogService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productCatalogService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductCatalog[]>) => res.ok),
                map((res: HttpResponse<IProductCatalog[]>) => res.body)
            )
            .subscribe(
                (res: IProductCatalog[]) => {
                    this.productCatalogs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductCatalogs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductCatalog) {
        return item.id;
    }

    registerChangeInProductCatalogs() {
        this.eventSubscriber = this.eventManager.subscribe('productCatalogListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
