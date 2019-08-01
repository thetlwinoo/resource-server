import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from './customers.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';

@Component({
    selector: 'jhi-customers-update',
    templateUrl: './customers-update.component.html'
})
export class CustomersUpdateComponent implements OnInit {
    customers: ICustomers;
    isSaving: boolean;

    people: IPeople[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected customersService: CustomersService,
        protected peopleService: PeopleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ customers }) => {
            this.customers = customers;
        });
        this.peopleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customers.id !== undefined) {
            this.subscribeToSaveResponse(this.customersService.update(this.customers));
        } else {
            this.subscribeToSaveResponse(this.customersService.create(this.customers));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomers>>) {
        result.subscribe((res: HttpResponse<ICustomers>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
