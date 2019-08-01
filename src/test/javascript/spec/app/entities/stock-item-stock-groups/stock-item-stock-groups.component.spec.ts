/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { StockItemStockGroupsComponent } from 'app/entities/stock-item-stock-groups/stock-item-stock-groups.component';
import { StockItemStockGroupsService } from 'app/entities/stock-item-stock-groups/stock-item-stock-groups.service';
import { StockItemStockGroups } from 'app/shared/model/stock-item-stock-groups.model';

describe('Component Tests', () => {
    describe('StockItemStockGroups Management Component', () => {
        let comp: StockItemStockGroupsComponent;
        let fixture: ComponentFixture<StockItemStockGroupsComponent>;
        let service: StockItemStockGroupsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [StockItemStockGroupsComponent],
                providers: []
            })
                .overrideTemplate(StockItemStockGroupsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockItemStockGroupsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockItemStockGroupsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new StockItemStockGroups(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.stockItemStockGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
