import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from './package-types.service';

@Component({
    selector: 'jhi-package-types-update',
    templateUrl: './package-types-update.component.html'
})
export class PackageTypesUpdateComponent implements OnInit {
    packageTypes: IPackageTypes;
    isSaving: boolean;
    validFromDp: any;
    validToDp: any;

    constructor(protected packageTypesService: PackageTypesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ packageTypes }) => {
            this.packageTypes = packageTypes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.packageTypes.id !== undefined) {
            this.subscribeToSaveResponse(this.packageTypesService.update(this.packageTypes));
        } else {
            this.subscribeToSaveResponse(this.packageTypesService.create(this.packageTypes));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPackageTypes>>) {
        result.subscribe((res: HttpResponse<IPackageTypes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
