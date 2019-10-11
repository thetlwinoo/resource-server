import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILastestMerchantUploadedDocument } from 'app/shared/model/lastest-merchant-uploaded-document.model';
import { LastestMerchantUploadedDocumentService } from './lastest-merchant-uploaded-document.service';

@Component({
    selector: 'jhi-lastest-merchant-uploaded-document-delete-dialog',
    templateUrl: './lastest-merchant-uploaded-document-delete-dialog.component.html'
})
export class LastestMerchantUploadedDocumentDeleteDialogComponent {
    lastestMerchantUploadedDocument: ILastestMerchantUploadedDocument;

    constructor(
        protected lastestMerchantUploadedDocumentService: LastestMerchantUploadedDocumentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lastestMerchantUploadedDocumentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lastestMerchantUploadedDocumentListModification',
                content: 'Deleted an lastestMerchantUploadedDocument'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lastest-merchant-uploaded-document-delete-popup',
    template: ''
})
export class LastestMerchantUploadedDocumentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lastestMerchantUploadedDocument }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LastestMerchantUploadedDocumentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.lastestMerchantUploadedDocument = lastestMerchantUploadedDocument;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/lastest-merchant-uploaded-document', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/lastest-merchant-uploaded-document', { outlets: { popup: null } }]);
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
