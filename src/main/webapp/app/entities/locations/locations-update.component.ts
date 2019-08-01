import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILocations } from 'app/shared/model/locations.model';
import { LocationsService } from './locations.service';

@Component({
    selector: 'jhi-locations-update',
    templateUrl: './locations-update.component.html'
})
export class LocationsUpdateComponent implements OnInit {
    locations: ILocations;
    isSaving: boolean;

    constructor(protected locationsService: LocationsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ locations }) => {
            this.locations = locations;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.locations.id !== undefined) {
            this.subscribeToSaveResponse(this.locationsService.update(this.locations));
        } else {
            this.subscribeToSaveResponse(this.locationsService.create(this.locations));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocations>>) {
        result.subscribe((res: HttpResponse<ILocations>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
