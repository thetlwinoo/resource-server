import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from './special-deals.service';
import { IBuyingGroups } from 'app/shared/model/buying-groups.model';
import { BuyingGroupsService } from 'app/entities/buying-groups';
import { ICustomerCategories } from 'app/shared/model/customer-categories.model';
import { CustomerCategoriesService } from 'app/entities/customer-categories';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items';

@Component({
    selector: 'jhi-special-deals-update',
    templateUrl: './special-deals-update.component.html'
})
export class SpecialDealsUpdateComponent implements OnInit {
    specialDeals: ISpecialDeals;
    isSaving: boolean;

    buyinggroups: IBuyingGroups[];

    customercategories: ICustomerCategories[];

    customers: ICustomers[];

    productcategories: IProductCategory[];

    stockitems: IStockItems[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected specialDealsService: SpecialDealsService,
        protected buyingGroupsService: BuyingGroupsService,
        protected customerCategoriesService: CustomerCategoriesService,
        protected customersService: CustomersService,
        protected productCategoryService: ProductCategoryService,
        protected stockItemsService: StockItemsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ specialDeals }) => {
            this.specialDeals = specialDeals;
        });
        this.buyingGroupsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBuyingGroups[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBuyingGroups[]>) => response.body)
            )
            .subscribe((res: IBuyingGroups[]) => (this.buyinggroups = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.customerCategoriesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomerCategories[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomerCategories[]>) => response.body)
            )
            .subscribe(
                (res: ICustomerCategories[]) => (this.customercategories = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.customersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomers[]>) => response.body)
            )
            .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.stockItemsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockItems[]>) => response.body)
            )
            .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.specialDeals.id !== undefined) {
            this.subscribeToSaveResponse(this.specialDealsService.update(this.specialDeals));
        } else {
            this.subscribeToSaveResponse(this.specialDealsService.create(this.specialDeals));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialDeals>>) {
        result.subscribe((res: HttpResponse<ISpecialDeals>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBuyingGroupsById(index: number, item: IBuyingGroups) {
        return item.id;
    }

    trackCustomerCategoriesById(index: number, item: ICustomerCategories) {
        return item.id;
    }

    trackCustomersById(index: number, item: ICustomers) {
        return item.id;
    }

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackStockItemsById(index: number, item: IStockItems) {
        return item.id;
    }
}
