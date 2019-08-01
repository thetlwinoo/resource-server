import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockGroups } from 'app/shared/model/stock-groups.model';

@Component({
    selector: 'jhi-stock-groups-detail',
    templateUrl: './stock-groups-detail.component.html'
})
export class StockGroupsDetailComponent implements OnInit {
    stockGroups: IStockGroups;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockGroups }) => {
            this.stockGroups = stockGroups;
        });
    }

    previousState() {
        window.history.back();
    }
}
