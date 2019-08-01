/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { StockItemHoldingsService } from 'app/entities/stock-item-holdings/stock-item-holdings.service';
import { IStockItemHoldings, StockItemHoldings } from 'app/shared/model/stock-item-holdings.model';

describe('Service Tests', () => {
    describe('StockItemHoldings Service', () => {
        let injector: TestBed;
        let service: StockItemHoldingsService;
        let httpMock: HttpTestingController;
        let elemDefault: IStockItemHoldings;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StockItemHoldingsService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new StockItemHoldings(0, 0, 'AAAAAAA', 0, 0, 0, 0);
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

            it('should create a StockItemHoldings', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new StockItemHoldings(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a StockItemHoldings', async () => {
                const returnedFromService = Object.assign(
                    {
                        quantityOnHand: 1,
                        binLocation: 'BBBBBB',
                        lastStocktakeQuantity: 1,
                        lastCostPrice: 1,
                        reorderLevel: 1,
                        targerStockLevel: 1
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

            it('should return a list of StockItemHoldings', async () => {
                const returnedFromService = Object.assign(
                    {
                        quantityOnHand: 1,
                        binLocation: 'BBBBBB',
                        lastStocktakeQuantity: 1,
                        lastCostPrice: 1,
                        reorderLevel: 1,
                        targerStockLevel: 1
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

            it('should delete a StockItemHoldings', async () => {
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
