import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductSubCategory } from 'app/shared/model/product-sub-category.model';

type EntityResponseType = HttpResponse<IProductSubCategory>;
type EntityArrayResponseType = HttpResponse<IProductSubCategory[]>;

@Injectable({ providedIn: 'root' })
export class ProductSubCategoryService {
    public resourceUrl = SERVER_API_URL + 'api/product-sub-categories';

    constructor(protected http: HttpClient) {}

    create(productSubCategory: IProductSubCategory): Observable<EntityResponseType> {
        return this.http.post<IProductSubCategory>(this.resourceUrl, productSubCategory, { observe: 'response' });
    }

    update(productSubCategory: IProductSubCategory): Observable<EntityResponseType> {
        return this.http.put<IProductSubCategory>(this.resourceUrl, productSubCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductSubCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductSubCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
