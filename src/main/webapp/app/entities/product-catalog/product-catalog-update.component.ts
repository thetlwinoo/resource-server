import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductCatalog } from 'app/shared/model/product-catalog.model';
import { ProductCatalogService } from './product-catalog.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';

@Component({
    selector: 'jhi-product-catalog-update',
    templateUrl: './product-catalog-update.component.html'
})
export class ProductCatalogUpdateComponent implements OnInit {
    productCatalog: IProductCatalog;
    isSaving: boolean;

    productcategories: IProductCategory[];

    products: IProducts[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productCatalogService: ProductCatalogService,
        protected productCategoryService: ProductCategoryService,
        protected productsService: ProductsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productCatalog }) => {
            this.productCatalog = productCatalog;
        });
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.productCatalog.id !== undefined) {
            this.subscribeToSaveResponse(this.productCatalogService.update(this.productCatalog));
        } else {
            this.subscribeToSaveResponse(this.productCatalogService.create(this.productCatalog));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductCatalog>>) {
        result.subscribe((res: HttpResponse<IProductCatalog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }
}
