import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductAttribute } from 'app/shared/model/product-attribute.model';
import { AccountService } from 'app/core';
import { ProductAttributeService } from './product-attribute.service';

@Component({
    selector: 'jhi-product-attribute',
    templateUrl: './product-attribute.component.html'
})
export class ProductAttributeComponent implements OnInit, OnDestroy {
    productAttributes: IProductAttribute[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productAttributeService: ProductAttributeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productAttributeService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductAttribute[]>) => res.ok),
                map((res: HttpResponse<IProductAttribute[]>) => res.body)
            )
            .subscribe(
                (res: IProductAttribute[]) => {
                    this.productAttributes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductAttributes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductAttribute) {
        return item.id;
    }

    registerChangeInProductAttributes() {
        this.eventSubscriber = this.eventManager.subscribe('productAttributeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
