import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductTransactions } from 'app/shared/model/product-transactions.model';

@Component({
    selector: 'jhi-product-transactions-detail',
    templateUrl: './product-transactions-detail.component.html'
})
export class ProductTransactionsDetailComponent implements OnInit {
    productTransactions: IProductTransactions;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productTransactions }) => {
            this.productTransactions = productTransactions;
        });
    }

    previousState() {
        window.history.back();
    }
}
