import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICities } from 'app/shared/model/cities.model';
import { CitiesService } from './cities.service';
import { IStateProvinces } from 'app/shared/model/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces';

@Component({
    selector: 'jhi-cities-update',
    templateUrl: './cities-update.component.html'
})
export class CitiesUpdateComponent implements OnInit {
    cities: ICities;
    isSaving: boolean;

    stateprovinces: IStateProvinces[];
    validFromDp: any;
    validToDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected citiesService: CitiesService,
        protected stateProvincesService: StateProvincesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cities }) => {
            this.cities = cities;
        });
        this.stateProvincesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStateProvinces[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStateProvinces[]>) => response.body)
            )
            .subscribe((res: IStateProvinces[]) => (this.stateprovinces = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cities.id !== undefined) {
            this.subscribeToSaveResponse(this.citiesService.update(this.cities));
        } else {
            this.subscribeToSaveResponse(this.citiesService.create(this.cities));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICities>>) {
        result.subscribe((res: HttpResponse<ICities>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
