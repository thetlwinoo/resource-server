import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductTransactions } from 'app/shared/model/product-transactions.model';
import { ProductTransactionsService } from './product-transactions.service';

@Component({
    selector: 'jhi-product-transactions-delete-dialog',
    templateUrl: './product-transactions-delete-dialog.component.html'
})
export class ProductTransactionsDeleteDialogComponent {
    productTransactions: IProductTransactions;

    constructor(
        protected productTransactionsService: ProductTransactionsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productTransactionsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productTransactionsListModification',
                content: 'Deleted an productTransactions'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-transactions-delete-popup',
    template: ''
})
export class ProductTransactionsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productTransactions }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductTransactionsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productTransactions = productTransactions;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/product-transactions', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/product-transactions', { outlets: { popup: null } }]);
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
