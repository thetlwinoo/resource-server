/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductSubCategoryDetailComponent } from 'app/entities/product-sub-category/product-sub-category-detail.component';
import { ProductSubCategory } from 'app/shared/model/product-sub-category.model';

describe('Component Tests', () => {
    describe('ProductSubCategory Management Detail Component', () => {
        let comp: ProductSubCategoryDetailComponent;
        let fixture: ComponentFixture<ProductSubCategoryDetailComponent>;
        const route = ({ data: of({ productSubCategory: new ProductSubCategory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductSubCategoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductSubCategoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductSubCategoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productSubCategory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
