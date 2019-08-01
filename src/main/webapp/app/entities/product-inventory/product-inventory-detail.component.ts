import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductInventory } from 'app/shared/model/product-inventory.model';

@Component({
    selector: 'jhi-product-inventory-detail',
    templateUrl: './product-inventory-detail.component.html'
})
export class ProductInventoryDetailComponent implements OnInit {
    productInventory: IProductInventory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productInventory }) => {
            this.productInventory = productInventory;
        });
    }

    previousState() {
        window.history.back();
    }
}
