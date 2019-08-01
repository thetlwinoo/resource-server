/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResourceTestModule } from '../../../test.module';
import { ProductInventoryDeleteDialogComponent } from 'app/entities/product-inventory/product-inventory-delete-dialog.component';
import { ProductInventoryService } from 'app/entities/product-inventory/product-inventory.service';

describe('Component Tests', () => {
    describe('ProductInventory Management Delete Component', () => {
        let comp: ProductInventoryDeleteDialogComponent;
        let fixture: ComponentFixture<ProductInventoryDeleteDialogComponent>;
        let service: ProductInventoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductInventoryDeleteDialogComponent]
            })
                .overrideTemplate(ProductInventoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductInventoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductInventoryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
