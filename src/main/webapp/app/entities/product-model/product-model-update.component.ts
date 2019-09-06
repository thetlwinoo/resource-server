import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiDataUtils } from 'ng-jhipster';
import { IProductModel } from 'app/shared/model/product-model.model';
import { ProductModelService } from './product-model.service';

@Component({
    selector: 'jhi-product-model-update',
    templateUrl: './product-model-update.component.html'
})
export class ProductModelUpdateComponent implements OnInit {
    productModel: IProductModel;
    isSaving: boolean;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected productModelService: ProductModelService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productModel }) => {
            this.productModel = productModel;
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
        this.dataUtils.clearInputImage(this.productModel, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productModel.id !== undefined) {
            this.subscribeToSaveResponse(this.productModelService.update(this.productModel));
        } else {
            this.subscribeToSaveResponse(this.productModelService.create(this.productModel));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductModel>>) {
        result.subscribe((res: HttpResponse<IProductModel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
