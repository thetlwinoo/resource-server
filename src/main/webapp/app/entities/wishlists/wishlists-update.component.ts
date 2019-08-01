import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IWishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from './wishlists.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';

@Component({
    selector: 'jhi-wishlists-update',
    templateUrl: './wishlists-update.component.html'
})
export class WishlistsUpdateComponent implements OnInit {
    wishlists: IWishlists;
    isSaving: boolean;

    wishlistusers: IPeople[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected wishlistsService: WishlistsService,
        protected peopleService: PeopleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wishlists }) => {
            this.wishlists = wishlists;
        });
        this.peopleService
            .query({ filter: 'wishlist-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe(
                (res: IPeople[]) => {
                    if (!this.wishlists.wishlistUserId) {
                        this.wishlistusers = res;
                    } else {
                        this.peopleService
                            .find(this.wishlists.wishlistUserId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPeople>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPeople>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPeople) => (this.wishlistusers = [subRes].concat(res)),
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
        if (this.wishlists.id !== undefined) {
            this.subscribeToSaveResponse(this.wishlistsService.update(this.wishlists));
        } else {
            this.subscribeToSaveResponse(this.wishlistsService.create(this.wishlists));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IWishlists>>) {
        result.subscribe((res: HttpResponse<IWishlists>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
