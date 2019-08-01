/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResourceTestModule } from '../../../test.module';
import { StockItemStockGroupsDeleteDialogComponent } from 'app/entities/stock-item-stock-groups/stock-item-stock-groups-delete-dialog.component';
import { StockItemStockGroupsService } from 'app/entities/stock-item-stock-groups/stock-item-stock-groups.service';

describe('Component Tests', () => {
    describe('StockItemStockGroups Management Delete Component', () => {
        let comp: StockItemStockGroupsDeleteDialogComponent;
        let fixture: ComponentFixture<StockItemStockGroupsDeleteDialogComponent>;
        let service: StockItemStockGroupsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [StockItemStockGroupsDeleteDialogComponent]
            })
                .overrideTemplate(StockItemStockGroupsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockItemStockGroupsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockItemStockGroupsService);
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
