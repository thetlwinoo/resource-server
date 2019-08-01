/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResourceTestModule } from '../../../test.module';
import { ProductSubCategoryDeleteDialogComponent } from 'app/entities/product-sub-category/product-sub-category-delete-dialog.component';
import { ProductSubCategoryService } from 'app/entities/product-sub-category/product-sub-category.service';

describe('Component Tests', () => {
    describe('ProductSubCategory Management Delete Component', () => {
        let comp: ProductSubCategoryDeleteDialogComponent;
        let fixture: ComponentFixture<ProductSubCategoryDeleteDialogComponent>;
        let service: ProductSubCategoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductSubCategoryDeleteDialogComponent]
            })
                .overrideTemplate(ProductSubCategoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductSubCategoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductSubCategoryService);
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
