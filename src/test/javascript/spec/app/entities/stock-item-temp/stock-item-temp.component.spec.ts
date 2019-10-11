/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ResourceTestModule } from '../../../test.module';
import { StockItemTempComponent } from 'app/entities/stock-item-temp/stock-item-temp.component';
import { StockItemTempService } from 'app/entities/stock-item-temp/stock-item-temp.service';
import { StockItemTemp } from 'app/shared/model/stock-item-temp.model';

describe('Component Tests', () => {
    describe('StockItemTemp Management Component', () => {
        let comp: StockItemTempComponent;
        let fixture: ComponentFixture<StockItemTempComponent>;
        let service: StockItemTempService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResourceTestModule],
                declarations: [StockItemTempComponent],
                providers: []
            })
                .overrideTemplate(StockItemTempComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockItemTempComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockItemTempService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new StockItemTemp(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.stockItemTemps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
