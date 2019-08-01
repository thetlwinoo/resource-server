import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductSubCategory } from 'app/shared/model/product-sub-category.model';
import { ProductSubCategoryService } from './product-sub-category.service';

@Component({
    selector: 'jhi-product-sub-category-delete-dialog',
    templateUrl: './product-sub-category-delete-dialog.component.html'
})
export class ProductSubCategoryDeleteDialogComponent {
    productSubCategory: IProductSubCategory;

    constructor(
        protected productSubCategoryService: ProductSubCategoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productSubCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productSubCategoryListModification',
                content: 'Deleted an productSubCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-sub-category-delete-popup',
    template: ''
})
export class ProductSubCategoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productSubCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductSubCategoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productSubCategory = productSubCategory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/product-sub-category', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/product-sub-category', { outlets: { popup: null } }]);
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
