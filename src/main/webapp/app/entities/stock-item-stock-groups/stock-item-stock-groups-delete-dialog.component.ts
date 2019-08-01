import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItemStockGroups } from 'app/shared/model/stock-item-stock-groups.model';
import { StockItemStockGroupsService } from './stock-item-stock-groups.service';

@Component({
    selector: 'jhi-stock-item-stock-groups-delete-dialog',
    templateUrl: './stock-item-stock-groups-delete-dialog.component.html'
})
export class StockItemStockGroupsDeleteDialogComponent {
    stockItemStockGroups: IStockItemStockGroups;

    constructor(
        protected stockItemStockGroupsService: StockItemStockGroupsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stockItemStockGroupsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'stockItemStockGroupsListModification',
                content: 'Deleted an stockItemStockGroups'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stock-item-stock-groups-delete-popup',
    template: ''
})
export class StockItemStockGroupsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockItemStockGroups }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockItemStockGroupsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.stockItemStockGroups = stockItemStockGroups;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/stock-item-stock-groups', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/stock-item-stock-groups', { outlets: { popup: null } }]);
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
