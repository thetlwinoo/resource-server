import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBusinessEntityContact } from 'app/shared/model/business-entity-contact.model';
import { BusinessEntityContactService } from './business-entity-contact.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';
import { IContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from 'app/entities/contact-type';

@Component({
    selector: 'jhi-business-entity-contact-update',
    templateUrl: './business-entity-contact-update.component.html'
})
export class BusinessEntityContactUpdateComponent implements OnInit {
    businessEntityContact: IBusinessEntityContact;
    isSaving: boolean;

    people: IPeople[];

    contacttypes: IContactType[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected businessEntityContactService: BusinessEntityContactService,
        protected peopleService: PeopleService,
        protected contactTypeService: ContactTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ businessEntityContact }) => {
            this.businessEntityContact = businessEntityContact;
        });
        this.peopleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.contactTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IContactType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IContactType[]>) => response.body)
            )
            .subscribe((res: IContactType[]) => (this.contacttypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.businessEntityContact.id !== undefined) {
            this.subscribeToSaveResponse(this.businessEntityContactService.update(this.businessEntityContact));
        } else {
            this.subscribeToSaveResponse(this.businessEntityContactService.create(this.businessEntityContact));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessEntityContact>>) {
        result.subscribe(
            (res: HttpResponse<IBusinessEntityContact>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackContactTypeById(index: number, item: IContactType) {
        return item.id;
    }
}
