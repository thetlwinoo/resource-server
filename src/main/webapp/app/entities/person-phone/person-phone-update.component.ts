import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPersonPhone } from 'app/shared/model/person-phone.model';
import { PersonPhoneService } from './person-phone.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';
import { IPhoneNumberType } from 'app/shared/model/phone-number-type.model';
import { PhoneNumberTypeService } from 'app/entities/phone-number-type';

@Component({
    selector: 'jhi-person-phone-update',
    templateUrl: './person-phone-update.component.html'
})
export class PersonPhoneUpdateComponent implements OnInit {
    personPhone: IPersonPhone;
    isSaving: boolean;

    people: IPeople[];

    phonenumbertypes: IPhoneNumberType[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected personPhoneService: PersonPhoneService,
        protected peopleService: PeopleService,
        protected phoneNumberTypeService: PhoneNumberTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personPhone }) => {
            this.personPhone = personPhone;
        });
        this.peopleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.phoneNumberTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPhoneNumberType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPhoneNumberType[]>) => response.body)
            )
            .subscribe((res: IPhoneNumberType[]) => (this.phonenumbertypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.personPhone.id !== undefined) {
            this.subscribeToSaveResponse(this.personPhoneService.update(this.personPhone));
        } else {
            this.subscribeToSaveResponse(this.personPhoneService.create(this.personPhone));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonPhone>>) {
        result.subscribe((res: HttpResponse<IPersonPhone>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPhoneNumberTypeById(index: number, item: IPhoneNumberType) {
        return item.id;
    }
}
