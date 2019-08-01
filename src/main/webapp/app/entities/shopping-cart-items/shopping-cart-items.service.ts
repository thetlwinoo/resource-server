import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';

type EntityResponseType = HttpResponse<IShoppingCartItems>;
type EntityArrayResponseType = HttpResponse<IShoppingCartItems[]>;

@Injectable({ providedIn: 'root' })
export class ShoppingCartItemsService {
    public resourceUrl = SERVER_API_URL + 'api/shopping-cart-items';

    constructor(protected http: HttpClient) {}

    create(shoppingCartItems: IShoppingCartItems): Observable<EntityResponseType> {
        return this.http.post<IShoppingCartItems>(this.resourceUrl, shoppingCartItems, { observe: 'response' });
    }

    update(shoppingCartItems: IShoppingCartItems): Observable<EntityResponseType> {
        return this.http.put<IShoppingCartItems>(this.resourceUrl, shoppingCartItems, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IShoppingCartItems>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IShoppingCartItems[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
