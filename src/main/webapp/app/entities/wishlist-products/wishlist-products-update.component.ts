import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IWishlistProducts } from 'app/shared/model/wishlist-products.model';
import { WishlistProductsService } from './wishlist-products.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';
import { IWishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from 'app/entities/wishlists';

@Component({
    selector: 'jhi-wishlist-products-update',
    templateUrl: './wishlist-products-update.component.html'
})
export class WishlistProductsUpdateComponent implements OnInit {
    wishlistProducts: IWishlistProducts;
    isSaving: boolean;

    products: IProducts[];

    wishlists: IWishlists[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected wishlistProductsService: WishlistProductsService,
        protected productsService: ProductsService,
        protected wishlistsService: WishlistsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wishlistProducts }) => {
            this.wishlistProducts = wishlistProducts;
        });
        this.productsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.wishlistsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IWishlists[]>) => mayBeOk.ok),
                map((response: HttpResponse<IWishlists[]>) => response.body)
            )
            .subscribe((res: IWishlists[]) => (this.wishlists = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.wishlistProducts.id !== undefined) {
            this.subscribeToSaveResponse(this.wishlistProductsService.update(this.wishlistProducts));
        } else {
            this.subscribeToSaveResponse(this.wishlistProductsService.create(this.wishlistProducts));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IWishlistProducts>>) {
        result.subscribe((res: HttpResponse<IWishlistProducts>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackWishlistsById(index: number, item: IWishlists) {
        return item.id;
    }
}
