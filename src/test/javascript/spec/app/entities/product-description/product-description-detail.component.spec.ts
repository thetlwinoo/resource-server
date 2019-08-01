/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductDescriptionDetailComponent } from 'app/entities/product-description/product-description-detail.component';
import { ProductDescription } from 'app/shared/model/product-description.model';

describe('Component Tests', () => {
    describe('ProductDescription Management Detail Component', () => {
        let comp: ProductDescriptionDetailComponent;
        let fixture: ComponentFixture<ProductDescriptionDetailComponent>;
        const route = ({ data: of({ productDescription: new ProductDescription(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductDescriptionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductDescriptionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductDescriptionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productDescription).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
