import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ILastestMerchantUploadedDocument } from 'app/shared/model/lastest-merchant-uploaded-document.model';
import { AccountService } from 'app/core';
import { LastestMerchantUploadedDocumentService } from './lastest-merchant-uploaded-document.service';

@Component({
    selector: 'jhi-lastest-merchant-uploaded-document',
    templateUrl: './lastest-merchant-uploaded-document.component.html'
})
export class LastestMerchantUploadedDocumentComponent implements OnInit, OnDestroy {
    lastestMerchantUploadedDocuments: ILastestMerchantUploadedDocument[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected lastestMerchantUploadedDocumentService: LastestMerchantUploadedDocumentService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.lastestMerchantUploadedDocumentService
            .query()
            .pipe(
                filter((res: HttpResponse<ILastestMerchantUploadedDocument[]>) => res.ok),
                map((res: HttpResponse<ILastestMerchantUploadedDocument[]>) => res.body)
            )
            .subscribe(
                (res: ILastestMerchantUploadedDocument[]) => {
                    this.lastestMerchantUploadedDocuments = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLastestMerchantUploadedDocuments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILastestMerchantUploadedDocument) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInLastestMerchantUploadedDocuments() {
        this.eventSubscriber = this.eventManager.subscribe('lastestMerchantUploadedDocumentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
