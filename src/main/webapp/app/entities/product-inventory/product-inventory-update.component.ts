import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductInventory } from 'app/shared/model/product-inventory.model';
import { ProductInventoryService } from './product-inventory.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';
import { ILocations } from 'app/shared/model/locations.model';
import { LocationsService } from 'app/entities/locations';

@Component({
    selector: 'jhi-product-inventory-update',
    templateUrl: './product-inventory-update.component.html'
})
export class ProductInventoryUpdateComponent implements OnInit {
    productInventory: IProductInventory;
    isSaving: boolean;

    products: IProducts[];

    locations: ILocations[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productInventoryService: ProductInventoryService,
        protected productsService: ProductsService,
        protected locationsService: LocationsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productInventory }) => {
            this.productInventory = productInventory;
        });
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.locationsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILocations[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILocations[]>) => response.body)
            )
            .subscribe((res: ILocations[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productInventory.id !== undefined) {
            this.subscribeToSaveResponse(this.productInventoryService.update(this.productInventory));
        } else {
            this.subscribeToSaveResponse(this.productInventoryService.create(this.productInventory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductInventory>>) {
        result.subscribe((res: HttpResponse<IProductInventory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLocationsById(index: number, item: ILocations) {
        return item.id;
    }
}
