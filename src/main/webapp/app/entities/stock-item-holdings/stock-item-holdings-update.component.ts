import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';
import { StockItemHoldingsService } from './stock-item-holdings.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';

@Component({
    selector: 'jhi-stock-item-holdings-update',
    templateUrl: './stock-item-holdings-update.component.html'
})
export class StockItemHoldingsUpdateComponent implements OnInit {
    stockItemHoldings: IStockItemHoldings;
    isSaving: boolean;

    products: IProducts[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockItemHoldingsService: StockItemHoldingsService,
        protected productsService: ProductsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockItemHoldings }) => {
            this.stockItemHoldings = stockItemHoldings;
        });
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
        if (this.stockItemHoldings.id !== undefined) {
            this.subscribeToSaveResponse(this.stockItemHoldingsService.update(this.stockItemHoldings));
        } else {
            this.subscribeToSaveResponse(this.stockItemHoldingsService.create(this.stockItemHoldings));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItemHoldings>>) {
        result.subscribe((res: HttpResponse<IStockItemHoldings>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
