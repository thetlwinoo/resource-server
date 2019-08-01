/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ComparesUpdateComponent } from 'app/entities/compares/compares-update.component';
import { ComparesService } from 'app/entities/compares/compares.service';
import { Compares } from 'app/shared/model/compares.model';

describe('Component Tests', () => {
    describe('Compares Management Update Component', () => {
        let comp: ComparesUpdateComponent;
        let fixture: ComponentFixture<ComparesUpdateComponent>;
        let service: ComparesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ComparesUpdateComponent]
            })
                .overrideTemplate(ComparesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ComparesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComparesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Compares(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.compares = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Compares();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.compares = entity;
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
