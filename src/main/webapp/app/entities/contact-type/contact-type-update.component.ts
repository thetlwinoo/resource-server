import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from './contact-type.service';

@Component({
    selector: 'jhi-contact-type-update',
    templateUrl: './contact-type-update.component.html'
})
export class ContactTypeUpdateComponent implements OnInit {
    contactType: IContactType;
    isSaving: boolean;

    constructor(protected contactTypeService: ContactTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contactType }) => {
            this.contactType = contactType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contactType.id !== undefined) {
            this.subscribeToSaveResponse(this.contactTypeService.update(this.contactType));
        } else {
            this.subscribeToSaveResponse(this.contactTypeService.create(this.contactType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactType>>) {
        result.subscribe((res: HttpResponse<IContactType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
