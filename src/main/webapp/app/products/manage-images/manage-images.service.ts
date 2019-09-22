import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProducts } from 'app/shared/model/products.model';

type EntityResponseType = HttpResponse<IProducts>;
type EntityArrayResponseType = HttpResponse<IProducts[]>;

@Injectable({ providedIn: 'root' })
export class ManageImagesService {
    public resourceUrl = SERVER_API_URL + 'api/products';
    public uploadUrl = SERVER_API_URL + 'api/products/upload';

    constructor(protected http: HttpClient) {}

    upload(file: any): Observable<any> {
        console.log('upload path', file);
        const formData = new FormData();
        formData.append('file', file);
        return this.http.post<any>(this.uploadUrl, formData, { observe: 'response' }).pipe(map((res: any) => res));
    }

    create(products: IProducts): Observable<EntityResponseType> {
        return this.http.post<IProducts>(this.resourceUrl, products, { observe: 'response' });
    }

    update(products: IProducts): Observable<EntityResponseType> {
        return this.http.put<IProducts>(this.resourceUrl, products, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProducts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProducts[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
