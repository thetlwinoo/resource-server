/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { StockGroupsUpdateComponent } from 'app/entities/stock-groups/stock-groups-update.component';
import { StockGroupsService } from 'app/entities/stock-groups/stock-groups.service';
import { StockGroups } from 'app/shared/model/stock-groups.model';

describe('Component Tests', () => {
    describe('StockGroups Management Update Component', () => {
        let comp: StockGroupsUpdateComponent;
        let fixture: ComponentFixture<StockGroupsUpdateComponent>;
        let service: StockGroupsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [StockGroupsUpdateComponent]
            })
                .overrideTemplate(StockGroupsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockGroupsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockGroupsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StockGroups(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.stockGroups = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StockGroups();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.stockGroups = entity;
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
