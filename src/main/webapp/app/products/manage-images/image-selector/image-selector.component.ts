import { Component, EventEmitter, Input, Output, AfterViewInit } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IProductPhoto, ProductPhoto } from 'app/shared/model/product-photo.model';
import { FileUploader, FileUploaderOptions, ParsedResponseHeaders } from 'ng2-file-upload';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { Cloudinary } from '@cloudinary/angular-5.x';
import { IProducts } from 'app/shared/model/products.model';
import { CloudinaryService } from 'app/shared/components/cloudinary/cloudinary.service';
import { ProductPhotoService } from 'app/entities/product-photo';
import { ProductPhotoExtendService } from 'app/shared/services/product-photo-extend.service';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { Router } from '@angular/router';
import { CloudinaryModel } from 'app/shared/model/cloudinary.model';
import { url } from 'inspector';

@Component({
    selector: 'image-selector',
    templateUrl: './image-selector.component.html',
    styleUrls: ['./image-selector.scss']
})
export class ImageSelectorComponent implements AfterViewInit {
    @Input() productPhoto: IProductPhoto;
    @Input() product: IProducts;
    @Input() selectedIndex;
    @Output() createCompleted = new EventEmitter();
    @Output() setDefaultCompleted = new EventEmitter();
    @Output() deleteCompleted = new EventEmitter();
    isSaving: boolean;
    isSetDefaultPhoto: boolean;
    selectedFile: File = null;

    public image: any;

    // get productPhoto() {
    //     return this.productPhotos?this.productPhotos[0]:null;
    // }

    constructor(
        private http: HttpClient,
        private productPhotoService: ProductPhotoService,
        protected jhiAlertService: JhiAlertService,
        private productPhotoExtendService: ProductPhotoExtendService,
        private router: Router
    ) {
        // this.cloudinaryService.onResponseChanged.subscribe(response => {
        //     if(response && response.length>0){
        //         console.log('cloudinary response',response);
        //     }
        //
        // });
    }

    ngAfterViewInit(): void {
        // console.log('aaa',this.productPhotos,this.selectorIndex);
    }

    public onUploadCompleted(event) {
        if (event[0]) {
            const productPhoto: ProductPhoto = new ProductPhoto();
            const uploadEvent = event[0];
            const cloudinaryModel: CloudinaryModel = new CloudinaryModel();
            cloudinaryModel.cloud_name = 'www-pixsurf-com';
            // cloudinaryModel.transformations = 'w_256,ar_1:1,c_fill,g_auto,e_art:hokusai'; //square
            // cloudinaryModel.transformations = "w_512,ar_16:9,c_fill,g_auto,e_sharpen"; //spharpen
            cloudinaryModel.transformations = 'c_thumb,w_200,g_face';
            cloudinaryModel.resource_type = uploadEvent.data.resource_type;
            cloudinaryModel.type = uploadEvent.data.type;
            cloudinaryModel.public_id = uploadEvent.data.public_id;
            cloudinaryModel.version = uploadEvent.data.version;
            cloudinaryModel.format = uploadEvent.data.format;

            productPhoto.originalPhoto = uploadEvent.data.url;
            productPhoto.thumbnailPhoto = this.generatePhoto(cloudinaryModel);
            productPhoto.priority = this.selectedIndex;
            productPhoto.productId = this.product.id;
            productPhoto.deleteToken = uploadEvent.data.delete_token;
            // console.log('Upload Event', productPhoto);
            this.save(productPhoto);
        }
    }

    private generatePhoto(cloudinary: CloudinaryModel) {
        let url = 'http://res.cloudinary.com/';
        url = url + cloudinary.cloud_name + '/';
        url = url + cloudinary.resource_type + '/';
        url = url + cloudinary.type + '/';
        url = url + cloudinary.transformations + '/';
        url = url + 'v' + cloudinary.version + '/';
        url = url + cloudinary.public_id;
        url = url + '.' + cloudinary.format;

        console.log('thumb nail url', url);
        return url;
    }

    save(productPhoto: IProductPhoto) {
        this.isSaving = true;
        if (productPhoto.id !== undefined) {
            this.subscribeToSaveResponse(this.productPhotoService.update(productPhoto));
        } else {
            this.subscribeToSaveResponse(this.productPhotoService.create(productPhoto));
        }
    }

    onSetDefault(event) {
        this.isSetDefaultPhoto = true;
        if (event.id !== undefined) {
            this.subscribeToSetDefaultResponse(this.productPhotoExtendService.setDefault(event));
        }
    }

    onDeleteProductPhoto(event) {
        // console.log('on delete product photo', event);
        this.router.navigate(['/', 'manage-images', { outlets: { popup: this.productPhoto.id + '/delete' } }]);
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductPhoto>>) {
        result.subscribe((res: HttpResponse<IProductPhoto>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected subscribeToSetDefaultResponse(result: Observable<HttpResponse<IProductPhoto>>) {
        result.subscribe(
            (res: HttpResponse<IProductPhoto>) => this.onDefaultPhotoSuccess(res),
            (res: HttpErrorResponse) => this.onDefaultPhotoError()
        );
    }

    protected onSaveSuccess(event) {
        this.isSaving = false;
        this.createCompleted.emit(event);
        // this.previousState();
    }

    protected onDefaultPhotoSuccess(event) {
        this.isSetDefaultPhoto = false;
        if (event.body) {
            // this.productPhoto = event.body;
            this.setDefaultCompleted.emit(event.body);
        }

        // this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onDefaultPhotoError() {
        this.isSetDefaultPhoto = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }

    // public onFileSelected(event) {
    //     this.selectedFile = <File>event.target.files[0];
    //     if (this.selectedFile) {
    //         return this.upload();
    //     }
    // }
    //
    // upload() {
    //     const fd = new FormData();
    //     fd.append('image', this.selectedFile, this.selectedFile.name);
    //     this.http.post('http://example.com/upload/image', fd).subscribe(
    //         (res: any) => {
    //             this.image = res.data;
    //             this.event.emit(this.image);
    //         },
    //         (err: any) => {
    //             // Show error message or make something.
    //         }
    //     );
    // }
}
