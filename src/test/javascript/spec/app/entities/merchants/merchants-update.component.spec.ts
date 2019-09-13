/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { MerchantsUpdateComponent } from 'app/entities/merchants/merchants-update.component';
import { MerchantsService } from 'app/entities/merchants/merchants.service';
import { Merchants } from 'app/shared/model/merchants.model';

describe('Component Tests', () => {
    describe('Merchants Management Update Component', () => {
        let comp: MerchantsUpdateComponent;
        let fixture: ComponentFixture<MerchantsUpdateComponent>;
        let service: MerchantsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [MerchantsUpdateComponent]
            })
                .overrideTemplate(MerchantsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MerchantsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Merchants(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.merchants = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Merchants();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.merchants = entity;
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
