import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';
import { AccountService } from 'app/core';
import { SupplierCategoriesService } from './supplier-categories.service';

@Component({
    selector: 'jhi-supplier-categories',
    templateUrl: './supplier-categories.component.html'
})
export class SupplierCategoriesComponent implements OnInit, OnDestroy {
    supplierCategories: ISupplierCategories[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected supplierCategoriesService: SupplierCategoriesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.supplierCategoriesService
            .query()
            .pipe(
                filter((res: HttpResponse<ISupplierCategories[]>) => res.ok),
                map((res: HttpResponse<ISupplierCategories[]>) => res.body)
            )
            .subscribe(
                (res: ISupplierCategories[]) => {
                    this.supplierCategories = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSupplierCategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISupplierCategories) {
        return item.id;
    }

    registerChangeInSupplierCategories() {
        this.eventSubscriber = this.eventManager.subscribe('supplierCategoriesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
