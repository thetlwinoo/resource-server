/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductModelDetailComponent } from 'app/entities/product-model/product-model-detail.component';
import { ProductModel } from 'app/shared/model/product-model.model';

describe('Component Tests', () => {
    describe('ProductModel Management Detail Component', () => {
        let comp: ProductModelDetailComponent;
        let fixture: ComponentFixture<ProductModelDetailComponent>;
        const route = ({ data: of({ productModel: new ProductModel(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductModelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductModelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductModelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productModel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
