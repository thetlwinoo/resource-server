import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISystemParameters } from 'app/shared/model/system-parameters.model';
import { AccountService } from 'app/core';
import { SystemParametersService } from './system-parameters.service';

@Component({
    selector: 'jhi-system-parameters',
    templateUrl: './system-parameters.component.html'
})
export class SystemParametersComponent implements OnInit, OnDestroy {
    systemParameters: ISystemParameters[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected systemParametersService: SystemParametersService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.systemParametersService
            .query()
            .pipe(
                filter((res: HttpResponse<ISystemParameters[]>) => res.ok),
                map((res: HttpResponse<ISystemParameters[]>) => res.body)
            )
            .subscribe(
                (res: ISystemParameters[]) => {
                    this.systemParameters = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSystemParameters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISystemParameters) {
        return item.id;
    }

    registerChangeInSystemParameters() {
        this.eventSubscriber = this.eventManager.subscribe('systemParametersListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
