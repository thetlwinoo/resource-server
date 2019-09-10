/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { MerchantsDetailComponent } from 'app/entities/merchants/merchants-detail.component';
import { Merchants } from 'app/shared/model/merchants.model';

describe('Component Tests', () => {
    describe('Merchants Management Detail Component', () => {
        let comp: MerchantsDetailComponent;
        let fixture: ComponentFixture<MerchantsDetailComponent>;
        const route = ({ data: of({ merchants: new Merchants(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [MerchantsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MerchantsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MerchantsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.merchants).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
