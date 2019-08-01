import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPersonEmailAddress } from 'app/shared/model/person-email-address.model';
import { PersonEmailAddressService } from './person-email-address.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';

@Component({
    selector: 'jhi-person-email-address-update',
    templateUrl: './person-email-address-update.component.html'
})
export class PersonEmailAddressUpdateComponent implements OnInit {
    personEmailAddress: IPersonEmailAddress;
    isSaving: boolean;

    people: IPeople[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected personEmailAddressService: PersonEmailAddressService,
        protected peopleService: PeopleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ personEmailAddress }) => {
            this.personEmailAddress = personEmailAddress;
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
        if (this.personEmailAddress.id !== undefined) {
            this.subscribeToSaveResponse(this.personEmailAddressService.update(this.personEmailAddress));
        } else {
            this.subscribeToSaveResponse(this.personEmailAddressService.create(this.personEmailAddress));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonEmailAddress>>) {
        result.subscribe((res: HttpResponse<IPersonEmailAddress>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
