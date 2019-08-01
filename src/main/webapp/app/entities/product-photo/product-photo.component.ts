import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductPhoto } from 'app/shared/model/product-photo.model';
import { AccountService } from 'app/core';
import { ProductPhotoService } from './product-photo.service';

@Component({
    selector: 'jhi-product-photo',
    templateUrl: './product-photo.component.html'
})
export class ProductPhotoComponent implements OnInit, OnDestroy {
    productPhotos: IProductPhoto[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productPhotoService: ProductPhotoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productPhotoService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductPhoto[]>) => res.ok),
                map((res: HttpResponse<IProductPhoto[]>) => res.body)
            )
            .subscribe(
                (res: IProductPhoto[]) => {
                    this.productPhotos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductPhotos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductPhoto) {
        return item.id;
    }

    registerChangeInProductPhotos() {
        this.eventSubscriber = this.eventManager.subscribe('productPhotoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
