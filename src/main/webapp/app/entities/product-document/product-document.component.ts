import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IProductDocument } from 'app/shared/model/product-document.model';
import { AccountService } from 'app/core';
import { ProductDocumentService } from './product-document.service';

@Component({
    selector: 'jhi-product-document',
    templateUrl: './product-document.component.html'
})
export class ProductDocumentComponent implements OnInit, OnDestroy {
    productDocuments: IProductDocument[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productDocumentService: ProductDocumentService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productDocumentService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductDocument[]>) => res.ok),
                map((res: HttpResponse<IProductDocument[]>) => res.body)
            )
            .subscribe(
                (res: IProductDocument[]) => {
                    this.productDocuments = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductDocuments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductDocument) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInProductDocuments() {
        this.eventSubscriber = this.eventManager.subscribe('productDocumentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
