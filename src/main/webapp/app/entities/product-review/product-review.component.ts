import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductReview } from 'app/shared/model/product-review.model';
import { AccountService } from 'app/core';
import { ProductReviewService } from './product-review.service';

@Component({
    selector: 'jhi-product-review',
    templateUrl: './product-review.component.html'
})
export class ProductReviewComponent implements OnInit, OnDestroy {
    productReviews: IProductReview[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productReviewService: ProductReviewService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productReviewService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductReview[]>) => res.ok),
                map((res: HttpResponse<IProductReview[]>) => res.body)
            )
            .subscribe(
                (res: IProductReview[]) => {
                    this.productReviews = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductReviews();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductReview) {
        return item.id;
    }

    registerChangeInProductReviews() {
        this.eventSubscriber = this.eventManager.subscribe('productReviewListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
