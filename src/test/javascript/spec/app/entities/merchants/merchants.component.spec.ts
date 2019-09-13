/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { MerchantsComponent } from 'app/entities/merchants/merchants.component';
import { MerchantsService } from 'app/entities/merchants/merchants.service';
import { Merchants } from 'app/shared/model/merchants.model';

describe('Component Tests', () => {
    describe('Merchants Management Component', () => {
        let comp: MerchantsComponent;
        let fixture: ComponentFixture<MerchantsComponent>;
        let service: MerchantsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [MerchantsComponent],
                providers: []
            })
                .overrideTemplate(MerchantsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MerchantsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Merchants(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.merchants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
