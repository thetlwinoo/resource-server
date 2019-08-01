/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResourceTestModule } from '../../../test.module';
import { StockGroupsDeleteDialogComponent } from 'app/entities/stock-groups/stock-groups-delete-dialog.component';
import { StockGroupsService } from 'app/entities/stock-groups/stock-groups.service';

describe('Component Tests', () => {
    describe('StockGroups Management Delete Component', () => {
        let comp: StockGroupsDeleteDialogComponent;
        let fixture: ComponentFixture<StockGroupsDeleteDialogComponent>;
        let service: StockGroupsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [StockGroupsDeleteDialogComponent]
            })
                .overrideTemplate(StockGroupsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockGroupsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockGroupsService);
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
