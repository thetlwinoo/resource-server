import { Moment } from 'moment';
import { IReviewLines } from 'app/shared/model/review-lines.model';

export interface IReviews {
    id?: number;
    reviewerName?: string;
    emailAddress?: string;
    reviewDate?: Moment;
    overAllSellerRating?: number;
    overAllSellerReview?: string;
    overAllDeliveryRating?: number;
    overAllDeliveryReview?: string;
    reviewAsAnonymous?: boolean;
    completedReview?: boolean;
    reviewLineLists?: IReviewLines[];
    reviewId?: number;
}

export class Reviews implements IReviews {
    constructor(
        public id?: number,
        public reviewerName?: string,
        public emailAddress?: string,
        public reviewDate?: Moment,
        public overAllSellerRating?: number,
        public overAllSellerReview?: string,
        public overAllDeliveryRating?: number,
        public overAllDeliveryReview?: string,
        public reviewAsAnonymous?: boolean,
        public completedReview?: boolean,
        public reviewLineLists?: IReviewLines[],
        public reviewId?: number
    ) {
        this.reviewAsAnonymous = this.reviewAsAnonymous || false;
        this.completedReview = this.completedReview || false;
    }
}
