import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReviews } from 'app/shared/model/reviews.model';

@Component({
    selector: 'jhi-reviews-detail',
    templateUrl: './reviews-detail.component.html'
})
export class ReviewsDetailComponent implements OnInit {
    reviews: IReviews;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reviews }) => {
            this.reviews = reviews;
        });
    }

    previousState() {
        window.history.back();
    }
}
