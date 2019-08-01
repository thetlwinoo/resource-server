import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';

type EntityResponseType = HttpResponse<IShoppingCarts>;
type EntityArrayResponseType = HttpResponse<IShoppingCarts[]>;

@Injectable({ providedIn: 'root' })
export class ShoppingCartsService {
    public resourceUrl = SERVER_API_URL + 'api/shopping-carts';

    constructor(protected http: HttpClient) {}

    create(shoppingCarts: IShoppingCarts): Observable<EntityResponseType> {
        return this.http.post<IShoppingCarts>(this.resourceUrl, shoppingCarts, { observe: 'response' });
    }

    update(shoppingCarts: IShoppingCarts): Observable<EntityResponseType> {
        return this.http.put<IShoppingCarts>(this.resourceUrl, shoppingCarts, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IShoppingCarts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IShoppingCarts[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
