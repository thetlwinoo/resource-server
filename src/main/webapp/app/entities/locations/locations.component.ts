import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILocations } from 'app/shared/model/locations.model';
import { AccountService } from 'app/core';
import { LocationsService } from './locations.service';

@Component({
    selector: 'jhi-locations',
    templateUrl: './locations.component.html'
})
export class LocationsComponent implements OnInit, OnDestroy {
    locations: ILocations[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected locationsService: LocationsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.locationsService
            .query()
            .pipe(
                filter((res: HttpResponse<ILocations[]>) => res.ok),
                map((res: HttpResponse<ILocations[]>) => res.body)
            )
            .subscribe(
                (res: ILocations[]) => {
                    this.locations = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLocations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILocations) {
        return item.id;
    }

    registerChangeInLocations() {
        this.eventSubscriber = this.eventManager.subscribe('locationsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
