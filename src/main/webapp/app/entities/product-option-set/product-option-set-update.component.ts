import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from './product-option-set.service';

@Component({
    selector: 'jhi-product-option-set-update',
    templateUrl: './product-option-set-update.component.html'
})
export class ProductOptionSetUpdateComponent implements OnInit {
    productOptionSet: IProductOptionSet;
    isSaving: boolean;

    constructor(protected productOptionSetService: ProductOptionSetService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productOptionSet }) => {
            this.productOptionSet = productOptionSet;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productOptionSet.id !== undefined) {
            this.subscribeToSaveResponse(this.productOptionSetService.update(this.productOptionSet));
        } else {
            this.subscribeToSaveResponse(this.productOptionSetService.create(this.productOptionSet));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductOptionSet>>) {
        result.subscribe((res: HttpResponse<IProductOptionSet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
