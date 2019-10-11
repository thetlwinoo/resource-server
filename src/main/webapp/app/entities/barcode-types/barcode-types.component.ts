import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBarcodeTypes } from 'app/shared/model/barcode-types.model';
import { AccountService } from 'app/core';
import { BarcodeTypesService } from './barcode-types.service';

@Component({
    selector: 'jhi-barcode-types',
    templateUrl: './barcode-types.component.html'
})
export class BarcodeTypesComponent implements OnInit, OnDestroy {
    barcodeTypes: IBarcodeTypes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected barcodeTypesService: BarcodeTypesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.barcodeTypesService
            .query()
            .pipe(
                filter((res: HttpResponse<IBarcodeTypes[]>) => res.ok),
                map((res: HttpResponse<IBarcodeTypes[]>) => res.body)
            )
            .subscribe(
                (res: IBarcodeTypes[]) => {
                    this.barcodeTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBarcodeTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBarcodeTypes) {
        return item.id;
    }

    registerChangeInBarcodeTypes() {
        this.eventSubscriber = this.eventManager.subscribe('barcodeTypesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
