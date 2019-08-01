import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProductModel } from 'app/shared/model/product-model.model';
import { ProductModelService } from './product-model.service';

@Component({
    selector: 'jhi-product-model-update',
    templateUrl: './product-model-update.component.html'
})
export class ProductModelUpdateComponent implements OnInit {
    productModel: IProductModel;
    isSaving: boolean;

    constructor(protected productModelService: ProductModelService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productModel }) => {
            this.productModel = productModel;
        });
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
