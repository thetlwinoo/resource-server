import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProductModel } from 'app/shared/model/product-model.model';

@Component({
    selector: 'jhi-product-model-detail',
    templateUrl: './product-model-detail.component.html'
})
export class ProductModelDetailComponent implements OnInit {
    productModel: IProductModel;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productModel }) => {
            this.productModel = productModel;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
