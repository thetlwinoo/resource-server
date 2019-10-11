/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StockItemTempService } from 'app/entities/stock-item-temp/stock-item-temp.service';
import { IStockItemTemp, StockItemTemp } from 'app/shared/model/stock-item-temp.model';

describe('Service Tests', () => {
    describe('StockItemTemp Service', () => {
        let injector: TestBed;
        let service: StockItemTempService;
        let httpMock: HttpTestingController;
        let elemDefault: IStockItemTemp;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StockItemTempService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new StockItemTemp(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                0,
                'AAAAAAA',
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                0,
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
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                0
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

            it('should create a StockItemTemp', async () => {
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
                    .create(new StockItemTemp(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a StockItemTemp', async () => {
                const returnedFromService = Object.assign(
                    {
                        stockItemName: 'BBBBBB',
                        vendorCode: 'BBBBBB',
                        vendorSKU: 'BBBBBB',
                        barcode: 'BBBBBB',
                        barcodeTypeId: 1,
                        barcodeTypeName: 'BBBBBB',
                        productType: 'BBBBBB',
                        productCategoryId: 1,
                        productCategoryName: 'BBBBBB',
                        productAttributeSetId: 1,
                        productAttributeId: 1,
                        productAttributeValue: 'BBBBBB',
                        productOptionSetId: 1,
                        productOptionId: 1,
                        productOptionValue: 'BBBBBB',
                        modelName: 'BBBBBB',
                        modelNumber: 'BBBBBB',
                        materialId: 1,
                        materialName: 'BBBBBB',
                        shortDescription: 'BBBBBB',
                        description: 'BBBBBB',
                        productBrandId: 1,
                        productBrandName: 'BBBBBB',
                        highlights: 'BBBBBB',
                        searchDetails: 'BBBBBB',
                        careInstructions: 'BBBBBB',
                        dangerousGoods: 'BBBBBB',
                        videoUrl: 'BBBBBB',
                        unitPrice: 1,
                        remommendedRetailPrice: 1,
                        currencyCode: 'BBBBBB',
                        quantityOnHand: 1,
                        warrantyPeriod: 'BBBBBB',
                        warrantyPolicy: 'BBBBBB',
                        warrantyTypeId: 1,
                        warrantyTypeName: 'BBBBBB',
                        whatInTheBox: 'BBBBBB',
                        itemLength: 1,
                        itemWidth: 1,
                        itemHeight: 1,
                        itemWeight: 1,
                        itemPackageLength: 1,
                        itemPackageWidth: 1,
                        itemPackageHeight: 1,
                        itemPackageWeight: 1,
                        itemLengthUnitMeasureId: 1,
                        itemLengthUnitMeasureCode: 'BBBBBB',
                        itemWidthUnitMeasureId: 1,
                        itemWidthUnitMeasureCode: 'BBBBBB',
                        itemHeightUnitMeasureId: 1,
                        itemHeightUnitMeasureCode: 'BBBBBB',
                        itemWeightUnitMeasureId: 1,
                        itemWeightUnitMeasureCode: 'BBBBBB',
                        itemPackageLengthUnitMeasureId: 1,
                        itemPackageLengthUnitMeasureCode: 'BBBBBB',
                        itemPackageWidthUnitMeasureId: 1,
                        itemPackageWidthUnitMeasureCode: 'BBBBBB',
                        itemPackageHeightUnitMeasureId: 1,
                        itemPackageHeightUnitMeasureCode: 'BBBBBB',
                        itemPackageWeightUnitMeasureId: 1,
                        itemPackageWeightUnitMeasureCode: 'BBBBBB',
                        noOfPieces: 1,
                        noOfItems: 1,
                        manufacture: 'BBBBBB',
                        specialFeactures: 'BBBBBB',
                        productComplianceCertificate: 'BBBBBB',
                        genuineAndLegal: true,
                        countryOfOrigin: 'BBBBBB',
                        usageAndSideEffects: 'BBBBBB',
                        safetyWarnning: 'BBBBBB',
                        sellStartDate: currentDate.format(DATE_FORMAT),
                        sellEndDate: currentDate.format(DATE_FORMAT),
                        status: 1
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

            it('should return a list of StockItemTemp', async () => {
                const returnedFromService = Object.assign(
                    {
                        stockItemName: 'BBBBBB',
                        vendorCode: 'BBBBBB',
                        vendorSKU: 'BBBBBB',
                        barcode: 'BBBBBB',
                        barcodeTypeId: 1,
                        barcodeTypeName: 'BBBBBB',
                        productType: 'BBBBBB',
                        productCategoryId: 1,
                        productCategoryName: 'BBBBBB',
                        productAttributeSetId: 1,
                        productAttributeId: 1,
                        productAttributeValue: 'BBBBBB',
                        productOptionSetId: 1,
                        productOptionId: 1,
                        productOptionValue: 'BBBBBB',
                        modelName: 'BBBBBB',
                        modelNumber: 'BBBBBB',
                        materialId: 1,
                        materialName: 'BBBBBB',
                        shortDescription: 'BBBBBB',
                        description: 'BBBBBB',
                        productBrandId: 1,
                        productBrandName: 'BBBBBB',
                        highlights: 'BBBBBB',
                        searchDetails: 'BBBBBB',
                        careInstructions: 'BBBBBB',
                        dangerousGoods: 'BBBBBB',
                        videoUrl: 'BBBBBB',
                        unitPrice: 1,
                        remommendedRetailPrice: 1,
                        currencyCode: 'BBBBBB',
                        quantityOnHand: 1,
                        warrantyPeriod: 'BBBBBB',
                        warrantyPolicy: 'BBBBBB',
                        warrantyTypeId: 1,
                        warrantyTypeName: 'BBBBBB',
                        whatInTheBox: 'BBBBBB',
                        itemLength: 1,
                        itemWidth: 1,
                        itemHeight: 1,
                        itemWeight: 1,
                        itemPackageLength: 1,
                        itemPackageWidth: 1,
                        itemPackageHeight: 1,
                        itemPackageWeight: 1,
                        itemLengthUnitMeasureId: 1,
                        itemLengthUnitMeasureCode: 'BBBBBB',
                        itemWidthUnitMeasureId: 1,
                        itemWidthUnitMeasureCode: 'BBBBBB',
                        itemHeightUnitMeasureId: 1,
                        itemHeightUnitMeasureCode: 'BBBBBB',
                        itemWeightUnitMeasureId: 1,
                        itemWeightUnitMeasureCode: 'BBBBBB',
                        itemPackageLengthUnitMeasureId: 1,
                        itemPackageLengthUnitMeasureCode: 'BBBBBB',
                        itemPackageWidthUnitMeasureId: 1,
                        itemPackageWidthUnitMeasureCode: 'BBBBBB',
                        itemPackageHeightUnitMeasureId: 1,
                        itemPackageHeightUnitMeasureCode: 'BBBBBB',
                        itemPackageWeightUnitMeasureId: 1,
                        itemPackageWeightUnitMeasureCode: 'BBBBBB',
                        noOfPieces: 1,
                        noOfItems: 1,
                        manufacture: 'BBBBBB',
                        specialFeactures: 'BBBBBB',
                        productComplianceCertificate: 'BBBBBB',
                        genuineAndLegal: true,
                        countryOfOrigin: 'BBBBBB',
                        usageAndSideEffects: 'BBBBBB',
                        safetyWarnning: 'BBBBBB',
                        sellStartDate: currentDate.format(DATE_FORMAT),
                        sellEndDate: currentDate.format(DATE_FORMAT),
                        status: 1
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

            it('should delete a StockItemTemp', async () => {
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
