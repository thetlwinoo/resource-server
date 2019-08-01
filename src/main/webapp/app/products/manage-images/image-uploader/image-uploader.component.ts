import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { ProductPhotoService } from 'app/entities/product-photo';
import { filter, map } from 'rxjs/operators';
import { IProductPhoto, ProductPhoto } from 'app/shared/model/product-photo.model';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { CloudinaryService } from 'app/shared/components/cloudinary/cloudinary.service';
import { Cloudinary } from '@cloudinary/angular-5.x';

const NO_OF_SELECTOR = 8;

@Component({
    selector: 'image-uploader',
    templateUrl: './image-uploader.component.html',
    styleUrls: ['./image-uploader.scss']
})
export class ImageUploaderComponent implements OnInit, OnDestroy {
    @Input() product;
    currentAccount: any;
    eventSubscriber: Subscription;
    deleteEventSubscriber: Subscription;

    constructor(
        private http: HttpClient,
        protected productPhotoService: ProductPhotoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        private cloudinaryService: CloudinaryService,
        private cloudinary: Cloudinary
    ) {}

    ngOnInit() {
        // this.loadProductImages();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductPhotos();
    }

    ngOnDestroy() {
        if (this.eventSubscriber) {
            this.eventManager.destroy(this.eventSubscriber);
        }
        if (this.deleteEventSubscriber) {
            this.eventManager.destroy(this.deleteEventSubscriber);
        }
    }

    get counter() {
        return new Array(NO_OF_SELECTOR);
    }

    loadProductImages() {
        if (this.product) {
            this.productPhotoService
                .query({
                    'productId.equals': this.product.id
                })
                .pipe(
                    filter((res: HttpResponse<IProductPhoto[]>) => res.ok),
                    map((res: HttpResponse<IProductPhoto[]>) => res.body)
                )
                .subscribe(
                    (res: IProductPhoto[]) => {
                        this.product.productPhotos = res;
                        // console.log('res', res);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    registerChangeInProductPhotos() {
        this.deleteEventSubscriber = this.eventManager.subscribe('imageListModification', response => {
            this.deleteImage(response.data, 0);
        });
    }

    public onCreateCompleted(event) {
        console.log('on create completed', event);
        this.loadProductImages();
    }

    public onSetDefaultCompleted(event) {
        console.log('on set default completed', event);
        this.loadProductImages();
    }

    deleteImage = function(data: any, index: number) {
        if (data.deleteToken) {
            try {
                const delete_token = data.deleteToken;
                const url = `https://api.cloudinary.com/v1_1/${this.cloudinary.config().cloud_name}/delete_by_token`;
                const headers = new Headers({ 'Content-Type': 'application/json', 'X-Requested-With': 'XMLHttpRequest' });
                const options = { headers: headers };
                const body = {
                    token: delete_token
                };
                this.http.post(url, body, options).subscribe(response => {
                    console.log(`Deleted image -${response.result}`);
                    this.loadProductImages();
                });
            } catch (ex) {
                this.loadProductImages();
                console.log('Deleted image Exception:', ex);
            }
        } else {
            this.loadProductImages();
        }
    };
}

``;
