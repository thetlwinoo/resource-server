import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILastestMerchantUploadedDocument } from 'app/shared/model/lastest-merchant-uploaded-document.model';

type EntityResponseType = HttpResponse<ILastestMerchantUploadedDocument>;
type EntityArrayResponseType = HttpResponse<ILastestMerchantUploadedDocument[]>;

@Injectable({ providedIn: 'root' })
export class LastestMerchantUploadedDocumentService {
    public resourceUrl = SERVER_API_URL + 'api/lastest-merchant-uploaded-documents';

    constructor(protected http: HttpClient) {}

    create(lastestMerchantUploadedDocument: ILastestMerchantUploadedDocument): Observable<EntityResponseType> {
        return this.http.post<ILastestMerchantUploadedDocument>(this.resourceUrl, lastestMerchantUploadedDocument, { observe: 'response' });
    }

    update(lastestMerchantUploadedDocument: ILastestMerchantUploadedDocument): Observable<EntityResponseType> {
        return this.http.put<ILastestMerchantUploadedDocument>(this.resourceUrl, lastestMerchantUploadedDocument, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILastestMerchantUploadedDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILastestMerchantUploadedDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
