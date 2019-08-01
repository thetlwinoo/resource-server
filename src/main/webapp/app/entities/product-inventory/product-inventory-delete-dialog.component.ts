import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductInventory } from 'app/shared/model/product-inventory.model';
import { ProductInventoryService } from './product-inventory.service';

@Component({
    selector: 'jhi-product-inventory-delete-dialog',
    templateUrl: './product-inventory-delete-dialog.component.html'
})
export class ProductInventoryDeleteDialogComponent {
    productInventory: IProductInventory;

    constructor(
        protected productInventoryService: ProductInventoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productInventoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productInventoryListModification',
                content: 'Deleted an productInventory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-inventory-delete-popup',
    template: ''
})
export class ProductInventoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productInventory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductInventoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productInventory = productInventory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/product-inventory', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/product-inventory', { outlets: { popup: null } }]);
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
