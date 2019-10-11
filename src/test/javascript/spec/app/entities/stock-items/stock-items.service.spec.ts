/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { IStockItems, StockItems } from 'app/shared/model/stock-items.model';

describe('Service Tests', () => {
    describe('StockItems Service', () => {
        let injector: TestBed;
        let service: StockItemsService;
        let httpMock: HttpTestingController;
        let elemDefault: IStockItems;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StockItemsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new StockItems(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                0,
                'AAAAAAA',
                'AAAAAAA',
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        sellStartDate: currentDate.format(DATE_FORMAT),
                        sellEndDate: currentDate.format(DATE_FORMAT)
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

            it('should create a StockItems', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        sellStartDate: currentDate.format(DATE_FORMAT),
                        sellEndDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        sellStartDate: currentDate,
                        sellEndDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new StockItems(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a StockItems', async () => {
                const returnedFromService = Object.assign(
                    {
                        stockItemName: 'BBBBBB',
                        vendorCode: 'BBBBBB',
                        vendorSKU: 'BBBBBB',
                        generatedSKU: 'BBBBBB',
                        barcode: 'BBBBBB',
                        unitPrice: 1,
                        recommendedRetailPrice: 1,
                        quantityOnHand: 1,
                        itemLength: 1,
                        itemWidth: 1,
                        itemHeight: 1,
                        itemWeight: 1,
                        itemPackageLength: 1,
                        itemPackageWidth: 1,
                        itemPackageHeight: 1,
                        itemPackageWeight: 1,
                        noOfPieces: 1,
                        noOfItems: 1,
                        manufacture: 'BBBBBB',
                        marketingComments: 'BBBBBB',
                        internalComments: 'BBBBBB',
                        sellStartDate: currentDate.format(DATE_FORMAT),
                        sellEndDate: currentDate.format(DATE_FORMAT),
                        sellCount: 1,
                        customFields: 'BBBBBB',
                        thumbnailUrl: 'BBBBBB',
                        activeInd: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        sellStartDate: currentDate,
                        sellEndDate: currentDate
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

            it('should return a list of StockItems', async () => {
                const returnedFromService = Object.assign(
                    {
                        stockItemName: 'BBBBBB',
                        vendorCode: 'BBBBBB',
                        vendorSKU: 'BBBBBB',
                        generatedSKU: 'BBBBBB',
                        barcode: 'BBBBBB',
                        unitPrice: 1,
                        recommendedRetailPrice: 1,
                        quantityOnHand: 1,
                        itemLength: 1,
                        itemWidth: 1,
                        itemHeight: 1,
                        itemWeight: 1,
                        itemPackageLength: 1,
                        itemPackageWidth: 1,
                        itemPackageHeight: 1,
                        itemPackageWeight: 1,
                        noOfPieces: 1,
                        noOfItems: 1,
                        manufacture: 'BBBBBB',
                        marketingComments: 'BBBBBB',
                        internalComments: 'BBBBBB',
                        sellStartDate: currentDate.format(DATE_FORMAT),
                        sellEndDate: currentDate.format(DATE_FORMAT),
                        sellCount: 1,
                        customFields: 'BBBBBB',
                        thumbnailUrl: 'BBBBBB',
                        activeInd: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        sellStartDate: currentDate,
                        sellEndDate: currentDate
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

            it('should delete a StockItems', async () => {
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
