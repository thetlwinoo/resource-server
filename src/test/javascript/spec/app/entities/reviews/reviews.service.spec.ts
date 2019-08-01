/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ReviewsService } from 'app/entities/reviews/reviews.service';
import { IReviews, Reviews } from 'app/shared/model/reviews.model';

describe('Service Tests', () => {
    describe('Reviews Service', () => {
        let injector: TestBed;
        let service: ReviewsService;
        let httpMock: HttpTestingController;
        let elemDefault: IReviews;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ReviewsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Reviews(0, 'AAAAAAA', 'AAAAAAA', currentDate, 0, 'AAAAAAA', 0, 'AAAAAAA', false, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        reviewDate: currentDate.format(DATE_FORMAT)
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

            it('should create a Reviews', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        reviewDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        reviewDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Reviews(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Reviews', async () => {
                const returnedFromService = Object.assign(
                    {
                        reviewerName: 'BBBBBB',
                        emailAddress: 'BBBBBB',
                        reviewDate: currentDate.format(DATE_FORMAT),
                        overAllSellerRating: 1,
                        overAllSellerReview: 'BBBBBB',
                        overAllDeliveryRating: 1,
                        overAllDeliveryReview: 'BBBBBB',
                        reviewAsAnonymous: true,
                        completedReview: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        reviewDate: currentDate
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

            it('should return a list of Reviews', async () => {
                const returnedFromService = Object.assign(
                    {
                        reviewerName: 'BBBBBB',
                        emailAddress: 'BBBBBB',
                        reviewDate: currentDate.format(DATE_FORMAT),
                        overAllSellerRating: 1,
                        overAllSellerReview: 'BBBBBB',
                        overAllDeliveryRating: 1,
                        overAllDeliveryReview: 'BBBBBB',
                        reviewAsAnonymous: true,
                        completedReview: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        reviewDate: currentDate
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

            it('should delete a Reviews', async () => {
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
