import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductPhoto } from 'app/shared/model/product-photo.model';
import { ProductPhotoService } from './product-photo.service';

@Component({
    selector: 'jhi-product-photo-delete-dialog',
    templateUrl: './product-photo-delete-dialog.component.html'
})
export class ProductPhotoDeleteDialogComponent {
    productPhoto: IProductPhoto;

    constructor(
        protected productPhotoService: ProductPhotoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productPhotoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productPhotoListModification',
                content: 'Deleted an productPhoto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-photo-delete-popup',
    template: ''
})
export class ProductPhotoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productPhoto }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductPhotoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productPhoto = productPhoto;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/product-photo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/product-photo', { outlets: { popup: null } }]);
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
