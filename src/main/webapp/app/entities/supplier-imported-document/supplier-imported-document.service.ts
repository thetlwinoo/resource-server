import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';

type EntityResponseType = HttpResponse<ISupplierImportedDocument>;
type EntityArrayResponseType = HttpResponse<ISupplierImportedDocument[]>;

@Injectable({ providedIn: 'root' })
export class SupplierImportedDocumentService {
    public resourceUrl = SERVER_API_URL + 'api/supplier-imported-documents';

    constructor(protected http: HttpClient) {}

    create(supplierImportedDocument: ISupplierImportedDocument): Observable<EntityResponseType> {
        return this.http.post<ISupplierImportedDocument>(this.resourceUrl, supplierImportedDocument, { observe: 'response' });
    }

    update(supplierImportedDocument: ISupplierImportedDocument): Observable<EntityResponseType> {
        return this.http.put<ISupplierImportedDocument>(this.resourceUrl, supplierImportedDocument, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISupplierImportedDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISupplierImportedDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
