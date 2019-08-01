/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { ProductTransactionsComponent } from 'app/entities/product-transactions/product-transactions.component';
import { ProductTransactionsService } from 'app/entities/product-transactions/product-transactions.service';
import { ProductTransactions } from 'app/shared/model/product-transactions.model';

describe('Component Tests', () => {
    describe('ProductTransactions Management Component', () => {
        let comp: ProductTransactionsComponent;
        let fixture: ComponentFixture<ProductTransactionsComponent>;
        let service: ProductTransactionsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductTransactionsComponent],
                providers: []
            })
                .overrideTemplate(ProductTransactionsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductTransactionsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductTransactionsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductTransactions(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
