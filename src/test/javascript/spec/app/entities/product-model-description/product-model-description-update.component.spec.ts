/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductModelDescriptionUpdateComponent } from 'app/entities/product-model-description/product-model-description-update.component';
import { ProductModelDescriptionService } from 'app/entities/product-model-description/product-model-description.service';
import { ProductModelDescription } from 'app/shared/model/product-model-description.model';

describe('Component Tests', () => {
    describe('ProductModelDescription Management Update Component', () => {
        let comp: ProductModelDescriptionUpdateComponent;
        let fixture: ComponentFixture<ProductModelDescriptionUpdateComponent>;
        let service: ProductModelDescriptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductModelDescriptionUpdateComponent]
            })
                .overrideTemplate(ProductModelDescriptionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductModelDescriptionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductModelDescriptionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductModelDescription(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productModelDescription = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductModelDescription();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productModelDescription = entity;
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
