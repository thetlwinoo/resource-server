import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductTags } from 'app/shared/model/product-tags.model';
import { ProductTagsService } from './product-tags.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';

@Component({
    selector: 'jhi-product-tags-update',
    templateUrl: './product-tags-update.component.html'
})
export class ProductTagsUpdateComponent implements OnInit {
    productTags: IProductTags;
    isSaving: boolean;

    products: IProducts[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productTagsService: ProductTagsService,
        protected productsService: ProductsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productTags }) => {
            this.productTags = productTags;
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
        if (this.productTags.id !== undefined) {
            this.subscribeToSaveResponse(this.productTagsService.update(this.productTags));
        } else {
            this.subscribeToSaveResponse(this.productTagsService.create(this.productTags));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductTags>>) {
        result.subscribe((res: HttpResponse<IProductTags>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
