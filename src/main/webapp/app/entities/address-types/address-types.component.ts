import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AccountService } from 'app/core';
import { AddressTypesService } from './address-types.service';

@Component({
    selector: 'jhi-address-types',
    templateUrl: './address-types.component.html'
})
export class AddressTypesComponent implements OnInit, OnDestroy {
    addressTypes: IAddressTypes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected addressTypesService: AddressTypesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.addressTypesService
            .query()
            .pipe(
                filter((res: HttpResponse<IAddressTypes[]>) => res.ok),
                map((res: HttpResponse<IAddressTypes[]>) => res.body)
            )
            .subscribe(
                (res: IAddressTypes[]) => {
                    this.addressTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAddressTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAddressTypes) {
        return item.id;
    }

    registerChangeInAddressTypes() {
        this.eventSubscriber = this.eventManager.subscribe('addressTypesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
