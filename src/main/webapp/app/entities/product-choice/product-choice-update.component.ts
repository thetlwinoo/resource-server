import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductChoice } from 'app/shared/model/product-choice.model';
import { ProductChoiceService } from './product-choice.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';
import { ProductAttributeSetService } from 'app/entities/product-attribute-set';
import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from 'app/entities/product-option-set';

@Component({
    selector: 'jhi-product-choice-update',
    templateUrl: './product-choice-update.component.html'
})
export class ProductChoiceUpdateComponent implements OnInit {
    productChoice: IProductChoice;
    isSaving: boolean;

    productcategories: IProductCategory[];

    productattributesets: IProductAttributeSet[];

    productoptionsets: IProductOptionSet[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productChoiceService: ProductChoiceService,
        protected productCategoryService: ProductCategoryService,
        protected productAttributeSetService: ProductAttributeSetService,
        protected productOptionSetService: ProductOptionSetService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productChoice }) => {
            this.productChoice = productChoice;
        });
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productAttributeSetService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductAttributeSet[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductAttributeSet[]>) => response.body)
            )
            .subscribe(
                (res: IProductAttributeSet[]) => (this.productattributesets = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
        if (this.productChoice.id !== undefined) {
            this.subscribeToSaveResponse(this.productChoiceService.update(this.productChoice));
        } else {
            this.subscribeToSaveResponse(this.productChoiceService.create(this.productChoice));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductChoice>>) {
        result.subscribe((res: HttpResponse<IProductChoice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackProductAttributeSetById(index: number, item: IProductAttributeSet) {
        return item.id;
    }

    trackProductOptionSetById(index: number, item: IProductOptionSet) {
        return item.id;
    }
}
