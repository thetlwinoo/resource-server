import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductPhoto } from 'app/shared/model/product-photo.model';
import { IProducts } from 'app/shared/model/products.model';

type EntityResponseType = HttpResponse<IProductPhoto>;
type EntityArrayResponseType = HttpResponse<IProductPhoto[]>;

@Injectable({ providedIn: 'root' })
export class ProductPhotoExtendService {
    public resourceUrl = SERVER_API_URL + 'api/product-photo-extend';

    constructor(protected http: HttpClient) {}

    setDefault(productPhoto: IProductPhoto): Observable<EntityResponseType> {
        const copy = Object.assign({}, productPhoto);
        return this.http.post<IProductPhoto>(this.resourceUrl + '/default', copy, { observe: 'response' });
    }
}
