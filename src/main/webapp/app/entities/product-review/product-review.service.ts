import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductReview } from 'app/shared/model/product-review.model';

type EntityResponseType = HttpResponse<IProductReview>;
type EntityArrayResponseType = HttpResponse<IProductReview[]>;

@Injectable({ providedIn: 'root' })
export class ProductReviewService {
    public resourceUrl = SERVER_API_URL + 'api/product-reviews';

    constructor(protected http: HttpClient) {}

    create(productReview: IProductReview): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(productReview);
        return this.http
            .post<IProductReview>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(productReview: IProductReview): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(productReview);
        return this.http
            .put<IProductReview>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProductReview>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProductReview[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(productReview: IProductReview): IProductReview {
        const copy: IProductReview = Object.assign({}, productReview, {
            reviewDate:
                productReview.reviewDate != null && productReview.reviewDate.isValid() ? productReview.reviewDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.reviewDate = res.body.reviewDate != null ? moment(res.body.reviewDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((productReview: IProductReview) => {
                productReview.reviewDate = productReview.reviewDate != null ? moment(productReview.reviewDate) : null;
            });
        }
        return res;
    }
}
