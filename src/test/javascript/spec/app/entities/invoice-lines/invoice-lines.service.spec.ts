/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { InvoiceLinesService } from 'app/entities/invoice-lines/invoice-lines.service';
import { IInvoiceLines, InvoiceLines } from 'app/shared/model/invoice-lines.model';

describe('Service Tests', () => {
    describe('InvoiceLines Service', () => {
        let injector: TestBed;
        let service: InvoiceLinesService;
        let httpMock: HttpTestingController;
        let elemDefault: IInvoiceLines;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(InvoiceLinesService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new InvoiceLines(0, 'AAAAAAA', 0, 0, 0, 0, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a InvoiceLines', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new InvoiceLines(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a InvoiceLines', async () => {
                const returnedFromService = Object.assign(
                    {
                        description: 'BBBBBB',
                        quantity: 1,
                        unitPrice: 1,
                        taxRate: 1,
                        taxAmount: 1,
                        lineProfit: 1,
                        extendedPrice: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of InvoiceLines', async () => {
                const returnedFromService = Object.assign(
                    {
                        description: 'BBBBBB',
                        quantity: 1,
                        unitPrice: 1,
                        taxRate: 1,
                        taxAmount: 1,
                        lineProfit: 1,
                        extendedPrice: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a InvoiceLines', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
