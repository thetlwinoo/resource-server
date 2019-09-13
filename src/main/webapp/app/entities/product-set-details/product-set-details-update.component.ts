import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProductSetDetails } from 'app/shared/model/product-set-details.model';
import { ProductSetDetailsService } from './product-set-details.service';

@Component({
    selector: 'jhi-product-set-details-update',
    templateUrl: './product-set-details-update.component.html'
})
export class ProductSetDetailsUpdateComponent implements OnInit {
    productSetDetails: IProductSetDetails;
    isSaving: boolean;

    constructor(protected productSetDetailsService: ProductSetDetailsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productSetDetails }) => {
            this.productSetDetails = productSetDetails;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productSetDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.productSetDetailsService.update(this.productSetDetails));
        } else {
            this.subscribeToSaveResponse(this.productSetDetailsService.create(this.productSetDetails));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSetDetails>>) {
        result.subscribe((res: HttpResponse<IProductSetDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
