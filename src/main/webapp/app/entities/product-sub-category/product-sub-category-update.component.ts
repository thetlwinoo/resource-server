import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductSubCategory } from 'app/shared/model/product-sub-category.model';
import { ProductSubCategoryService } from './product-sub-category.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';

@Component({
    selector: 'jhi-product-sub-category-update',
    templateUrl: './product-sub-category-update.component.html'
})
export class ProductSubCategoryUpdateComponent implements OnInit {
    productSubCategory: IProductSubCategory;
    isSaving: boolean;

    productcategories: IProductCategory[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productSubCategoryService: ProductSubCategoryService,
        protected productCategoryService: ProductCategoryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productSubCategory }) => {
            this.productSubCategory = productSubCategory;
        });
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productSubCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.productSubCategoryService.update(this.productSubCategory));
        } else {
            this.subscribeToSaveResponse(this.productSubCategoryService.create(this.productSubCategory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSubCategory>>) {
        result.subscribe((res: HttpResponse<IProductSubCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
