import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IShipMethod } from 'app/shared/model/ship-method.model';
import { ShipMethodService } from './ship-method.service';

@Component({
    selector: 'jhi-ship-method-update',
    templateUrl: './ship-method-update.component.html'
})
export class ShipMethodUpdateComponent implements OnInit {
    shipMethod: IShipMethod;
    isSaving: boolean;

    constructor(protected shipMethodService: ShipMethodService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ shipMethod }) => {
            this.shipMethod = shipMethod;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shipMethod.id !== undefined) {
            this.subscribeToSaveResponse(this.shipMethodService.update(this.shipMethod));
        } else {
            this.subscribeToSaveResponse(this.shipMethodService.create(this.shipMethod));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipMethod>>) {
        result.subscribe((res: HttpResponse<IShipMethod>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
