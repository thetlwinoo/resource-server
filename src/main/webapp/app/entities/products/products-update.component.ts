import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from './products.service';
import { IProductDocument } from 'app/shared/model/product-document.model';
import { ProductDocumentService } from 'app/entities/product-document';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from 'app/entities/product-brand';

@Component({
    selector: 'jhi-products-update',
    templateUrl: './products-update.component.html'
})
export class ProductsUpdateComponent implements OnInit {
    products: IProducts;
    isSaving: boolean;

    documents: IProductDocument[];

    suppliers: ISuppliers[];

    productcategories: IProductCategory[];

    productbrands: IProductBrand[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected productsService: ProductsService,
        protected productDocumentService: ProductDocumentService,
        protected suppliersService: SuppliersService,
        protected productCategoryService: ProductCategoryService,
        protected productBrandService: ProductBrandService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ products }) => {
            this.products = products;
        });
        this.productDocumentService
            .query({ 'productId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IProductDocument[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductDocument[]>) => response.body)
            )
            .subscribe(
                (res: IProductDocument[]) => {
                    if (!this.products.documentId) {
                        this.documents = res;
                    } else {
                        this.productDocumentService
                            .find(this.products.documentId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IProductDocument>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IProductDocument>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IProductDocument) => (this.documents = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.suppliersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISuppliers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISuppliers[]>) => response.body)
            )
            .subscribe((res: ISuppliers[]) => (this.suppliers = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
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

    trackProductDocumentById(index: number, item: IProductDocument) {
        return item.id;
    }

    trackSuppliersById(index: number, item: ISuppliers) {
        return item.id;
    }

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackProductBrandById(index: number, item: IProductBrand) {
        return item.id;
    }
}
