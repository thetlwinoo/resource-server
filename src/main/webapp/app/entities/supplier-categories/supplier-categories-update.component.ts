import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';
import { SupplierCategoriesService } from './supplier-categories.service';

@Component({
    selector: 'jhi-supplier-categories-update',
    templateUrl: './supplier-categories-update.component.html'
})
export class SupplierCategoriesUpdateComponent implements OnInit {
    supplierCategories: ISupplierCategories;
    isSaving: boolean;
    validFromDp: any;
    validToDp: any;

    constructor(protected supplierCategoriesService: SupplierCategoriesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplierCategories }) => {
            this.supplierCategories = supplierCategories;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.supplierCategories.id !== undefined) {
            this.subscribeToSaveResponse(this.supplierCategoriesService.update(this.supplierCategories));
        } else {
            this.subscribeToSaveResponse(this.supplierCategoriesService.create(this.supplierCategories));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierCategories>>) {
        result.subscribe((res: HttpResponse<ISupplierCategories>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
