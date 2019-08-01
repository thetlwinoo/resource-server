import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInvoiceLines } from 'app/shared/model/invoice-lines.model';

type EntityResponseType = HttpResponse<IInvoiceLines>;
type EntityArrayResponseType = HttpResponse<IInvoiceLines[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceLinesService {
    public resourceUrl = SERVER_API_URL + 'api/invoice-lines';

    constructor(protected http: HttpClient) {}

    create(invoiceLines: IInvoiceLines): Observable<EntityResponseType> {
        return this.http.post<IInvoiceLines>(this.resourceUrl, invoiceLines, { observe: 'response' });
    }

    update(invoiceLines: IInvoiceLines): Observable<EntityResponseType> {
        return this.http.put<IInvoiceLines>(this.resourceUrl, invoiceLines, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IInvoiceLines>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInvoiceLines[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
