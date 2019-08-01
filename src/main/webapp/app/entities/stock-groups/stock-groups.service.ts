import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockGroups } from 'app/shared/model/stock-groups.model';

type EntityResponseType = HttpResponse<IStockGroups>;
type EntityArrayResponseType = HttpResponse<IStockGroups[]>;

@Injectable({ providedIn: 'root' })
export class StockGroupsService {
    public resourceUrl = SERVER_API_URL + 'api/stock-groups';

    constructor(protected http: HttpClient) {}

    create(stockGroups: IStockGroups): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockGroups);
        return this.http
            .post<IStockGroups>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(stockGroups: IStockGroups): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockGroups);
        return this.http
            .put<IStockGroups>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStockGroups>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStockGroups[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(stockGroups: IStockGroups): IStockGroups {
        const copy: IStockGroups = Object.assign({}, stockGroups, {
            validFrom: stockGroups.validFrom != null && stockGroups.validFrom.isValid() ? stockGroups.validFrom.format(DATE_FORMAT) : null,
            validTo: stockGroups.validTo != null && stockGroups.validTo.isValid() ? stockGroups.validTo.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.validFrom = res.body.validFrom != null ? moment(res.body.validFrom) : null;
            res.body.validTo = res.body.validTo != null ? moment(res.body.validTo) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((stockGroups: IStockGroups) => {
                stockGroups.validFrom = stockGroups.validFrom != null ? moment(stockGroups.validFrom) : null;
                stockGroups.validTo = stockGroups.validTo != null ? moment(stockGroups.validTo) : null;
            });
        }
        return res;
    }
}
