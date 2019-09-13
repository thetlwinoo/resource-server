import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiDataUtils } from 'ng-jhipster';
import { IProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from './product-brand.service';

@Component({
    selector: 'jhi-product-brand-update',
    templateUrl: './product-brand-update.component.html'
})
export class ProductBrandUpdateComponent implements OnInit {
    productBrand: IProductBrand;
    isSaving: boolean;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected productBrandService: ProductBrandService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productBrand }) => {
            this.productBrand = productBrand;
        });
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

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.productBrand, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productBrand.id !== undefined) {
            this.subscribeToSaveResponse(this.productBrandService.update(this.productBrand));
        } else {
            this.subscribeToSaveResponse(this.productBrandService.create(this.productBrand));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductBrand>>) {
        result.subscribe((res: HttpResponse<IProductBrand>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
