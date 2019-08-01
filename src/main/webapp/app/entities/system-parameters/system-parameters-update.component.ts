import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISystemParameters } from 'app/shared/model/system-parameters.model';
import { SystemParametersService } from './system-parameters.service';
import { ICities } from 'app/shared/model/cities.model';
import { CitiesService } from 'app/entities/cities';

@Component({
    selector: 'jhi-system-parameters-update',
    templateUrl: './system-parameters-update.component.html'
})
export class SystemParametersUpdateComponent implements OnInit {
    systemParameters: ISystemParameters;
    isSaving: boolean;

    cities: ICities[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected systemParametersService: SystemParametersService,
        protected citiesService: CitiesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ systemParameters }) => {
            this.systemParameters = systemParameters;
        });
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
        if (this.systemParameters.id !== undefined) {
            this.subscribeToSaveResponse(this.systemParametersService.update(this.systemParameters));
        } else {
            this.subscribeToSaveResponse(this.systemParametersService.create(this.systemParameters));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystemParameters>>) {
        result.subscribe((res: HttpResponse<ISystemParameters>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCitiesById(index: number, item: ICities) {
        return item.id;
    }
}
