/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResourceTestModule } from '../../../test.module';
import { ProductModelDescriptionDeleteDialogComponent } from 'app/entities/product-model-description/product-model-description-delete-dialog.component';
import { ProductModelDescriptionService } from 'app/entities/product-model-description/product-model-description.service';

describe('Component Tests', () => {
    describe('ProductModelDescription Management Delete Component', () => {
        let comp: ProductModelDescriptionDeleteDialogComponent;
        let fixture: ComponentFixture<ProductModelDescriptionDeleteDialogComponent>;
        let service: ProductModelDescriptionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductModelDescriptionDeleteDialogComponent]
            })
                .overrideTemplate(ProductModelDescriptionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductModelDescriptionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductModelDescriptionService);
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
