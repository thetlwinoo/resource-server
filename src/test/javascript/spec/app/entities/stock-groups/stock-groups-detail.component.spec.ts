/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { StockGroupsDetailComponent } from 'app/entities/stock-groups/stock-groups-detail.component';
import { StockGroups } from 'app/shared/model/stock-groups.model';

describe('Component Tests', () => {
    describe('StockGroups Management Detail Component', () => {
        let comp: StockGroupsDetailComponent;
        let fixture: ComponentFixture<StockGroupsDetailComponent>;
        const route = ({ data: of({ stockGroups: new StockGroups(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [StockGroupsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StockGroupsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockGroupsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stockGroups).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
