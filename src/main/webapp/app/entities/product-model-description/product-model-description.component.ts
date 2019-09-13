import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductModelDescription } from 'app/shared/model/product-model-description.model';
import { AccountService } from 'app/core';
import { ProductModelDescriptionService } from './product-model-description.service';

@Component({
    selector: 'jhi-product-model-description',
    templateUrl: './product-model-description.component.html'
})
export class ProductModelDescriptionComponent implements OnInit, OnDestroy {
    productModelDescriptions: IProductModelDescription[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productModelDescriptionService: ProductModelDescriptionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productModelDescriptionService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductModelDescription[]>) => res.ok),
                map((res: HttpResponse<IProductModelDescription[]>) => res.body)
            )
            .subscribe(
                (res: IProductModelDescription[]) => {
                    this.productModelDescriptions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductModelDescriptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductModelDescription) {
        return item.id;
    }

    registerChangeInProductModelDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('productModelDescriptionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
