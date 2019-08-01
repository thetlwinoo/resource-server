import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ICountries } from 'app/shared/model/countries.model';
import { CountriesService } from './countries.service';

@Component({
    selector: 'jhi-countries-update',
    templateUrl: './countries-update.component.html'
})
export class CountriesUpdateComponent implements OnInit {
    countries: ICountries;
    isSaving: boolean;
    validFromDp: any;
    validToDp: any;

    constructor(protected countriesService: CountriesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ countries }) => {
            this.countries = countries;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.countries.id !== undefined) {
            this.subscribeToSaveResponse(this.countriesService.update(this.countries));
        } else {
            this.subscribeToSaveResponse(this.countriesService.create(this.countries));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICountries>>) {
        result.subscribe((res: HttpResponse<ICountries>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
