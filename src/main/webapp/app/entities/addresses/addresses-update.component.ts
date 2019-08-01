import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAddresses } from 'app/shared/model/addresses.model';
import { AddressesService } from './addresses.service';
import { IStateProvinces } from 'app/shared/model/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces';
import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AddressTypesService } from 'app/entities/address-types';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';

@Component({
    selector: 'jhi-addresses-update',
    templateUrl: './addresses-update.component.html'
})
export class AddressesUpdateComponent implements OnInit {
    addresses: IAddresses;
    isSaving: boolean;

    stateprovinces: IStateProvinces[];

    addresstypes: IAddressTypes[];

    people: IPeople[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected addressesService: AddressesService,
        protected stateProvincesService: StateProvincesService,
        protected addressTypesService: AddressTypesService,
        protected peopleService: PeopleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ addresses }) => {
            this.addresses = addresses;
        });
        this.stateProvincesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStateProvinces[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStateProvinces[]>) => response.body)
            )
            .subscribe((res: IStateProvinces[]) => (this.stateprovinces = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.addressTypesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAddressTypes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAddressTypes[]>) => response.body)
            )
            .subscribe((res: IAddressTypes[]) => (this.addresstypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.peopleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.addresses.id !== undefined) {
            this.subscribeToSaveResponse(this.addressesService.update(this.addresses));
        } else {
            this.subscribeToSaveResponse(this.addressesService.create(this.addresses));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddresses>>) {
        result.subscribe((res: HttpResponse<IAddresses>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStateProvincesById(index: number, item: IStateProvinces) {
        return item.id;
    }

    trackAddressTypesById(index: number, item: IAddressTypes) {
        return item.id;
    }

    trackPeopleById(index: number, item: IPeople) {
        return item.id;
    }
}
