import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBusinessEntityContact } from 'app/shared/model/business-entity-contact.model';
import { AccountService } from 'app/core';
import { BusinessEntityContactService } from './business-entity-contact.service';

@Component({
    selector: 'jhi-business-entity-contact',
    templateUrl: './business-entity-contact.component.html'
})
export class BusinessEntityContactComponent implements OnInit, OnDestroy {
    businessEntityContacts: IBusinessEntityContact[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected businessEntityContactService: BusinessEntityContactService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.businessEntityContactService
            .query()
            .pipe(
                filter((res: HttpResponse<IBusinessEntityContact[]>) => res.ok),
                map((res: HttpResponse<IBusinessEntityContact[]>) => res.body)
            )
            .subscribe(
                (res: IBusinessEntityContact[]) => {
                    this.businessEntityContacts = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBusinessEntityContacts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBusinessEntityContact) {
        return item.id;
    }

    registerChangeInBusinessEntityContacts() {
        this.eventSubscriber = this.eventManager.subscribe('businessEntityContactListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
