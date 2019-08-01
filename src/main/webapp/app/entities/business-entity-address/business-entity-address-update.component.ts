import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBusinessEntityAddress } from 'app/shared/model/business-entity-address.model';
import { BusinessEntityAddressService } from './business-entity-address.service';
import { IAddresses } from 'app/shared/model/addresses.model';
import { AddressesService } from 'app/entities/addresses';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';
import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AddressTypesService } from 'app/entities/address-types';

@Component({
    selector: 'jhi-business-entity-address-update',
    templateUrl: './business-entity-address-update.component.html'
})
export class BusinessEntityAddressUpdateComponent implements OnInit {
    businessEntityAddress: IBusinessEntityAddress;
    isSaving: boolean;

    addresses: IAddresses[];

    people: IPeople[];

    addresstypes: IAddressTypes[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected businessEntityAddressService: BusinessEntityAddressService,
        protected addressesService: AddressesService,
        protected peopleService: PeopleService,
        protected addressTypesService: AddressTypesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ businessEntityAddress }) => {
            this.businessEntityAddress = businessEntityAddress;
        });
        this.addressesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAddresses[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAddresses[]>) => response.body)
            )
            .subscribe((res: IAddresses[]) => (this.addresses = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.peopleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.addressTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAddressTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAddressTypes[]>) => response.body)
            )
            .subscribe((res: IAddressTypes[]) => (this.addresstypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.businessEntityAddress.id !== undefined) {
            this.subscribeToSaveResponse(this.businessEntityAddressService.update(this.businessEntityAddress));
        } else {
            this.subscribeToSaveResponse(this.businessEntityAddressService.create(this.businessEntityAddress));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessEntityAddress>>) {
        result.subscribe(
            (res: HttpResponse<IBusinessEntityAddress>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackAddressesById(index: number, item: IAddresses) {
        return item.id;
    }

    trackPeopleById(index: number, item: IPeople) {
        return item.id;
    }

    trackAddressTypesById(index: number, item: IAddressTypes) {
        return item.id;
    }
}
