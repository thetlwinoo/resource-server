/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { ProductInventoryComponent } from 'app/entities/product-inventory/product-inventory.component';
import { ProductInventoryService } from 'app/entities/product-inventory/product-inventory.service';
import { ProductInventory } from 'app/shared/model/product-inventory.model';

describe('Component Tests', () => {
    describe('ProductInventory Management Component', () => {
        let comp: ProductInventoryComponent;
        let fixture: ComponentFixture<ProductInventoryComponent>;
        let service: ProductInventoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductInventoryComponent],
                providers: []
            })
                .overrideTemplate(ProductInventoryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductInventoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductInventoryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductInventory(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productInventories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
