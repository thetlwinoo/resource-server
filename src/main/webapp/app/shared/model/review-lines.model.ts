export interface IReviewLines {
    id?: number;
    productRating?: number;
    productReview?: any;
    sellerRating?: number;
    sellerReview?: any;
    deliveryRating?: number;
    deliveryReview?: any;
    photoContentType?: string;
    photo?: any;
    stockItemId?: number;
    reviewId?: number;
}

export class ReviewLines implements IReviewLines {
    constructor(
        public id?: number,
        public productRating?: number,
        public productReview?: any,
        public sellerRating?: number,
        public sellerReview?: any,
        public deliveryRating?: number,
        public deliveryReview?: any,
        public photoContentType?: string,
        public photo?: any,
        public stockItemId?: number,
        public reviewId?: number
    ) {}
}
