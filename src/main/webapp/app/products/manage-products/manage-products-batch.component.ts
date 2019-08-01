import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
// import {Message} from "primeng/api";
import { MenuItem, Message } from 'primeng/components/common/api';
import { ManageProductsService } from './manage-products.service';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'jhi-products-detail',
    templateUrl: './manage-products-batch.component.html'
})
export class ManageProductsBatchComponent implements OnInit {
    uploadedFiles: any[] = [];
    uploadMsgs: Message[] = [];
    isUploaded = false;

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected productsService: ManageProductsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        // this.activatedRoute.data.subscribe(({ products }) => {
        //     this.products = products;
        // });
    }

    onUpload(event: any) {
        for (const file of event.files) {
            this.uploadedFiles.push(file);
        }

        this.subscribeToUploadResponse(this.productsService.upload(this.uploadedFiles[0]));

        this.uploadMsgs = [];
        this.uploadMsgs.push({ severity: 'info', summary: 'File Uploaded', detail: '' });
    }

    protected subscribeToUploadResponse(result: Observable<HttpResponse<any>>) {
        result.subscribe((res: HttpResponse<any>) => this.onUploadSuccess(res), (res: HttpErrorResponse) => this.onUploadError());
    }

    protected onUploadSuccess(res) {
        console.log('upload success', res);
        this.isUploaded = true;
        this.previousState();
    }

    protected onUploadError() {
        console.log('upload failed');
        this.isUploaded = false;
    }

    previousState() {
        window.history.back();
    }
}
