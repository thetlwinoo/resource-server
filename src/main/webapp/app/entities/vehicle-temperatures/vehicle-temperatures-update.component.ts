import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IVehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';
import { VehicleTemperaturesService } from './vehicle-temperatures.service';

@Component({
    selector: 'jhi-vehicle-temperatures-update',
    templateUrl: './vehicle-temperatures-update.component.html'
})
export class VehicleTemperaturesUpdateComponent implements OnInit {
    vehicleTemperatures: IVehicleTemperatures;
    isSaving: boolean;

    constructor(protected vehicleTemperaturesService: VehicleTemperaturesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vehicleTemperatures }) => {
            this.vehicleTemperatures = vehicleTemperatures;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vehicleTemperatures.id !== undefined) {
            this.subscribeToSaveResponse(this.vehicleTemperaturesService.update(this.vehicleTemperatures));
        } else {
            this.subscribeToSaveResponse(this.vehicleTemperaturesService.create(this.vehicleTemperatures));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleTemperatures>>) {
        result.subscribe((res: HttpResponse<IVehicleTemperatures>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
