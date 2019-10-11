import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMaterials } from 'app/shared/model/materials.model';
import { MaterialsService } from './materials.service';

@Component({
    selector: 'jhi-materials-update',
    templateUrl: './materials-update.component.html'
})
export class MaterialsUpdateComponent implements OnInit {
    materials: IMaterials;
    isSaving: boolean;

    constructor(protected materialsService: MaterialsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ materials }) => {
            this.materials = materials;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.materials.id !== undefined) {
            this.subscribeToSaveResponse(this.materialsService.update(this.materials));
        } else {
            this.subscribeToSaveResponse(this.materialsService.create(this.materials));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaterials>>) {
        result.subscribe((res: HttpResponse<IMaterials>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
