import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStockItemStockGroups } from 'app/shared/model/stock-item-stock-groups.model';
import { StockItemStockGroupsService } from './stock-item-stock-groups.service';
import { IStockGroups } from 'app/shared/model/stock-groups.model';
import { StockGroupsService } from 'app/entities/stock-groups';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';

@Component({
    selector: 'jhi-stock-item-stock-groups-update',
    templateUrl: './stock-item-stock-groups-update.component.html'
})
export class StockItemStockGroupsUpdateComponent implements OnInit {
    stockItemStockGroups: IStockItemStockGroups;
    isSaving: boolean;

    stockgroups: IStockGroups[];

    products: IProducts[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockItemStockGroupsService: StockItemStockGroupsService,
        protected stockGroupsService: StockGroupsService,
        protected productsService: ProductsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockItemStockGroups }) => {
            this.stockItemStockGroups = stockItemStockGroups;
        });
        this.stockGroupsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockGroups[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockGroups[]>) => response.body)
            )
            .subscribe((res: IStockGroups[]) => (this.stockgroups = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stockItemStockGroups.id !== undefined) {
            this.subscribeToSaveResponse(this.stockItemStockGroupsService.update(this.stockItemStockGroups));
        } else {
            this.subscribeToSaveResponse(this.stockItemStockGroupsService.create(this.stockItemStockGroups));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItemStockGroups>>) {
        result.subscribe(
            (res: HttpResponse<IStockItemStockGroups>) => this.onSaveSuccess(),
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

    trackStockGroupsById(index: number, item: IStockGroups) {
        return item.id;
    }

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }
}
