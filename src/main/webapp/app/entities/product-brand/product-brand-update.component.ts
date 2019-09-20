import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from './product-brand.service';
import { IMerchants } from 'app/shared/model/merchants.model';
import { MerchantsService } from 'app/entities/merchants';

@Component({
    selector: 'jhi-product-brand-update',
    templateUrl: './product-brand-update.component.html'
})
export class ProductBrandUpdateComponent implements OnInit {
    productBrand: IProductBrand;
    isSaving: boolean;

    merchants: IMerchants[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected productBrandService: ProductBrandService,
        protected merchantsService: MerchantsService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productBrand }) => {
            this.productBrand = productBrand;
        });
        this.merchantsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMerchants[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMerchants[]>) => response.body)
            )
            .subscribe((res: IMerchants[]) => (this.merchants = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.productBrand, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productBrand.id !== undefined) {
            this.subscribeToSaveResponse(this.productBrandService.update(this.productBrand));
        } else {
            this.subscribeToSaveResponse(this.productBrandService.create(this.productBrand));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductBrand>>) {
        result.subscribe((res: HttpResponse<IProductBrand>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMerchantsById(index: number, item: IMerchants) {
        return item.id;
    }
}
