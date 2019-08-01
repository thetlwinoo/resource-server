/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductInventoryDetailComponent } from 'app/entities/product-inventory/product-inventory-detail.component';
import { ProductInventory } from 'app/shared/model/product-inventory.model';

describe('Component Tests', () => {
    describe('ProductInventory Management Detail Component', () => {
        let comp: ProductInventoryDetailComponent;
        let fixture: ComponentFixture<ProductInventoryDetailComponent>;
        const route = ({ data: of({ productInventory: new ProductInventory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductInventoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductInventoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductInventoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productInventory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
