import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from './products.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers';
import { IMerchants } from 'app/shared/model/merchants.model';
import { MerchantsService } from 'app/entities/merchants';
import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from 'app/entities/package-types';
import { IProductModel } from 'app/shared/model/product-model.model';
import { ProductModelService } from 'app/entities/product-model';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from 'app/entities/product-brand';
import { IWarrantyTypes } from 'app/shared/model/warranty-types.model';
import { WarrantyTypesService } from 'app/entities/warranty-types';

@Component({
    selector: 'jhi-products-update',
    templateUrl: './products-update.component.html'
})
export class ProductsUpdateComponent implements OnInit {
    products: IProducts;
    isSaving: boolean;

    suppliers: ISuppliers[];

    merchants: IMerchants[];

    packagetypes: IPackageTypes[];

    productmodels: IProductModel[];

    productcategories: IProductCategory[];

    productbrands: IProductBrand[];

    warrantytypes: IWarrantyTypes[];
    sellStartDateDp: any;
    sellEndDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productsService: ProductsService,
        protected suppliersService: SuppliersService,
        protected merchantsService: MerchantsService,
        protected packageTypesService: PackageTypesService,
        protected productModelService: ProductModelService,
        protected productCategoryService: ProductCategoryService,
        protected productBrandService: ProductBrandService,
        protected warrantyTypesService: WarrantyTypesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ products }) => {
            this.products = products;
        });
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
        this.packageTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPackageTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPackageTypes[]>) => response.body)
            )
            .subscribe((res: IPackageTypes[]) => (this.packagetypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productModelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductModel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductModel[]>) => response.body)
            )
            .subscribe((res: IProductModel[]) => (this.productmodels = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productBrandService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductBrand[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductBrand[]>) => response.body)
            )
            .subscribe((res: IProductBrand[]) => (this.productbrands = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.warrantyTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IWarrantyTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IWarrantyTypes[]>) => response.body)
            )
            .subscribe((res: IWarrantyTypes[]) => (this.warrantytypes = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackSuppliersById(index: number, item: ISuppliers) {
        return item.id;
    }

    trackMerchantsById(index: number, item: IMerchants) {
        return item.id;
    }

    trackPackageTypesById(index: number, item: IPackageTypes) {
        return item.id;
    }

    trackProductModelById(index: number, item: IProductModel) {
        return item.id;
    }

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackProductBrandById(index: number, item: IProductBrand) {
        return item.id;
    }

    trackWarrantyTypesById(index: number, item: IWarrantyTypes) {
        return item.id;
    }
}
