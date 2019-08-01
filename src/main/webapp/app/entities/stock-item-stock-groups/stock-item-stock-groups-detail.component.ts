import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockItemStockGroups } from 'app/shared/model/stock-item-stock-groups.model';

@Component({
    selector: 'jhi-stock-item-stock-groups-detail',
    templateUrl: './stock-item-stock-groups-detail.component.html'
})
export class StockItemStockGroupsDetailComponent implements OnInit {
    stockItemStockGroups: IStockItemStockGroups;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockItemStockGroups }) => {
            this.stockItemStockGroups = stockItemStockGroups;
        });
    }

    previousState() {
        window.history.back();
    }
}
