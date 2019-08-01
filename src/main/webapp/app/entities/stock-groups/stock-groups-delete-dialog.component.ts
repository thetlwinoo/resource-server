import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockGroups } from 'app/shared/model/stock-groups.model';
import { StockGroupsService } from './stock-groups.service';

@Component({
    selector: 'jhi-stock-groups-delete-dialog',
    templateUrl: './stock-groups-delete-dialog.component.html'
})
export class StockGroupsDeleteDialogComponent {
    stockGroups: IStockGroups;

    constructor(
        protected stockGroupsService: StockGroupsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stockGroupsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'stockGroupsListModification',
                content: 'Deleted an stockGroups'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stock-groups-delete-popup',
    template: ''
})
export class StockGroupsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockGroups }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockGroupsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.stockGroups = stockGroups;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/stock-groups', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/stock-groups', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
