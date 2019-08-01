import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductDescription } from 'app/shared/model/product-description.model';
import { ProductDescriptionService } from './product-description.service';
import { IProductModel } from 'app/shared/model/product-model.model';
import { ProductModelService } from 'app/entities/product-model';

@Component({
    selector: 'jhi-product-description-update',
    templateUrl: './product-description-update.component.html'
})
export class ProductDescriptionUpdateComponent implements OnInit {
    productDescription: IProductDescription;
    isSaving: boolean;

    productmodels: IProductModel[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productDescriptionService: ProductDescriptionService,
        protected productModelService: ProductModelService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productDescription }) => {
            this.productDescription = productDescription;
        });
        this.productModelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductModel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductModel[]>) => response.body)
            )
            .subscribe((res: IProductModel[]) => (this.productmodels = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productDescription.id !== undefined) {
            this.subscribeToSaveResponse(this.productDescriptionService.update(this.productDescription));
        } else {
            this.subscribeToSaveResponse(this.productDescriptionService.create(this.productDescription));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductDescription>>) {
        result.subscribe((res: HttpResponse<IProductDescription>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductModelById(index: number, item: IProductModel) {
        return item.id;
    }
}
