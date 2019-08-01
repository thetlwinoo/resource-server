import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from 'app/entities/shopping-carts';
import { IWishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from 'app/entities/wishlists';
import { ICompares } from 'app/shared/model/compares.model';
import { ComparesService } from 'app/entities/compares';

@Component({
    selector: 'jhi-people-update',
    templateUrl: './people-update.component.html'
})
export class PeopleUpdateComponent implements OnInit {
    people: IPeople;
    isSaving: boolean;

    shoppingcarts: IShoppingCarts[];

    wishlists: IWishlists[];

    compares: ICompares[];
    validFromDp: any;
    validToDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected peopleService: PeopleService,
        protected shoppingCartsService: ShoppingCartsService,
        protected wishlistsService: WishlistsService,
        protected comparesService: ComparesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ people }) => {
            this.people = people;
        });
        this.shoppingCartsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IShoppingCarts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IShoppingCarts[]>) => response.body)
            )
            .subscribe((res: IShoppingCarts[]) => (this.shoppingcarts = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.wishlistsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IWishlists[]>) => mayBeOk.ok),
                map((response: HttpResponse<IWishlists[]>) => response.body)
            )
            .subscribe((res: IWishlists[]) => (this.wishlists = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.comparesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompares[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompares[]>) => response.body)
            )
            .subscribe((res: ICompares[]) => (this.compares = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.people.id !== undefined) {
            this.subscribeToSaveResponse(this.peopleService.update(this.people));
        } else {
            this.subscribeToSaveResponse(this.peopleService.create(this.people));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeople>>) {
        result.subscribe((res: HttpResponse<IPeople>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackShoppingCartsById(index: number, item: IShoppingCarts) {
        return item.id;
    }

    trackWishlistsById(index: number, item: IWishlists) {
        return item.id;
    }

    trackComparesById(index: number, item: ICompares) {
        return item.id;
    }
}
