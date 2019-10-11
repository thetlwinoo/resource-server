import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILastestMerchantUploadedDocument } from 'app/shared/model/lastest-merchant-uploaded-document.model';

@Component({
    selector: 'jhi-lastest-merchant-uploaded-document-detail',
    templateUrl: './lastest-merchant-uploaded-document-detail.component.html'
})
export class LastestMerchantUploadedDocumentDetailComponent implements OnInit {
    lastestMerchantUploadedDocument: ILastestMerchantUploadedDocument;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lastestMerchantUploadedDocument }) => {
            this.lastestMerchantUploadedDocument = lastestMerchantUploadedDocument;
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
