import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from './reviews.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders';

@Component({
    selector: 'jhi-reviews-update',
    templateUrl: './reviews-update.component.html'
})
export class ReviewsUpdateComponent implements OnInit {
    reviews: IReviews;
    isSaving: boolean;

    orders: IOrders[];
    reviewDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected reviewsService: ReviewsService,
        protected ordersService: OrdersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reviews }) => {
            this.reviews = reviews;
        });
        this.ordersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOrders[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOrders[]>) => response.body)
            )
            .subscribe((res: IOrders[]) => (this.orders = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reviews.id !== undefined) {
            this.subscribeToSaveResponse(this.reviewsService.update(this.reviews));
        } else {
            this.subscribeToSaveResponse(this.reviewsService.create(this.reviews));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReviews>>) {
        result.subscribe((res: HttpResponse<IReviews>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackOrdersById(index: number, item: IOrders) {
        return item.id;
    }
}
