import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductModelDescription } from 'app/shared/model/product-model-description.model';
import { ProductModelDescriptionService } from './product-model-description.service';
import { IProductModel } from 'app/shared/model/product-model.model';
import { ProductModelService } from 'app/entities/product-model';

@Component({
    selector: 'jhi-product-model-description-update',
    templateUrl: './product-model-description-update.component.html'
})
export class ProductModelDescriptionUpdateComponent implements OnInit {
    productModelDescription: IProductModelDescription;
    isSaving: boolean;

    productmodels: IProductModel[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productModelDescriptionService: ProductModelDescriptionService,
        protected productModelService: ProductModelService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productModelDescription }) => {
            this.productModelDescription = productModelDescription;
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
        if (this.productModelDescription.id !== undefined) {
            this.subscribeToSaveResponse(this.productModelDescriptionService.update(this.productModelDescription));
        } else {
            this.subscribeToSaveResponse(this.productModelDescriptionService.create(this.productModelDescription));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductModelDescription>>) {
        result.subscribe(
            (res: HttpResponse<IProductModelDescription>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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
