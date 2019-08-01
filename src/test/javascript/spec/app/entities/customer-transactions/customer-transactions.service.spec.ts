/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CustomerTransactionsService } from 'app/entities/customer-transactions/customer-transactions.service';
import { ICustomerTransactions, CustomerTransactions } from 'app/shared/model/customer-transactions.model';

describe('Service Tests', () => {
    describe('CustomerTransactions Service', () => {
        let injector: TestBed;
        let service: CustomerTransactionsService;
        let httpMock: HttpTestingController;
        let elemDefault: ICustomerTransactions;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CustomerTransactionsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CustomerTransactions(0, currentDate, 0, 0, 0, 0, currentDate, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        transactionDate: currentDate.format(DATE_FORMAT),
                        finalizationDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a CustomerTransactions', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        transactionDate: currentDate.format(DATE_FORMAT),
                        finalizationDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        transactionDate: currentDate,
                        finalizationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CustomerTransactions(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CustomerTransactions', async () => {
                const returnedFromService = Object.assign(
                    {
                        transactionDate: currentDate.format(DATE_FORMAT),
                        amountExcludingTax: 1,
                        taxAmount: 1,
                        transactionAmount: 1,
                        outstandingBalance: 1,
                        finalizationDate: currentDate.format(DATE_FORMAT),
                        isFinalized: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        transactionDate: currentDate,
                        finalizationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of CustomerTransactions', async () => {
                const returnedFromService = Object.assign(
                    {
                        transactionDate: currentDate.format(DATE_FORMAT),
                        amountExcludingTax: 1,
                        taxAmount: 1,
                        transactionAmount: 1,
                        outstandingBalance: 1,
                        finalizationDate: currentDate.format(DATE_FORMAT),
                        isFinalized: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        transactionDate: currentDate,
                        finalizationDate: currentDate
                    },
                    returnedFromService
                );
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

            it('should delete a CustomerTransactions', async () => {
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
