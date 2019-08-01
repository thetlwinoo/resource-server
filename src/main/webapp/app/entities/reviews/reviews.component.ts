import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReviews } from 'app/shared/model/reviews.model';
import { AccountService } from 'app/core';
import { ReviewsService } from './reviews.service';

@Component({
    selector: 'jhi-reviews',
    templateUrl: './reviews.component.html'
})
export class ReviewsComponent implements OnInit, OnDestroy {
    reviews: IReviews[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected reviewsService: ReviewsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.reviewsService
            .query()
            .pipe(
                filter((res: HttpResponse<IReviews[]>) => res.ok),
                map((res: HttpResponse<IReviews[]>) => res.body)
            )
            .subscribe(
                (res: IReviews[]) => {
                    this.reviews = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInReviews();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReviews) {
        return item.id;
    }

    registerChangeInReviews() {
        this.eventSubscriber = this.eventManager.subscribe('reviewsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
