import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProducts } from 'app/shared/model/products.model';

type EntityResponseType = HttpResponse<IProducts>;
type EntityArrayResponseType = HttpResponse<IProducts[]>;

@Injectable({ providedIn: 'root' })
export class ManageImagesService {
    public resourceUrl = SERVER_API_URL + 'api/products';
    public uploadUrl = SERVER_API_URL + 'api/products/upload';

    constructor(protected http: HttpClient) {}

    create(products: IProducts): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(products);
        return this.http
            .post<IProducts>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    upload(file: any): Observable<any> {
        console.log('upload path', file);
        const formData = new FormData();
        formData.append('file', file);
        return this.http.post<any>(this.uploadUrl, formData, { observe: 'response' }).pipe(map((res: any) => res));
    }

    update(products: IProducts): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(products);
        return this.http
            .put<IProducts>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProducts>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProducts[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(products: IProducts): IProducts {
        const copy: IProducts = Object.assign({}, products, {
            sellStartDate:
                products.sellStartDate != null && products.sellStartDate.isValid() ? products.sellStartDate.format(DATE_FORMAT) : null,
            sellEndDate: products.sellEndDate != null && products.sellEndDate.isValid() ? products.sellEndDate.format(DATE_FORMAT) : null,
            discontinuedDate:
                products.discontinuedDate != null && products.discontinuedDate.isValid()
                    ? products.discontinuedDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.sellStartDate = res.body.sellStartDate != null ? moment(res.body.sellStartDate) : null;
            res.body.sellEndDate = res.body.sellEndDate != null ? moment(res.body.sellEndDate) : null;
            res.body.discontinuedDate = res.body.discontinuedDate != null ? moment(res.body.discontinuedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((products: IProducts) => {
                products.sellStartDate = products.sellStartDate != null ? moment(products.sellStartDate) : null;
                products.sellEndDate = products.sellEndDate != null ? moment(products.sellEndDate) : null;
                products.discontinuedDate = products.discontinuedDate != null ? moment(products.discontinuedDate) : null;
            });
        }
        return res;
    }
}
