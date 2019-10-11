import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from './stock-items.service';
import { IReviewLines } from 'app/shared/model/review-lines.model';
import { ReviewLinesService } from 'app/entities/review-lines';
import { IUnitMeasure } from 'app/shared/model/unit-measure.model';
import { UnitMeasureService } from 'app/entities/unit-measure';
import { IProductAttribute } from 'app/shared/model/product-attribute.model';
import { ProductAttributeService } from 'app/entities/product-attribute';
import { IProductOption } from 'app/shared/model/product-option.model';
import { ProductOptionService } from 'app/entities/product-option';
import { IMaterials } from 'app/shared/model/materials.model';
import { MaterialsService } from 'app/entities/materials';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';
import { IBarcodeTypes } from 'app/shared/model/barcode-types.model';
import { BarcodeTypesService } from 'app/entities/barcode-types';
import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';
import { StockItemHoldingsService } from 'app/entities/stock-item-holdings';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';

@Component({
    selector: 'jhi-stock-items-update',
    templateUrl: './stock-items-update.component.html'
})
export class StockItemsUpdateComponent implements OnInit {
    stockItems: IStockItems;
    isSaving: boolean;

    stockitemonreviewlines: IReviewLines[];

    unitmeasures: IUnitMeasure[];

    productattributes: IProductAttribute[];

    productoptions: IProductOption[];

    materials: IMaterials[];

    currencies: ICurrency[];

    barcodetypes: IBarcodeTypes[];

    stockitemholdings: IStockItemHoldings[];

    products: IProducts[];
    sellStartDateDp: any;
    sellEndDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockItemsService: StockItemsService,
        protected reviewLinesService: ReviewLinesService,
        protected unitMeasureService: UnitMeasureService,
        protected productAttributeService: ProductAttributeService,
        protected productOptionService: ProductOptionService,
        protected materialsService: MaterialsService,
        protected currencyService: CurrencyService,
        protected barcodeTypesService: BarcodeTypesService,
        protected stockItemHoldingsService: StockItemHoldingsService,
        protected productsService: ProductsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockItems }) => {
            this.stockItems = stockItems;
        });
        this.reviewLinesService
            .query({ filter: 'stockitem-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IReviewLines[]>) => mayBeOk.ok),
                map((response: HttpResponse<IReviewLines[]>) => response.body)
            )
            .subscribe(
                (res: IReviewLines[]) => {
                    if (!this.stockItems.stockItemOnReviewLineId) {
                        this.stockitemonreviewlines = res;
                    } else {
                        this.reviewLinesService
                            .find(this.stockItems.stockItemOnReviewLineId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IReviewLines>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IReviewLines>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IReviewLines) => (this.stockitemonreviewlines = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.unitMeasureService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUnitMeasure[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUnitMeasure[]>) => response.body)
            )
            .subscribe((res: IUnitMeasure[]) => (this.unitmeasures = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productAttributeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductAttribute[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductAttribute[]>) => response.body)
            )
            .subscribe((res: IProductAttribute[]) => (this.productattributes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productOptionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductOption[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductOption[]>) => response.body)
            )
            .subscribe((res: IProductOption[]) => (this.productoptions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.materialsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMaterials[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMaterials[]>) => response.body)
            )
            .subscribe((res: IMaterials[]) => (this.materials = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.currencyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICurrency[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICurrency[]>) => response.body)
            )
            .subscribe((res: ICurrency[]) => (this.currencies = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.barcodeTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBarcodeTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBarcodeTypes[]>) => response.body)
            )
            .subscribe((res: IBarcodeTypes[]) => (this.barcodetypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.stockItemHoldingsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockItemHoldings[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockItemHoldings[]>) => response.body)
            )
            .subscribe(
                (res: IStockItemHoldings[]) => (this.stockitemholdings = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
        if (this.stockItems.id !== undefined) {
            this.subscribeToSaveResponse(this.stockItemsService.update(this.stockItems));
        } else {
            this.subscribeToSaveResponse(this.stockItemsService.create(this.stockItems));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItems>>) {
        result.subscribe((res: HttpResponse<IStockItems>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackReviewLinesById(index: number, item: IReviewLines) {
        return item.id;
    }

    trackUnitMeasureById(index: number, item: IUnitMeasure) {
        return item.id;
    }

    trackProductAttributeById(index: number, item: IProductAttribute) {
        return item.id;
    }

    trackProductOptionById(index: number, item: IProductOption) {
        return item.id;
    }

    trackMaterialsById(index: number, item: IMaterials) {
        return item.id;
    }

    trackCurrencyById(index: number, item: ICurrency) {
        return item.id;
    }

    trackBarcodeTypesById(index: number, item: IBarcodeTypes) {
        return item.id;
    }

    trackStockItemHoldingsById(index: number, item: IStockItemHoldings) {
        return item.id;
    }

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }
}
