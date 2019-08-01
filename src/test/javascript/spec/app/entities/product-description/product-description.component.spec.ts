/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { ProductDescriptionComponent } from 'app/entities/product-description/product-description.component';
import { ProductDescriptionService } from 'app/entities/product-description/product-description.service';
import { ProductDescription } from 'app/shared/model/product-description.model';

describe('Component Tests', () => {
    describe('ProductDescription Management Component', () => {
        let comp: ProductDescriptionComponent;
        let fixture: ComponentFixture<ProductDescriptionComponent>;
        let service: ProductDescriptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductDescriptionComponent],
                providers: []
            })
                .overrideTemplate(ProductDescriptionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductDescriptionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductDescriptionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductDescription(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productDescriptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
