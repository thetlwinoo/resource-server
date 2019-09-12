import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from './products.service';
import { IReviewLines } from 'app/shared/model/review-lines.model';
import { ReviewLinesService } from 'app/entities/review-lines';
import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from 'app/entities/package-types';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers';
import { IMerchants } from 'app/shared/model/merchants.model';
import { MerchantsService } from 'app/entities/merchants';
import { IProductSubCategory } from 'app/shared/model/product-sub-category.model';
import { ProductSubCategoryService } from 'app/entities/product-sub-category';
import { IUnitMeasure } from 'app/shared/model/unit-measure.model';
import { UnitMeasureService } from 'app/entities/unit-measure';
import { IProductModel } from 'app/shared/model/product-model.model';
import { ProductModelService } from 'app/entities/product-model';

@Component({
    selector: 'jhi-products-update',
    templateUrl: './products-update.component.html'
})
export class ProductsUpdateComponent implements OnInit {
    products: IProducts;
    isSaving: boolean;

    reviewlines: IReviewLines[];

    packagetypes: IPackageTypes[];

    suppliers: ISuppliers[];

    merchants: IMerchants[];

    productsubcategories: IProductSubCategory[];

    unitmeasures: IUnitMeasure[];

    productmodels: IProductModel[];
    sellStartDateDp: any;
    sellEndDateDp: any;
    discontinuedDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productsService: ProductsService,
        protected reviewLinesService: ReviewLinesService,
        protected packageTypesService: PackageTypesService,
        protected suppliersService: SuppliersService,
        protected merchantsService: MerchantsService,
        protected productSubCategoryService: ProductSubCategoryService,
        protected unitMeasureService: UnitMeasureService,
        protected productModelService: ProductModelService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ products }) => {
            this.products = products;
        });
        this.reviewLinesService
            .query({ 'productId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IReviewLines[]>) => mayBeOk.ok),
                map((response: HttpResponse<IReviewLines[]>) => response.body)
            )
            .subscribe(
                (res: IReviewLines[]) => {
                    if (!this.products.reviewLineId) {
                        this.reviewlines = res;
                    } else {
                        this.reviewLinesService
                            .find(this.products.reviewLineId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IReviewLines>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IReviewLines>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IReviewLines) => (this.reviewlines = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.packageTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPackageTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPackageTypes[]>) => response.body)
            )
            .subscribe((res: IPackageTypes[]) => (this.packagetypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.suppliersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISuppliers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISuppliers[]>) => response.body)
            )
            .subscribe((res: ISuppliers[]) => (this.suppliers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.merchantsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMerchants[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMerchants[]>) => response.body)
            )
            .subscribe((res: IMerchants[]) => (this.merchants = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackReviewLinesById(index: number, item: IReviewLines) {
        return item.id;
    }

    trackPackageTypesById(index: number, item: IPackageTypes) {
        return item.id;
    }

    trackSuppliersById(index: number, item: ISuppliers) {
        return item.id;
    }

    trackMerchantsById(index: number, item: IMerchants) {
        return item.id;
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
}
