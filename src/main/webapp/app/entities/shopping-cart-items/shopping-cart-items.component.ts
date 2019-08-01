import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';
import { AccountService } from 'app/core';
import { ShoppingCartItemsService } from './shopping-cart-items.service';

@Component({
    selector: 'jhi-shopping-cart-items',
    templateUrl: './shopping-cart-items.component.html'
})
export class ShoppingCartItemsComponent implements OnInit, OnDestroy {
    shoppingCartItems: IShoppingCartItems[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected shoppingCartItemsService: ShoppingCartItemsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.shoppingCartItemsService
            .query()
            .pipe(
                filter((res: HttpResponse<IShoppingCartItems[]>) => res.ok),
                map((res: HttpResponse<IShoppingCartItems[]>) => res.body)
            )
            .subscribe(
                (res: IShoppingCartItems[]) => {
                    this.shoppingCartItems = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInShoppingCartItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IShoppingCartItems) {
        return item.id;
    }

    registerChangeInShoppingCartItems() {
        this.eventSubscriber = this.eventManager.subscribe('shoppingCartItemsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
