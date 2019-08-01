/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductInventoryUpdateComponent } from 'app/entities/product-inventory/product-inventory-update.component';
import { ProductInventoryService } from 'app/entities/product-inventory/product-inventory.service';
import { ProductInventory } from 'app/shared/model/product-inventory.model';

describe('Component Tests', () => {
    describe('ProductInventory Management Update Component', () => {
        let comp: ProductInventoryUpdateComponent;
        let fixture: ComponentFixture<ProductInventoryUpdateComponent>;
        let service: ProductInventoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductInventoryUpdateComponent]
            })
                .overrideTemplate(ProductInventoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductInventoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductInventoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductInventory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productInventory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductInventory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productInventory = entity;
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
