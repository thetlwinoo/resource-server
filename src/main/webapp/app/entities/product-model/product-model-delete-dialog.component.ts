import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductModel } from 'app/shared/model/product-model.model';
import { ProductModelService } from './product-model.service';

@Component({
    selector: 'jhi-product-model-delete-dialog',
    templateUrl: './product-model-delete-dialog.component.html'
})
export class ProductModelDeleteDialogComponent {
    productModel: IProductModel;

    constructor(
        protected productModelService: ProductModelService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productModelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productModelListModification',
                content: 'Deleted an productModel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-model-delete-popup',
    template: ''
})
export class ProductModelDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productModel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductModelDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productModel = productModel;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/product-model', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/product-model', { outlets: { popup: null } }]);
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
