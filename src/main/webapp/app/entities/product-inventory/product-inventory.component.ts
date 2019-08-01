import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductInventory } from 'app/shared/model/product-inventory.model';
import { AccountService } from 'app/core';
import { ProductInventoryService } from './product-inventory.service';

@Component({
    selector: 'jhi-product-inventory',
    templateUrl: './product-inventory.component.html'
})
export class ProductInventoryComponent implements OnInit, OnDestroy {
    productInventories: IProductInventory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productInventoryService: ProductInventoryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productInventoryService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductInventory[]>) => res.ok),
                map((res: HttpResponse<IProductInventory[]>) => res.body)
            )
            .subscribe(
                (res: IProductInventory[]) => {
                    this.productInventories = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductInventories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductInventory) {
        return item.id;
    }

    registerChangeInProductInventories() {
        this.eventSubscriber = this.eventManager.subscribe('productInventoryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
