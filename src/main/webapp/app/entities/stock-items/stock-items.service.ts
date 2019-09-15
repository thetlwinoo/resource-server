import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockItems } from 'app/shared/model/stock-items.model';

type EntityResponseType = HttpResponse<IStockItems>;
type EntityArrayResponseType = HttpResponse<IStockItems[]>;

@Injectable({ providedIn: 'root' })
export class StockItemsService {
    public resourceUrl = SERVER_API_URL + 'api/stock-items';

    constructor(protected http: HttpClient) {}

    create(stockItems: IStockItems): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockItems);
        return this.http
            .post<IStockItems>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(stockItems: IStockItems): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockItems);
        return this.http
            .put<IStockItems>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStockItems>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStockItems[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(stockItems: IStockItems): IStockItems {
        const copy: IStockItems = Object.assign({}, stockItems, {
            discontinuedDate:
                stockItems.discontinuedDate != null && stockItems.discontinuedDate.isValid()
                    ? stockItems.discontinuedDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.discontinuedDate = res.body.discontinuedDate != null ? moment(res.body.discontinuedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((stockItems: IStockItems) => {
                stockItems.discontinuedDate = stockItems.discontinuedDate != null ? moment(stockItems.discontinuedDate) : null;
            });
        }
        return res;
    }
}