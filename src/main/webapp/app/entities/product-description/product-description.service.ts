import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductDescription } from 'app/shared/model/product-description.model';

type EntityResponseType = HttpResponse<IProductDescription>;
type EntityArrayResponseType = HttpResponse<IProductDescription[]>;

@Injectable({ providedIn: 'root' })
export class ProductDescriptionService {
    public resourceUrl = SERVER_API_URL + 'api/product-descriptions';

    constructor(protected http: HttpClient) {}

    create(productDescription: IProductDescription): Observable<EntityResponseType> {
        return this.http.post<IProductDescription>(this.resourceUrl, productDescription, { observe: 'response' });
    }

    update(productDescription: IProductDescription): Observable<EntityResponseType> {
        return this.http.put<IProductDescription>(this.resourceUrl, productDescription, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductDescription>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductDescription[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
