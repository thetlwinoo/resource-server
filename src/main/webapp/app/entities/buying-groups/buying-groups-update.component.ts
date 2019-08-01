import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IBuyingGroups } from 'app/shared/model/buying-groups.model';
import { BuyingGroupsService } from './buying-groups.service';

@Component({
    selector: 'jhi-buying-groups-update',
    templateUrl: './buying-groups-update.component.html'
})
export class BuyingGroupsUpdateComponent implements OnInit {
    buyingGroups: IBuyingGroups;
    isSaving: boolean;
    validFromDp: any;
    validToDp: any;

    constructor(protected buyingGroupsService: BuyingGroupsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ buyingGroups }) => {
            this.buyingGroups = buyingGroups;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.buyingGroups.id !== undefined) {
            this.subscribeToSaveResponse(this.buyingGroupsService.update(this.buyingGroups));
        } else {
            this.subscribeToSaveResponse(this.buyingGroupsService.create(this.buyingGroups));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBuyingGroups>>) {
        result.subscribe((res: HttpResponse<IBuyingGroups>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
