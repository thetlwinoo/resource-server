import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';
import { ShoppingCartItemsService } from './shopping-cart-items.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from 'app/entities/shopping-carts';

@Component({
    selector: 'jhi-shopping-cart-items-update',
    templateUrl: './shopping-cart-items-update.component.html'
})
export class ShoppingCartItemsUpdateComponent implements OnInit {
    shoppingCartItems: IShoppingCartItems;
    isSaving: boolean;

    products: IProducts[];

    shoppingcarts: IShoppingCarts[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected shoppingCartItemsService: ShoppingCartItemsService,
        protected productsService: ProductsService,
        protected shoppingCartsService: ShoppingCartsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ shoppingCartItems }) => {
            this.shoppingCartItems = shoppingCartItems;
        });
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.shoppingCartsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IShoppingCarts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IShoppingCarts[]>) => response.body)
            )
            .subscribe((res: IShoppingCarts[]) => (this.shoppingcarts = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shoppingCartItems.id !== undefined) {
            this.subscribeToSaveResponse(this.shoppingCartItemsService.update(this.shoppingCartItems));
        } else {
            this.subscribeToSaveResponse(this.shoppingCartItemsService.create(this.shoppingCartItems));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IShoppingCartItems>>) {
        result.subscribe((res: HttpResponse<IShoppingCartItems>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackShoppingCartsById(index: number, item: IShoppingCarts) {
        return item.id;
    }
}
