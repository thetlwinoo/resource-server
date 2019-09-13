import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';
import { ProductAttributeSetService } from './product-attribute-set.service';
import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from 'app/entities/product-option-set';

@Component({
    selector: 'jhi-product-attribute-set-update',
    templateUrl: './product-attribute-set-update.component.html'
})
export class ProductAttributeSetUpdateComponent implements OnInit {
    productAttributeSet: IProductAttributeSet;
    isSaving: boolean;

    productoptionsets: IProductOptionSet[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productAttributeSetService: ProductAttributeSetService,
        protected productOptionSetService: ProductOptionSetService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productAttributeSet }) => {
            this.productAttributeSet = productAttributeSet;
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
        if (this.productAttributeSet.id !== undefined) {
            this.subscribeToSaveResponse(this.productAttributeSetService.update(this.productAttributeSet));
        } else {
            this.subscribeToSaveResponse(this.productAttributeSetService.create(this.productAttributeSet));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductAttributeSet>>) {
        result.subscribe((res: HttpResponse<IProductAttributeSet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
