import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductPhoto } from 'app/shared/model/product-photo.model';
import { ProductPhotoService } from 'app/entities/product-photo/product-photo.service';

@Component({
    selector: 'jhi-image-delete-dialog',
    templateUrl: './image-delete-dialog.component.html'
})
export class ImageDeleteDialogComponent {
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
                name: 'imageListModification',
                content: 'Deleted an image',
                response: response,
                data: this.productPhoto
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-image-delete-popup',
    template: ''
})
export class ImageDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        console.log('bluejekrjie');
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productPhoto }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ImageDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productPhoto = productPhoto;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/manage-images', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/manage-images', { outlets: { popup: null } }]);
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
