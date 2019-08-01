import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IReviewLines } from 'app/shared/model/review-lines.model';
import { ReviewLinesService } from './review-lines.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';
import { IReviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from 'app/entities/reviews';

@Component({
    selector: 'jhi-review-lines-update',
    templateUrl: './review-lines-update.component.html'
})
export class ReviewLinesUpdateComponent implements OnInit {
    reviewLines: IReviewLines;
    isSaving: boolean;

    products: IProducts[];

    reviews: IReviews[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected reviewLinesService: ReviewLinesService,
        protected productsService: ProductsService,
        protected reviewsService: ReviewsService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reviewLines }) => {
            this.reviewLines = reviewLines;
        });
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.reviewsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IReviews[]>) => mayBeOk.ok),
                map((response: HttpResponse<IReviews[]>) => response.body)
            )
            .subscribe((res: IReviews[]) => (this.reviews = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.reviewLines, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reviewLines.id !== undefined) {
            this.subscribeToSaveResponse(this.reviewLinesService.update(this.reviewLines));
        } else {
            this.subscribeToSaveResponse(this.reviewLinesService.create(this.reviewLines));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReviewLines>>) {
        result.subscribe((res: HttpResponse<IReviewLines>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }

    trackReviewsById(index: number, item: IReviews) {
        return item.id;
    }
}
