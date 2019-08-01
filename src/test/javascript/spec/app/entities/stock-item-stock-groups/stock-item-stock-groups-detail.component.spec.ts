/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { StockItemStockGroupsDetailComponent } from 'app/entities/stock-item-stock-groups/stock-item-stock-groups-detail.component';
import { StockItemStockGroups } from 'app/shared/model/stock-item-stock-groups.model';

describe('Component Tests', () => {
    describe('StockItemStockGroups Management Detail Component', () => {
        let comp: StockItemStockGroupsDetailComponent;
        let fixture: ComponentFixture<StockItemStockGroupsDetailComponent>;
        const route = ({ data: of({ stockItemStockGroups: new StockItemStockGroups(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [StockItemStockGroupsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StockItemStockGroupsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockItemStockGroupsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stockItemStockGroups).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
