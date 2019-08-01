import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ICustomerCategories } from 'app/shared/model/customer-categories.model';
import { CustomerCategoriesService } from './customer-categories.service';

@Component({
    selector: 'jhi-customer-categories-update',
    templateUrl: './customer-categories-update.component.html'
})
export class CustomerCategoriesUpdateComponent implements OnInit {
    customerCategories: ICustomerCategories;
    isSaving: boolean;
    validFromDp: any;
    validToDp: any;

    constructor(protected customerCategoriesService: CustomerCategoriesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ customerCategories }) => {
            this.customerCategories = customerCategories;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customerCategories.id !== undefined) {
            this.subscribeToSaveResponse(this.customerCategoriesService.update(this.customerCategories));
        } else {
            this.subscribeToSaveResponse(this.customerCategoriesService.create(this.customerCategories));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerCategories>>) {
        result.subscribe((res: HttpResponse<ICustomerCategories>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
