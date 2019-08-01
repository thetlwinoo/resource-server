/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { VehicleTemperaturesService } from 'app/entities/vehicle-temperatures/vehicle-temperatures.service';
import { IVehicleTemperatures, VehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';

describe('Service Tests', () => {
    describe('VehicleTemperatures Service', () => {
        let injector: TestBed;
        let service: VehicleTemperaturesService;
        let httpMock: HttpTestingController;
        let elemDefault: IVehicleTemperatures;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(VehicleTemperaturesService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new VehicleTemperatures(0, 0, 'AAAAAAA', 0, 0, false, 'AAAAAAA', 'AAAAAAA');
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

            it('should create a VehicleTemperatures', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new VehicleTemperatures(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a VehicleTemperatures', async () => {
                const returnedFromService = Object.assign(
                    {
                        vehicleRegistration: 1,
                        chillerSensorNumber: 'BBBBBB',
                        recordedWhen: 1,
                        temperature: 1,
                        isCompressed: true,
                        fullSensorData: 'BBBBBB',
                        compressedSensorData: 'BBBBBB'
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

            it('should return a list of VehicleTemperatures', async () => {
                const returnedFromService = Object.assign(
                    {
                        vehicleRegistration: 1,
                        chillerSensorNumber: 'BBBBBB',
                        recordedWhen: 1,
                        temperature: 1,
                        isCompressed: true,
                        fullSensorData: 'BBBBBB',
                        compressedSensorData: 'BBBBBB'
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

            it('should delete a VehicleTemperatures', async () => {
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
