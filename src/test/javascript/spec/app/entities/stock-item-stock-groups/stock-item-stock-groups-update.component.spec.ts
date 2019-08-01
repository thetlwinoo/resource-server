/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { StockItemStockGroupsUpdateComponent } from 'app/entities/stock-item-stock-groups/stock-item-stock-groups-update.component';
import { StockItemStockGroupsService } from 'app/entities/stock-item-stock-groups/stock-item-stock-groups.service';
import { StockItemStockGroups } from 'app/shared/model/stock-item-stock-groups.model';

describe('Component Tests', () => {
    describe('StockItemStockGroups Management Update Component', () => {
        let comp: StockItemStockGroupsUpdateComponent;
        let fixture: ComponentFixture<StockItemStockGroupsUpdateComponent>;
        let service: StockItemStockGroupsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [StockItemStockGroupsUpdateComponent]
            })
                .overrideTemplate(StockItemStockGroupsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockItemStockGroupsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockItemStockGroupsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StockItemStockGroups(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.stockItemStockGroups = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StockItemStockGroups();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.stockItemStockGroups = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
