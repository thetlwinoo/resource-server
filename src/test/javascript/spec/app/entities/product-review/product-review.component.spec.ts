/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { ProductReviewComponent } from 'app/entities/product-review/product-review.component';
import { ProductReviewService } from 'app/entities/product-review/product-review.service';
import { ProductReview } from 'app/shared/model/product-review.model';

describe('Component Tests', () => {
    describe('ProductReview Management Component', () => {
        let comp: ProductReviewComponent;
        let fixture: ComponentFixture<ProductReviewComponent>;
        let service: ProductReviewService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductReviewComponent],
                providers: []
            })
                .overrideTemplate(ProductReviewComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductReviewComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductReviewService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductReview(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productReviews[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
