import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompares } from 'app/shared/model/compares.model';
import { ComparesService } from './compares.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';

@Component({
    selector: 'jhi-compares-update',
    templateUrl: './compares-update.component.html'
})
export class ComparesUpdateComponent implements OnInit {
    compares: ICompares;
    isSaving: boolean;

    compareusers: IPeople[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected comparesService: ComparesService,
        protected peopleService: PeopleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compares }) => {
            this.compares = compares;
        });
        this.peopleService
            .query({ filter: 'compare-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe(
                (res: IPeople[]) => {
                    if (!this.compares.compareUserId) {
                        this.compareusers = res;
                    } else {
                        this.peopleService
                            .find(this.compares.compareUserId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPeople>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPeople>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPeople) => (this.compareusers = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.compares.id !== undefined) {
            this.subscribeToSaveResponse(this.comparesService.update(this.compares));
        } else {
            this.subscribeToSaveResponse(this.comparesService.create(this.compares));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompares>>) {
        result.subscribe((res: HttpResponse<ICompares>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPeopleById(index: number, item: IPeople) {
        return item.id;
    }
}
