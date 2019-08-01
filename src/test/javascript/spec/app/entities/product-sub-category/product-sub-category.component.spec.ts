/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { ProductSubCategoryComponent } from 'app/entities/product-sub-category/product-sub-category.component';
import { ProductSubCategoryService } from 'app/entities/product-sub-category/product-sub-category.service';
import { ProductSubCategory } from 'app/shared/model/product-sub-category.model';

describe('Component Tests', () => {
    describe('ProductSubCategory Management Component', () => {
        let comp: ProductSubCategoryComponent;
        let fixture: ComponentFixture<ProductSubCategoryComponent>;
        let service: ProductSubCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductSubCategoryComponent],
                providers: []
            })
                .overrideTemplate(ProductSubCategoryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductSubCategoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductSubCategoryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductSubCategory(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productSubCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
