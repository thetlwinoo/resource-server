/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { ProductModelComponent } from 'app/entities/product-model/product-model.component';
import { ProductModelService } from 'app/entities/product-model/product-model.service';
import { ProductModel } from 'app/shared/model/product-model.model';

describe('Component Tests', () => {
    describe('ProductModel Management Component', () => {
        let comp: ProductModelComponent;
        let fixture: ComponentFixture<ProductModelComponent>;
        let service: ProductModelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [ProductModelComponent],
                providers: []
            })
                .overrideTemplate(ProductModelComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductModelComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductModelService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductModel(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productModels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
