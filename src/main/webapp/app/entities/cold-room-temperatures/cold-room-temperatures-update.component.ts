import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';
import { ColdRoomTemperaturesService } from './cold-room-temperatures.service';

@Component({
    selector: 'jhi-cold-room-temperatures-update',
    templateUrl: './cold-room-temperatures-update.component.html'
})
export class ColdRoomTemperaturesUpdateComponent implements OnInit {
    coldRoomTemperatures: IColdRoomTemperatures;
    isSaving: boolean;
    recordedWhenDp: any;
    validFromDp: any;
    validToDp: any;

    constructor(protected coldRoomTemperaturesService: ColdRoomTemperaturesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ coldRoomTemperatures }) => {
            this.coldRoomTemperatures = coldRoomTemperatures;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.coldRoomTemperatures.id !== undefined) {
            this.subscribeToSaveResponse(this.coldRoomTemperaturesService.update(this.coldRoomTemperatures));
        } else {
            this.subscribeToSaveResponse(this.coldRoomTemperaturesService.create(this.coldRoomTemperatures));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IColdRoomTemperatures>>) {
        result.subscribe(
            (res: HttpResponse<IColdRoomTemperatures>) => this.onSaveSuccess(),
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
}
