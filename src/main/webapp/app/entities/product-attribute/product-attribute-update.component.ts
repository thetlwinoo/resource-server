import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductAttribute } from 'app/shared/model/product-attribute.model';
import { ProductAttributeService } from './product-attribute.service';
import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';
import { ProductAttributeSetService } from 'app/entities/product-attribute-set';

@Component({
    selector: 'jhi-product-attribute-update',
    templateUrl: './product-attribute-update.component.html'
})
export class ProductAttributeUpdateComponent implements OnInit {
    productAttribute: IProductAttribute;
    isSaving: boolean;

    productattributesets: IProductAttributeSet[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productAttributeService: ProductAttributeService,
        protected productAttributeSetService: ProductAttributeSetService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productAttribute }) => {
            this.productAttribute = productAttribute;
        });
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productAttribute.id !== undefined) {
            this.subscribeToSaveResponse(this.productAttributeService.update(this.productAttribute));
        } else {
            this.subscribeToSaveResponse(this.productAttributeService.create(this.productAttribute));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductAttribute>>) {
        result.subscribe((res: HttpResponse<IProductAttribute>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductAttributeSetById(index: number, item: IProductAttributeSet) {
        return item.id;
    }
}
