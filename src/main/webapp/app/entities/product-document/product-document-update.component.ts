import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProductDocument } from 'app/shared/model/product-document.model';
import { ProductDocumentService } from './product-document.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';
import { ICulture } from 'app/shared/model/culture.model';
import { CultureService } from 'app/entities/culture';

@Component({
    selector: 'jhi-product-document-update',
    templateUrl: './product-document-update.component.html'
})
export class ProductDocumentUpdateComponent implements OnInit {
    productDocument: IProductDocument;
    isSaving: boolean;

    products: IProducts[];

    cultures: ICulture[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected productDocumentService: ProductDocumentService,
        protected productsService: ProductsService,
        protected cultureService: CultureService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productDocument }) => {
            this.productDocument = productDocument;
        });
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.cultureService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICulture[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICulture[]>) => response.body)
            )
            .subscribe((res: ICulture[]) => (this.cultures = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productDocument.id !== undefined) {
            this.subscribeToSaveResponse(this.productDocumentService.update(this.productDocument));
        } else {
            this.subscribeToSaveResponse(this.productDocumentService.create(this.productDocument));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductDocument>>) {
        result.subscribe((res: HttpResponse<IProductDocument>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }

    trackCultureById(index: number, item: ICulture) {
        return item.id;
    }
}
