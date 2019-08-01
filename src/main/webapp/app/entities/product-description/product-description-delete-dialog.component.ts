import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductDescription } from 'app/shared/model/product-description.model';
import { ProductDescriptionService } from './product-description.service';

@Component({
    selector: 'jhi-product-description-delete-dialog',
    templateUrl: './product-description-delete-dialog.component.html'
})
export class ProductDescriptionDeleteDialogComponent {
    productDescription: IProductDescription;

    constructor(
        protected productDescriptionService: ProductDescriptionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productDescriptionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productDescriptionListModification',
                content: 'Deleted an productDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-description-delete-popup',
    template: ''
})
export class ProductDescriptionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productDescription }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductDescriptionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productDescription = productDescription;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/product-description', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/product-description', { outlets: { popup: null } }]);
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
