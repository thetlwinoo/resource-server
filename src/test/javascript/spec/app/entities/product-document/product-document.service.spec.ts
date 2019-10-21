/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ProductDocumentService } from 'app/entities/product-document/product-document.service';
import { IProductDocument, ProductDocument } from 'app/shared/model/product-document.model';

describe('Service Tests', () => {
    describe('ProductDocument Service', () => {
        let injector: TestBed;
        let service: ProductDocumentService;
        let httpMock: HttpTestingController;
        let elemDefault: IProductDocument;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProductDocumentService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new ProductDocument(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA'
            );
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

            it('should create a ProductDocument', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new ProductDocument(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ProductDocument', async () => {
                const returnedFromService = Object.assign(
                    {
                        videoUrl: 'BBBBBB',
                        highlights: 'BBBBBB',
                        longDescription: 'BBBBBB',
                        shortDescription: 'BBBBBB',
                        description: 'BBBBBB',
                        careInstructions: 'BBBBBB',
                        productType: 'BBBBBB',
                        modelName: 'BBBBBB',
                        modelNumber: 'BBBBBB',
                        fabricType: 'BBBBBB',
                        specialFeatures: 'BBBBBB',
                        productComplianceCertificate: 'BBBBBB',
                        genuineAndLegal: true,
                        countryOfOrigin: 'BBBBBB',
                        usageAndSideEffects: 'BBBBBB',
                        safetyWarnning: 'BBBBBB',
                        warrantyPeriod: 'BBBBBB',
                        warrantyPolicy: 'BBBBBB'
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

            it('should return a list of ProductDocument', async () => {
                const returnedFromService = Object.assign(
                    {
                        videoUrl: 'BBBBBB',
                        highlights: 'BBBBBB',
                        longDescription: 'BBBBBB',
                        shortDescription: 'BBBBBB',
                        description: 'BBBBBB',
                        careInstructions: 'BBBBBB',
                        productType: 'BBBBBB',
                        modelName: 'BBBBBB',
                        modelNumber: 'BBBBBB',
                        fabricType: 'BBBBBB',
                        specialFeatures: 'BBBBBB',
                        productComplianceCertificate: 'BBBBBB',
                        genuineAndLegal: true,
                        countryOfOrigin: 'BBBBBB',
                        usageAndSideEffects: 'BBBBBB',
                        safetyWarnning: 'BBBBBB',
                        warrantyPeriod: 'BBBBBB',
                        warrantyPolicy: 'BBBBBB'
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

            it('should delete a ProductDocument', async () => {
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
