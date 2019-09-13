import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductOption } from 'app/shared/model/product-option.model';
import { ProductOptionService } from './product-option.service';
import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from 'app/entities/product-option-set';

@Component({
    selector: 'jhi-product-option-update',
    templateUrl: './product-option-update.component.html'
})
export class ProductOptionUpdateComponent implements OnInit {
    productOption: IProductOption;
    isSaving: boolean;

    productoptionsets: IProductOptionSet[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productOptionService: ProductOptionService,
        protected productOptionSetService: ProductOptionSetService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productOption }) => {
            this.productOption = productOption;
        });
        this.productOptionSetService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductOptionSet[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductOptionSet[]>) => response.body)
            )
            .subscribe((res: IProductOptionSet[]) => (this.productoptionsets = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productOption.id !== undefined) {
            this.subscribeToSaveResponse(this.productOptionService.update(this.productOption));
        } else {
            this.subscribeToSaveResponse(this.productOptionService.create(this.productOption));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductOption>>) {
        result.subscribe((res: HttpResponse<IProductOption>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductOptionSetById(index: number, item: IProductOptionSet) {
        return item.id;
    }
}
