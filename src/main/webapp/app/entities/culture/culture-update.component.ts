import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICulture } from 'app/shared/model/culture.model';
import { CultureService } from './culture.service';

@Component({
    selector: 'jhi-culture-update',
    templateUrl: './culture-update.component.html'
})
export class CultureUpdateComponent implements OnInit {
    culture: ICulture;
    isSaving: boolean;

    constructor(protected cultureService: CultureService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ culture }) => {
            this.culture = culture;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.culture.id !== undefined) {
            this.subscribeToSaveResponse(this.cultureService.update(this.culture));
        } else {
            this.subscribeToSaveResponse(this.cultureService.create(this.culture));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICulture>>) {
        result.subscribe((res: HttpResponse<ICulture>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
