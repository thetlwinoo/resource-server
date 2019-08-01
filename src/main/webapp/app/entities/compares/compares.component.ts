import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompares } from 'app/shared/model/compares.model';
import { AccountService } from 'app/core';
import { ComparesService } from './compares.service';

@Component({
    selector: 'jhi-compares',
    templateUrl: './compares.component.html'
})
export class ComparesComponent implements OnInit, OnDestroy {
    compares: ICompares[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected comparesService: ComparesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.comparesService
            .query()
            .pipe(
                filter((res: HttpResponse<ICompares[]>) => res.ok),
                map((res: HttpResponse<ICompares[]>) => res.body)
            )
            .subscribe(
                (res: ICompares[]) => {
                    this.compares = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCompares();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICompares) {
        return item.id;
    }

    registerChangeInCompares() {
        this.eventSubscriber = this.eventManager.subscribe('comparesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
