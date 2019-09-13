/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { PhotosService } from 'app/entities/photos/photos.service';
import { IPhotos, Photos } from 'app/shared/model/photos.model';

describe('Service Tests', () => {
    describe('Photos Service', () => {
        let injector: TestBed;
        let service: PhotosService;
        let httpMock: HttpTestingController;
        let elemDefault: IPhotos;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PhotosService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Photos(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                0,
                false,
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

            it('should create a Photos', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Photos(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Photos', async () => {
                const returnedFromService = Object.assign(
                    {
                        thumbnailPhoto: 'BBBBBB',
                        originalPhoto: 'BBBBBB',
                        bannerTallPhoto: 'BBBBBB',
                        bannerWidePhoto: 'BBBBBB',
                        circlePhoto: 'BBBBBB',
                        sharpenedPhoto: 'BBBBBB',
                        squarePhoto: 'BBBBBB',
                        watermarkPhoto: 'BBBBBB',
                        thumbnailPhotoBlob: 'BBBBBB',
                        originalPhotoBlob: 'BBBBBB',
                        bannerTallPhotoBlob: 'BBBBBB',
                        bannerWidePhotoBlob: 'BBBBBB',
                        circlePhotoBlob: 'BBBBBB',
                        sharpenedPhotoBlob: 'BBBBBB',
                        squarePhotoBlob: 'BBBBBB',
                        watermarkPhotoBlob: 'BBBBBB',
                        priority: 1,
                        defaultInd: true,
                        deleteToken: 'BBBBBB'
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

            it('should return a list of Photos', async () => {
                const returnedFromService = Object.assign(
                    {
                        thumbnailPhoto: 'BBBBBB',
                        originalPhoto: 'BBBBBB',
                        bannerTallPhoto: 'BBBBBB',
                        bannerWidePhoto: 'BBBBBB',
                        circlePhoto: 'BBBBBB',
                        sharpenedPhoto: 'BBBBBB',
                        squarePhoto: 'BBBBBB',
                        watermarkPhoto: 'BBBBBB',
                        thumbnailPhotoBlob: 'BBBBBB',
                        originalPhotoBlob: 'BBBBBB',
                        bannerTallPhotoBlob: 'BBBBBB',
                        bannerWidePhotoBlob: 'BBBBBB',
                        circlePhotoBlob: 'BBBBBB',
                        sharpenedPhotoBlob: 'BBBBBB',
                        squarePhotoBlob: 'BBBBBB',
                        watermarkPhotoBlob: 'BBBBBB',
                        priority: 1,
                        defaultInd: true,
                        deleteToken: 'BBBBBB'
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

            it('should delete a Photos', async () => {
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
