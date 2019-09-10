import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMerchants } from 'app/shared/model/merchants.model';

@Component({
    selector: 'jhi-merchants-detail',
    templateUrl: './merchants-detail.component.html'
})
export class MerchantsDetailComponent implements OnInit {
    merchants: IMerchants;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ merchants }) => {
            this.merchants = merchants;
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
