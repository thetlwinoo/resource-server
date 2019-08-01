import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IWishlistProducts } from 'app/shared/model/wishlist-products.model';
import { AccountService } from 'app/core';
import { WishlistProductsService } from './wishlist-products.service';

@Component({
    selector: 'jhi-wishlist-products',
    templateUrl: './wishlist-products.component.html'
})
export class WishlistProductsComponent implements OnInit, OnDestroy {
    wishlistProducts: IWishlistProducts[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected wishlistProductsService: WishlistProductsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.wishlistProductsService
            .query()
            .pipe(
                filter((res: HttpResponse<IWishlistProducts[]>) => res.ok),
                map((res: HttpResponse<IWishlistProducts[]>) => res.body)
            )
            .subscribe(
                (res: IWishlistProducts[]) => {
                    this.wishlistProducts = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInWishlistProducts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IWishlistProducts) {
        return item.id;
    }

    registerChangeInWishlistProducts() {
        this.eventSubscriber = this.eventManager.subscribe('wishlistProductsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
