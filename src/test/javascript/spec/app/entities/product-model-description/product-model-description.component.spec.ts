/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { ProductModelDescriptionComponent } from 'app/entities/product-model-description/product-model-description.component';
import { ProductModelDescriptionService } from 'app/entities/product-model-description/product-model-description.service';
import { ProductModelDescription } from 'app/shared/model/product-model-description.model';

describe('Component Tests', () => {
    describe('ProductModelDescription Management Component', () => {
        let comp: ProductModelDescriptionComponent;
        let fixture: ComponentFixture<ProductModelDescriptionComponent>;
        let service: ProductModelDescriptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductModelDescriptionComponent],
                providers: []
            })
                .overrideTemplate(ProductModelDescriptionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductModelDescriptionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductModelDescriptionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductModelDescription(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productModelDescriptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
