import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProducts } from 'app/shared/model/products.model';
import { ManageProductsService } from './manage-products.service';
import { IProductSubCategory } from 'app/shared/model/product-sub-category.model';
import { ProductSubCategoryService } from 'app/entities/product-sub-category';
import { IUnitMeasure } from 'app/shared/model/unit-measure.model';
import { UnitMeasureService } from 'app/entities/unit-measure';
import { IProductModel } from 'app/shared/model/product-model.model';
import { ProductModelService } from 'app/entities/product-model';
// import { IStockItems } from 'app/shared/model/stock-items.model';
// import { StockItemsService } from 'app/entities/stock-items';

@Component({
    selector: 'jhi-products-update',
    templateUrl: './manage-products-update.component.html'
})
export class ManageProductsUpdateComponent implements OnInit {
    products: IProducts;
    isSaving: boolean;

    productsubcategories: IProductSubCategory[];

    unitmeasures: IUnitMeasure[];

    productmodels: IProductModel[];

    // stockitems: IStockItems[];
    sellStartDateDp: any;
    sellEndDateDp: any;
    discontinuedDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productsService: ManageProductsService,
        protected productSubCategoryService: ProductSubCategoryService,
        protected unitMeasureService: UnitMeasureService,
        protected productModelService: ProductModelService,
        // protected stockItemsService: StockItemsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ products }) => {
            this.products = products;
        });
        this.productSubCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductSubCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductSubCategory[]>) => response.body)
            )
            .subscribe(
                (res: IProductSubCategory[]) => (this.productsubcategories = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.unitMeasureService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUnitMeasure[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUnitMeasure[]>) => response.body)
            )
            .subscribe((res: IUnitMeasure[]) => (this.unitmeasures = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productModelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductModel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductModel[]>) => response.body)
            )
            .subscribe((res: IProductModel[]) => (this.productmodels = res), (res: HttpErrorResponse) => this.onError(res.message));
        // this.stockItemsService
        //     .query()
        //     .pipe(
        //         filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
        //         map((response: HttpResponse<IStockItems[]>) => response.body)
        //     )
        //     .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.products.id !== undefined) {
            this.subscribeToSaveResponse(this.productsService.update(this.products));
        } else {
            this.subscribeToSaveResponse(this.productsService.create(this.products));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducts>>) {
        result.subscribe((res: HttpResponse<IProducts>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductSubCategoryById(index: number, item: IProductSubCategory) {
        return item.id;
    }

    trackUnitMeasureById(index: number, item: IUnitMeasure) {
        return item.id;
    }

    trackProductModelById(index: number, item: IProductModel) {
        return item.id;
    }

    // trackStockItemsById(index: number, item: IStockItems) {
    //     return item.id;
    // }
}
