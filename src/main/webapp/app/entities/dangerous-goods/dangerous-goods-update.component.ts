import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDangerousGoods } from 'app/shared/model/dangerous-goods.model';
import { DangerousGoodsService } from './dangerous-goods.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items';

@Component({
    selector: 'jhi-dangerous-goods-update',
    templateUrl: './dangerous-goods-update.component.html'
})
export class DangerousGoodsUpdateComponent implements OnInit {
    dangerousGoods: IDangerousGoods;
    isSaving: boolean;

    stockitems: IStockItems[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dangerousGoodsService: DangerousGoodsService,
        protected stockItemsService: StockItemsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dangerousGoods }) => {
            this.dangerousGoods = dangerousGoods;
        });
        this.stockItemsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockItems[]>) => response.body)
            )
            .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dangerousGoods.id !== undefined) {
            this.subscribeToSaveResponse(this.dangerousGoodsService.update(this.dangerousGoods));
        } else {
            this.subscribeToSaveResponse(this.dangerousGoodsService.create(this.dangerousGoods));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDangerousGoods>>) {
        result.subscribe((res: HttpResponse<IDangerousGoods>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStockItemsById(index: number, item: IStockItems) {
        return item.id;
    }
}
