import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';

type EntityResponseType = HttpResponse<IUploadTransactions>;
type EntityArrayResponseType = HttpResponse<IUploadTransactions[]>;

@Injectable({ providedIn: 'root' })
export class UploadTransactionsService {
    public resourceUrl = SERVER_API_URL + 'api/upload-transactions';

    constructor(protected http: HttpClient) {}

    create(uploadTransactions: IUploadTransactions): Observable<EntityResponseType> {
        return this.http.post<IUploadTransactions>(this.resourceUrl, uploadTransactions, { observe: 'response' });
    }

    update(uploadTransactions: IUploadTransactions): Observable<EntityResponseType> {
        return this.http.put<IUploadTransactions>(this.resourceUrl, uploadTransactions, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUploadTransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUploadTransactions[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
