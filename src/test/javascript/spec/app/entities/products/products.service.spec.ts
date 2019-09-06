/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProductsService } from 'app/entities/products/products.service';
import { IProducts, Products } from 'app/shared/model/products.model';

describe('Service Tests', () => {
    describe('Products Service', () => {
        let injector: TestBed;
        let service: ProductsService;
        let httpMock: HttpTestingController;
        let elemDefault: IProducts;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProductsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Products(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false,
                false,
                'AAAAAAA',
                0,
                0,
                0,
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                0
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        sellStartDate: currentDate.format(DATE_FORMAT),
                        sellEndDate: currentDate.format(DATE_FORMAT),
                        discontinuedDate: currentDate.format(DATE_FORMAT)
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

            it('should create a Products', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        sellStartDate: currentDate.format(DATE_FORMAT),
                        sellEndDate: currentDate.format(DATE_FORMAT),
                        discontinuedDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        sellStartDate: currentDate,
                        sellEndDate: currentDate,
                        discontinuedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Products(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Products', async () => {
                const returnedFromService = Object.assign(
                    {
                        productName: 'BBBBBB',
                        productNumber: 'BBBBBB',
                        searchDetails: 'BBBBBB',
                        makeFlag: true,
                        finishedGoodsFlag: true,
                        color: 'BBBBBB',
                        safetyStockLevel: 1,
                        reorderPoint: 1,
                        standardCost: 1,
                        unitPrice: 1,
                        recommendedRetailPrice: 1,
                        brand: 'BBBBBB',
                        specifySize: 'BBBBBB',
                        weight: 1,
                        daysToManufacture: 1,
                        productLine: 'BBBBBB',
                        classType: 'BBBBBB',
                        style: 'BBBBBB',
                        customFields: 'BBBBBB',
                        photo: 'BBBBBB',
                        sellStartDate: currentDate.format(DATE_FORMAT),
                        sellEndDate: currentDate.format(DATE_FORMAT),
                        marketingComments: 'BBBBBB',
                        internalComments: 'BBBBBB',
                        discontinuedDate: currentDate.format(DATE_FORMAT),
                        sellCount: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        sellStartDate: currentDate,
                        sellEndDate: currentDate,
                        discontinuedDate: currentDate
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

            it('should return a list of Products', async () => {
                const returnedFromService = Object.assign(
                    {
                        productName: 'BBBBBB',
                        productNumber: 'BBBBBB',
                        searchDetails: 'BBBBBB',
                        makeFlag: true,
                        finishedGoodsFlag: true,
                        color: 'BBBBBB',
                        safetyStockLevel: 1,
                        reorderPoint: 1,
                        standardCost: 1,
                        unitPrice: 1,
                        recommendedRetailPrice: 1,
                        brand: 'BBBBBB',
                        specifySize: 'BBBBBB',
                        weight: 1,
                        daysToManufacture: 1,
                        productLine: 'BBBBBB',
                        classType: 'BBBBBB',
                        style: 'BBBBBB',
                        customFields: 'BBBBBB',
                        photo: 'BBBBBB',
                        sellStartDate: currentDate.format(DATE_FORMAT),
                        sellEndDate: currentDate.format(DATE_FORMAT),
                        marketingComments: 'BBBBBB',
                        internalComments: 'BBBBBB',
                        discontinuedDate: currentDate.format(DATE_FORMAT),
                        sellCount: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        sellStartDate: currentDate,
                        sellEndDate: currentDate,
                        discontinuedDate: currentDate
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

            it('should delete a Products', async () => {
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
