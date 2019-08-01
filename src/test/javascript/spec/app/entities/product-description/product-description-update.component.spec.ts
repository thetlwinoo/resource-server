/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductDescriptionUpdateComponent } from 'app/entities/product-description/product-description-update.component';
import { ProductDescriptionService } from 'app/entities/product-description/product-description.service';
import { ProductDescription } from 'app/shared/model/product-description.model';

describe('Component Tests', () => {
    describe('ProductDescription Management Update Component', () => {
        let comp: ProductDescriptionUpdateComponent;
        let fixture: ComponentFixture<ProductDescriptionUpdateComponent>;
        let service: ProductDescriptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductDescriptionUpdateComponent]
            })
                .overrideTemplate(ProductDescriptionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductDescriptionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductDescriptionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductDescription(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productDescription = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductDescription();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productDescription = entity;
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
