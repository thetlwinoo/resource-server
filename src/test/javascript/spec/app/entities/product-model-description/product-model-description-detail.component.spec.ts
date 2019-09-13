/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductModelDescriptionDetailComponent } from 'app/entities/product-model-description/product-model-description-detail.component';
import { ProductModelDescription } from 'app/shared/model/product-model-description.model';

describe('Component Tests', () => {
    describe('ProductModelDescription Management Detail Component', () => {
        let comp: ProductModelDescriptionDetailComponent;
        let fixture: ComponentFixture<ProductModelDescriptionDetailComponent>;
        const route = ({ data: of({ productModelDescription: new ProductModelDescription(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductModelDescriptionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductModelDescriptionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductModelDescriptionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productModelDescription).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
