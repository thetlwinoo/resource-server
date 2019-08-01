import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReviewLines } from 'app/shared/model/review-lines.model';

type EntityResponseType = HttpResponse<IReviewLines>;
type EntityArrayResponseType = HttpResponse<IReviewLines[]>;

@Injectable({ providedIn: 'root' })
export class ReviewLinesService {
    public resourceUrl = SERVER_API_URL + 'api/review-lines';

    constructor(protected http: HttpClient) {}

    create(reviewLines: IReviewLines): Observable<EntityResponseType> {
        return this.http.post<IReviewLines>(this.resourceUrl, reviewLines, { observe: 'response' });
    }

    update(reviewLines: IReviewLines): Observable<EntityResponseType> {
        return this.http.put<IReviewLines>(this.resourceUrl, reviewLines, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReviewLines>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReviewLines[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
