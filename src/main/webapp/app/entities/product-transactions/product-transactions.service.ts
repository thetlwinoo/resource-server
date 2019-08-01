import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductTransactions } from 'app/shared/model/product-transactions.model';

type EntityResponseType = HttpResponse<IProductTransactions>;
type EntityArrayResponseType = HttpResponse<IProductTransactions[]>;

@Injectable({ providedIn: 'root' })
export class ProductTransactionsService {
    public resourceUrl = SERVER_API_URL + 'api/product-transactions';

    constructor(protected http: HttpClient) {}

    create(productTransactions: IProductTransactions): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(productTransactions);
        return this.http
            .post<IProductTransactions>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(productTransactions: IProductTransactions): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(productTransactions);
        return this.http
            .put<IProductTransactions>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProductTransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProductTransactions[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(productTransactions: IProductTransactions): IProductTransactions {
        const copy: IProductTransactions = Object.assign({}, productTransactions, {
            transactionOccuredWhen:
                productTransactions.transactionOccuredWhen != null && productTransactions.transactionOccuredWhen.isValid()
                    ? productTransactions.transactionOccuredWhen.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.transactionOccuredWhen = res.body.transactionOccuredWhen != null ? moment(res.body.transactionOccuredWhen) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((productTransactions: IProductTransactions) => {
                productTransactions.transactionOccuredWhen =
                    productTransactions.transactionOccuredWhen != null ? moment(productTransactions.transactionOccuredWhen) : null;
            });
        }
        return res;
    }
}
