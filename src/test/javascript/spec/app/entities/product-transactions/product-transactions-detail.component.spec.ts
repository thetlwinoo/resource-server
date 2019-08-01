/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { ProductTransactionsDetailComponent } from 'app/entities/product-transactions/product-transactions-detail.component';
import { ProductTransactions } from 'app/shared/model/product-transactions.model';

describe('Component Tests', () => {
    describe('ProductTransactions Management Detail Component', () => {
        let comp: ProductTransactionsDetailComponent;
        let fixture: ComponentFixture<ProductTransactionsDetailComponent>;
        const route = ({ data: of({ productTransactions: new ProductTransactions(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductTransactionsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductTransactionsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductTransactionsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productTransactions).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
