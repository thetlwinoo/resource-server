import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMerchants } from 'app/shared/model/merchants.model';
import { MerchantsService } from './merchants.service';

@Component({
    selector: 'jhi-merchants-delete-dialog',
    templateUrl: './merchants-delete-dialog.component.html'
})
export class MerchantsDeleteDialogComponent {
    merchants: IMerchants;

    constructor(
        protected merchantsService: MerchantsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.merchantsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'merchantsListModification',
                content: 'Deleted an merchants'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-merchants-delete-popup',
    template: ''
})
export class MerchantsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ merchants }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MerchantsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.merchants = merchants;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/merchants', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/merchants', { outlets: { popup: null } }]);
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
