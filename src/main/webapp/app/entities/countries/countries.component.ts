import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICountries } from 'app/shared/model/countries.model';
import { AccountService } from 'app/core';
import { CountriesService } from './countries.service';

@Component({
    selector: 'jhi-countries',
    templateUrl: './countries.component.html'
})
export class CountriesComponent implements OnInit, OnDestroy {
    countries: ICountries[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected countriesService: CountriesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.countriesService
            .query()
            .pipe(
                filter((res: HttpResponse<ICountries[]>) => res.ok),
                map((res: HttpResponse<ICountries[]>) => res.body)
            )
            .subscribe(
                (res: ICountries[]) => {
                    this.countries = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCountries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICountries) {
        return item.id;
    }

    registerChangeInCountries() {
        this.eventSubscriber = this.eventManager.subscribe('countriesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
