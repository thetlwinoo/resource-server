import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProductSet } from 'app/shared/model/product-set.model';
import { ProductSetService } from './product-set.service';

@Component({
    selector: 'jhi-product-set-update',
    templateUrl: './product-set-update.component.html'
})
export class ProductSetUpdateComponent implements OnInit {
    productSet: IProductSet;
    isSaving: boolean;

    constructor(protected productSetService: ProductSetService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productSet }) => {
            this.productSet = productSet;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productSet.id !== undefined) {
            this.subscribeToSaveResponse(this.productSetService.update(this.productSet));
        } else {
            this.subscribeToSaveResponse(this.productSetService.create(this.productSet));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSet>>) {
        result.subscribe((res: HttpResponse<IProductSet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
