import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from './shopping-carts.service';
import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from 'app/entities/special-deals';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers';

@Component({
    selector: 'jhi-shopping-carts-update',
    templateUrl: './shopping-carts-update.component.html'
})
export class ShoppingCartsUpdateComponent implements OnInit {
    shoppingCarts: IShoppingCarts;
    isSaving: boolean;

    specialdeals: ISpecialDeals[];

    cartusers: IPeople[];

    customers: ICustomers[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected shoppingCartsService: ShoppingCartsService,
        protected specialDealsService: SpecialDealsService,
        protected peopleService: PeopleService,
        protected customersService: CustomersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ shoppingCarts }) => {
            this.shoppingCarts = shoppingCarts;
        });
        this.specialDealsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISpecialDeals[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISpecialDeals[]>) => response.body)
            )
            .subscribe((res: ISpecialDeals[]) => (this.specialdeals = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.peopleService
            .query({ filter: 'cart-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe(
                (res: IPeople[]) => {
                    if (!this.shoppingCarts.cartUserId) {
                        this.cartusers = res;
                    } else {
                        this.peopleService
                            .find(this.shoppingCarts.cartUserId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPeople>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPeople>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPeople) => (this.cartusers = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.customersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomers[]>) => response.body)
            )
            .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shoppingCarts.id !== undefined) {
            this.subscribeToSaveResponse(this.shoppingCartsService.update(this.shoppingCarts));
        } else {
            this.subscribeToSaveResponse(this.shoppingCartsService.create(this.shoppingCarts));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IShoppingCarts>>) {
        result.subscribe((res: HttpResponse<IShoppingCarts>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSpecialDealsById(index: number, item: ISpecialDeals) {
        return item.id;
    }

    trackPeopleById(index: number, item: IPeople) {
        return item.id;
    }

    trackCustomersById(index: number, item: ICustomers) {
        return item.id;
    }
}
