/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductTransactionsUpdateComponent } from 'app/entities/product-transactions/product-transactions-update.component';
import { ProductTransactionsService } from 'app/entities/product-transactions/product-transactions.service';
import { ProductTransactions } from 'app/shared/model/product-transactions.model';

describe('Component Tests', () => {
    describe('ProductTransactions Management Update Component', () => {
        let comp: ProductTransactionsUpdateComponent;
        let fixture: ComponentFixture<ProductTransactionsUpdateComponent>;
        let service: ProductTransactionsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductTransactionsUpdateComponent]
            })
                .overrideTemplate(ProductTransactionsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductTransactionsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductTransactionsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductTransactions(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productTransactions = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductTransactions();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productTransactions = entity;
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
