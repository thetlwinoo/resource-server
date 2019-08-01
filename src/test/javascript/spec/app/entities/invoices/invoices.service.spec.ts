/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InvoicesService } from 'app/entities/invoices/invoices.service';
import { IInvoices, Invoices } from 'app/shared/model/invoices.model';

describe('Service Tests', () => {
    describe('Invoices Service', () => {
        let injector: TestBed;
        let service: InvoicesService;
        let httpMock: HttpTestingController;
        let elemDefault: IInvoices;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(InvoicesService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Invoices(
                0,
                currentDate,
                'AAAAAAA',
                false,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        confirmedDeliveryTime: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Invoices', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        confirmedDeliveryTime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        invoiceDate: currentDate,
                        confirmedDeliveryTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Invoices(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Invoices', async () => {
                const returnedFromService = Object.assign(
                    {
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        customerPurchaseOrderNumber: 'BBBBBB',
                        isCreditNote: true,
                        creditNoteReason: 'BBBBBB',
                        comments: 'BBBBBB',
                        deliveryInstructions: 'BBBBBB',
                        internalComments: 'BBBBBB',
                        totalDryItems: 1,
                        totalChillerItems: 1,
                        deliveryRun: 'BBBBBB',
                        runPosition: 'BBBBBB',
                        returnedDeliveryData: 'BBBBBB',
                        confirmedDeliveryTime: currentDate.format(DATE_TIME_FORMAT),
                        confirmedReceivedBy: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        invoiceDate: currentDate,
                        confirmedDeliveryTime: currentDate
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

            it('should return a list of Invoices', async () => {
                const returnedFromService = Object.assign(
                    {
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        customerPurchaseOrderNumber: 'BBBBBB',
                        isCreditNote: true,
                        creditNoteReason: 'BBBBBB',
                        comments: 'BBBBBB',
                        deliveryInstructions: 'BBBBBB',
                        internalComments: 'BBBBBB',
                        totalDryItems: 1,
                        totalChillerItems: 1,
                        deliveryRun: 'BBBBBB',
                        runPosition: 'BBBBBB',
                        returnedDeliveryData: 'BBBBBB',
                        confirmedDeliveryTime: currentDate.format(DATE_TIME_FORMAT),
                        confirmedReceivedBy: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        invoiceDate: currentDate,
                        confirmedDeliveryTime: currentDate
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

            it('should delete a Invoices', async () => {
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
