import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IStateProvinces } from 'app/shared/model/state-provinces.model';
import { StateProvincesService } from './state-provinces.service';
import { ICountries } from 'app/shared/model/countries.model';
import { CountriesService } from 'app/entities/countries';

@Component({
    selector: 'jhi-state-provinces-update',
    templateUrl: './state-provinces-update.component.html'
})
export class StateProvincesUpdateComponent implements OnInit {
    stateProvinces: IStateProvinces;
    isSaving: boolean;

    countries: ICountries[];
    validFromDp: any;
    validToDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stateProvincesService: StateProvincesService,
        protected countriesService: CountriesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stateProvinces }) => {
            this.stateProvinces = stateProvinces;
        });
        this.countriesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICountries[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICountries[]>) => response.body)
            )
            .subscribe((res: ICountries[]) => (this.countries = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stateProvinces.id !== undefined) {
            this.subscribeToSaveResponse(this.stateProvincesService.update(this.stateProvinces));
        } else {
            this.subscribeToSaveResponse(this.stateProvincesService.create(this.stateProvinces));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStateProvinces>>) {
        result.subscribe((res: HttpResponse<IStateProvinces>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCountriesById(index: number, item: ICountries) {
        return item.id;
    }
}
