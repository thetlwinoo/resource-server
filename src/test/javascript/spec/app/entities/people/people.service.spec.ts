/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PeopleService } from 'app/entities/people/people.service';
import { IPeople, People } from 'app/shared/model/people.model';

describe('Service Tests', () => {
    describe('People Service', () => {
        let injector: TestBed;
        let service: PeopleService;
        let httpMock: HttpTestingController;
        let elemDefault: IPeople;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PeopleService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new People(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false,
                'AAAAAAA',
                false,
                false,
                false,
                false,
                false,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        validFrom: currentDate.format(DATE_FORMAT),
                        validTo: currentDate.format(DATE_FORMAT)
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

            it('should create a People', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        validFrom: currentDate.format(DATE_FORMAT),
                        validTo: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        validFrom: currentDate,
                        validTo: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new People(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a People', async () => {
                const returnedFromService = Object.assign(
                    {
                        fullName: 'BBBBBB',
                        preferredName: 'BBBBBB',
                        searchName: 'BBBBBB',
                        isPermittedToLogon: true,
                        logonName: 'BBBBBB',
                        isExternalLogonProvider: true,
                        isSystemUser: true,
                        isEmployee: true,
                        isSalesPerson: true,
                        isGuestUser: true,
                        emailPromotion: 1,
                        userPreferences: 'BBBBBB',
                        phoneNumber: 'BBBBBB',
                        emailAddress: 'BBBBBB',
                        photo: 'BBBBBB',
                        customFields: 'BBBBBB',
                        otherLanguages: 'BBBBBB',
                        validFrom: currentDate.format(DATE_FORMAT),
                        validTo: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        validFrom: currentDate,
                        validTo: currentDate
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

            it('should return a list of People', async () => {
                const returnedFromService = Object.assign(
                    {
                        fullName: 'BBBBBB',
                        preferredName: 'BBBBBB',
                        searchName: 'BBBBBB',
                        isPermittedToLogon: true,
                        logonName: 'BBBBBB',
                        isExternalLogonProvider: true,
                        isSystemUser: true,
                        isEmployee: true,
                        isSalesPerson: true,
                        isGuestUser: true,
                        emailPromotion: 1,
                        userPreferences: 'BBBBBB',
                        phoneNumber: 'BBBBBB',
                        emailAddress: 'BBBBBB',
                        photo: 'BBBBBB',
                        customFields: 'BBBBBB',
                        otherLanguages: 'BBBBBB',
                        validFrom: currentDate.format(DATE_FORMAT),
                        validTo: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        validFrom: currentDate,
                        validTo: currentDate
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

            it('should delete a People', async () => {
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
