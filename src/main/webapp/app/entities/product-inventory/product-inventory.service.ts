import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductInventory } from 'app/shared/model/product-inventory.model';

type EntityResponseType = HttpResponse<IProductInventory>;
type EntityArrayResponseType = HttpResponse<IProductInventory[]>;

@Injectable({ providedIn: 'root' })
export class ProductInventoryService {
    public resourceUrl = SERVER_API_URL + 'api/product-inventories';

    constructor(protected http: HttpClient) {}

    create(productInventory: IProductInventory): Observable<EntityResponseType> {
        return this.http.post<IProductInventory>(this.resourceUrl, productInventory, { observe: 'response' });
    }

    update(productInventory: IProductInventory): Observable<EntityResponseType> {
        return this.http.put<IProductInventory>(this.resourceUrl, productInventory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductInventory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductInventory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
