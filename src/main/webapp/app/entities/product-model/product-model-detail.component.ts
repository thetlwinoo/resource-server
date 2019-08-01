import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductModel } from 'app/shared/model/product-model.model';

@Component({
    selector: 'jhi-product-model-detail',
    templateUrl: './product-model-detail.component.html'
})
export class ProductModelDetailComponent implements OnInit {
    productModel: IProductModel;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productModel }) => {
            this.productModel = productModel;
        });
    }

    previousState() {
        window.history.back();
    }
}
