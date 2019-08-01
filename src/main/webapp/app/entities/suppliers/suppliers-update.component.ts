import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from './suppliers.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';
import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';
import { SupplierCategoriesService } from 'app/entities/supplier-categories';
import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from 'app/entities/delivery-methods';
import { ICities } from 'app/shared/model/cities.model';
import { CitiesService } from 'app/entities/cities';

@Component({
    selector: 'jhi-suppliers-update',
    templateUrl: './suppliers-update.component.html'
})
export class SuppliersUpdateComponent implements OnInit {
    suppliers: ISuppliers;
    isSaving: boolean;

    people: IPeople[];

    suppliercategories: ISupplierCategories[];

    deliverymethods: IDeliveryMethods[];

    cities: ICities[];
    validFromDp: any;
    validToDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected suppliersService: SuppliersService,
        protected peopleService: PeopleService,
        protected supplierCategoriesService: SupplierCategoriesService,
        protected deliveryMethodsService: DeliveryMethodsService,
        protected citiesService: CitiesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ suppliers }) => {
            this.suppliers = suppliers;
        });
        this.peopleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.supplierCategoriesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplierCategories[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplierCategories[]>) => response.body)
            )
            .subscribe(
                (res: ISupplierCategories[]) => (this.suppliercategories = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.deliveryMethodsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDeliveryMethods[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDeliveryMethods[]>) => response.body)
            )
            .subscribe((res: IDeliveryMethods[]) => (this.deliverymethods = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.citiesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICities[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICities[]>) => response.body)
            )
            .subscribe((res: ICities[]) => (this.cities = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.suppliers.id !== undefined) {
            this.subscribeToSaveResponse(this.suppliersService.update(this.suppliers));
        } else {
            this.subscribeToSaveResponse(this.suppliersService.create(this.suppliers));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuppliers>>) {
        result.subscribe((res: HttpResponse<ISuppliers>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPeopleById(index: number, item: IPeople) {
        return item.id;
    }

    trackSupplierCategoriesById(index: number, item: ISupplierCategories) {
        return item.id;
    }

    trackDeliveryMethodsById(index: number, item: IDeliveryMethods) {
        return item.id;
    }

    trackCitiesById(index: number, item: ICities) {
        return item.id;
    }
}
