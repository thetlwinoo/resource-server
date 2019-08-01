import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompareProducts } from 'app/shared/model/compare-products.model';
import { CompareProductsService } from './compare-products.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';
import { ICompares } from 'app/shared/model/compares.model';
import { ComparesService } from 'app/entities/compares';

@Component({
    selector: 'jhi-compare-products-update',
    templateUrl: './compare-products-update.component.html'
})
export class CompareProductsUpdateComponent implements OnInit {
    compareProducts: ICompareProducts;
    isSaving: boolean;

    products: IProducts[];

    compares: ICompares[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected compareProductsService: CompareProductsService,
        protected productsService: ProductsService,
        protected comparesService: ComparesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compareProducts }) => {
            this.compareProducts = compareProducts;
        });
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.comparesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompares[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompares[]>) => response.body)
            )
            .subscribe((res: ICompares[]) => (this.compares = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.compareProducts.id !== undefined) {
            this.subscribeToSaveResponse(this.compareProductsService.update(this.compareProducts));
        } else {
            this.subscribeToSaveResponse(this.compareProductsService.create(this.compareProducts));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompareProducts>>) {
        result.subscribe((res: HttpResponse<ICompareProducts>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }

    trackComparesById(index: number, item: ICompares) {
        return item.id;
    }
}
