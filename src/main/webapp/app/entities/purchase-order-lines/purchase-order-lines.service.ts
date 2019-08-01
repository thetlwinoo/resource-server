import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';

type EntityResponseType = HttpResponse<IPurchaseOrderLines>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrderLines[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrderLinesService {
    public resourceUrl = SERVER_API_URL + 'api/purchase-order-lines';

    constructor(protected http: HttpClient) {}

    create(purchaseOrderLines: IPurchaseOrderLines): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseOrderLines);
        return this.http
            .post<IPurchaseOrderLines>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(purchaseOrderLines: IPurchaseOrderLines): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseOrderLines);
        return this.http
            .put<IPurchaseOrderLines>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPurchaseOrderLines>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseOrderLines[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(purchaseOrderLines: IPurchaseOrderLines): IPurchaseOrderLines {
        const copy: IPurchaseOrderLines = Object.assign({}, purchaseOrderLines, {
            lastReceiptDate:
                purchaseOrderLines.lastReceiptDate != null && purchaseOrderLines.lastReceiptDate.isValid()
                    ? purchaseOrderLines.lastReceiptDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.lastReceiptDate = res.body.lastReceiptDate != null ? moment(res.body.lastReceiptDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((purchaseOrderLines: IPurchaseOrderLines) => {
                purchaseOrderLines.lastReceiptDate =
                    purchaseOrderLines.lastReceiptDate != null ? moment(purchaseOrderLines.lastReceiptDate) : null;
            });
        }
        return res;
    }
}
