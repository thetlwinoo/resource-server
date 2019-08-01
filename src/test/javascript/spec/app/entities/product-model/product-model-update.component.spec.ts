/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductModelUpdateComponent } from 'app/entities/product-model/product-model-update.component';
import { ProductModelService } from 'app/entities/product-model/product-model.service';
import { ProductModel } from 'app/shared/model/product-model.model';

describe('Component Tests', () => {
    describe('ProductModel Management Update Component', () => {
        let comp: ProductModelUpdateComponent;
        let fixture: ComponentFixture<ProductModelUpdateComponent>;
        let service: ProductModelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductModelUpdateComponent]
            })
                .overrideTemplate(ProductModelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductModelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductModelService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductModel(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productModel = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductModel();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productModel = entity;
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
