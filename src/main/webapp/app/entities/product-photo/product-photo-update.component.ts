import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductPhoto } from 'app/shared/model/product-photo.model';
import { ProductPhotoService } from './product-photo.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';

@Component({
    selector: 'jhi-product-photo-update',
    templateUrl: './product-photo-update.component.html'
})
export class ProductPhotoUpdateComponent implements OnInit {
    productPhoto: IProductPhoto;
    isSaving: boolean;

    products: IProducts[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productPhotoService: ProductPhotoService,
        protected productsService: ProductsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productPhoto }) => {
            this.productPhoto = productPhoto;
        });
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productPhoto.id !== undefined) {
            this.subscribeToSaveResponse(this.productPhotoService.update(this.productPhoto));
        } else {
            this.subscribeToSaveResponse(this.productPhotoService.create(this.productPhoto));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductPhoto>>) {
        result.subscribe((res: HttpResponse<IProductPhoto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }
}
