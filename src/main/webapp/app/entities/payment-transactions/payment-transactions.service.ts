import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';

type EntityResponseType = HttpResponse<IPaymentTransactions>;
type EntityArrayResponseType = HttpResponse<IPaymentTransactions[]>;

@Injectable({ providedIn: 'root' })
export class PaymentTransactionsService {
    public resourceUrl = SERVER_API_URL + 'api/payment-transactions';

    constructor(protected http: HttpClient) {}

    create(paymentTransactions: IPaymentTransactions): Observable<EntityResponseType> {
        return this.http.post<IPaymentTransactions>(this.resourceUrl, paymentTransactions, { observe: 'response' });
    }

    update(paymentTransactions: IPaymentTransactions): Observable<EntityResponseType> {
        return this.http.put<IPaymentTransactions>(this.resourceUrl, paymentTransactions, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPaymentTransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPaymentTransactions[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
