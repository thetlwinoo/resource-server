import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductModel } from 'app/shared/model/product-model.model';
import { AccountService } from 'app/core';
import { ProductModelService } from './product-model.service';

@Component({
    selector: 'jhi-product-model',
    templateUrl: './product-model.component.html'
})
export class ProductModelComponent implements OnInit, OnDestroy {
    productModels: IProductModel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productModelService: ProductModelService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productModelService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductModel[]>) => res.ok),
                map((res: HttpResponse<IProductModel[]>) => res.body)
            )
            .subscribe(
                (res: IProductModel[]) => {
                    this.productModels = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductModels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductModel) {
        return item.id;
    }

    registerChangeInProductModels() {
        this.eventSubscriber = this.eventManager.subscribe('productModelListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
