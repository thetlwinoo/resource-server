import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductModelDescription } from 'app/shared/model/product-model-description.model';

@Component({
    selector: 'jhi-product-model-description-detail',
    templateUrl: './product-model-description-detail.component.html'
})
export class ProductModelDescriptionDetailComponent implements OnInit {
    productModelDescription: IProductModelDescription;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productModelDescription }) => {
            this.productModelDescription = productModelDescription;
        });
    }

    previousState() {
        window.history.back();
    }
}
