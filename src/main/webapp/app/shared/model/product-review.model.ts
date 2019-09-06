import { Moment } from 'moment';

export interface IProductReview {
    id?: number;
    reviewerName?: string;
    reviewDate?: Moment;
    emailAddress?: string;
    rating?: number;
    comments?: string;
    productProductName?: string;
    productId?: number;
}

export class ProductReview implements IProductReview {
    constructor(
        public id?: number,
        public reviewerName?: string,
        public reviewDate?: Moment,
        public emailAddress?: string,
        public rating?: number,
        public comments?: string,
        public productProductName?: string,
        public productId?: number
    ) {}
}
