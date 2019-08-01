import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductDescription } from 'app/shared/model/product-description.model';

@Component({
    selector: 'jhi-product-description-detail',
    templateUrl: './product-description-detail.component.html'
})
export class ProductDescriptionDetailComponent implements OnInit {
    productDescription: IProductDescription;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productDescription }) => {
            this.productDescription = productDescription;
        });
    }

    previousState() {
        window.history.back();
    }
}
