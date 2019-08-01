import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockItemStockGroups } from 'app/shared/model/stock-item-stock-groups.model';

type EntityResponseType = HttpResponse<IStockItemStockGroups>;
type EntityArrayResponseType = HttpResponse<IStockItemStockGroups[]>;

@Injectable({ providedIn: 'root' })
export class StockItemStockGroupsService {
    public resourceUrl = SERVER_API_URL + 'api/stock-item-stock-groups';

    constructor(protected http: HttpClient) {}

    create(stockItemStockGroups: IStockItemStockGroups): Observable<EntityResponseType> {
        return this.http.post<IStockItemStockGroups>(this.resourceUrl, stockItemStockGroups, { observe: 'response' });
    }

    update(stockItemStockGroups: IStockItemStockGroups): Observable<EntityResponseType> {
        return this.http.put<IStockItemStockGroups>(this.resourceUrl, stockItemStockGroups, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStockItemStockGroups>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStockItemStockGroups[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
