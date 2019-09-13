import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductModelDescription } from 'app/shared/model/product-model-description.model';

type EntityResponseType = HttpResponse<IProductModelDescription>;
type EntityArrayResponseType = HttpResponse<IProductModelDescription[]>;

@Injectable({ providedIn: 'root' })
export class ProductModelDescriptionService {
    public resourceUrl = SERVER_API_URL + 'api/product-model-descriptions';

    constructor(protected http: HttpClient) {}

    create(productModelDescription: IProductModelDescription): Observable<EntityResponseType> {
        return this.http.post<IProductModelDescription>(this.resourceUrl, productModelDescription, { observe: 'response' });
    }

    update(productModelDescription: IProductModelDescription): Observable<EntityResponseType> {
        return this.http.put<IProductModelDescription>(this.resourceUrl, productModelDescription, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductModelDescription>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductModelDescription[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
