/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductSubCategoryUpdateComponent } from 'app/entities/product-sub-category/product-sub-category-update.component';
import { ProductSubCategoryService } from 'app/entities/product-sub-category/product-sub-category.service';
import { ProductSubCategory } from 'app/shared/model/product-sub-category.model';

describe('Component Tests', () => {
    describe('ProductSubCategory Management Update Component', () => {
        let comp: ProductSubCategoryUpdateComponent;
        let fixture: ComponentFixture<ProductSubCategoryUpdateComponent>;
        let service: ProductSubCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductSubCategoryUpdateComponent]
            })
                .overrideTemplate(ProductSubCategoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductSubCategoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductSubCategoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductSubCategory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productSubCategory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductSubCategory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productSubCategory = entity;
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
